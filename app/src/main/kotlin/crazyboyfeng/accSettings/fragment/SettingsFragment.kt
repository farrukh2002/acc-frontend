package crazyboyfeng.accSettings.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.preference.*
import crazyboyfeng.accSettings.R
import crazyboyfeng.accSettings.acc.AccHandler
import crazyboyfeng.accSettings.acc.Command
import crazyboyfeng.accSettings.data.AccDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var accPreferenceCategory: PreferenceCategory

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = AccDataStore(requireContext())
        setPreferencesFromResource(R.xml.settings_preferences, null)
        reload()
        checkAcc()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)!!
    }

    private fun reload() {
        setPreferencesFromResource(R.xml.settings_preferences, null)
        accPreferenceCategory = findPreference(getString(R.string.acc))!!
    }

    private fun enableAcc() {
        preferenceManager.preferenceDataStore = AccDataStore(requireContext())
        reload()
        accPreferenceCategory.isEnabled = true
        val infoStatus = findPreference<PreferenceCategory>(getString(R.string.info_status))!!
        infoStatus.isVisible = true

        val infoCapacity = findPreference<CustomEditTextPreference>(getString(R.string.info_capacity))!!
        val infoTemp = findPreference<CustomEditTextPreference>(getString(R.string.info_temp))!!
        val infoCurrent = findPreference<CustomEditTextPreference>(getString(R.string.info_current_now))!!
        val infoVoltage = findPreference<CustomEditTextPreference>(getString(R.string.info_voltage_now))!!
        val infoPower = findPreference<CustomEditTextPreference>(getString(R.string.info_power_now))!!

        val customPreferences = listOf(infoCapacity, infoTemp, infoCurrent, infoVoltage, infoPower)

        customPreferences.forEach { pref ->
            pref.setSummaryProvider {
                val preference = it as CustomEditTextPreference
                val text = preference.text
                if (text.isNullOrEmpty()) text else text.toString()
            }
        }

        updateInfo()
    }

    private suspend fun testVersion() {
        val versions = Command.getVersion()
        val bundledVersionCode = resources.getInteger(R.integer.acc_version_code)
        if (versions.first < bundledVersionCode) {
            accPreferenceCategory.summary = getString(R.string.installed_incompatible, versions.second)
            return
        }
        enableAcc()
        if (versions.first == bundledVersionCode) {
            accPreferenceCategory.summary = getString(R.string.installed_compatible, versions.second)
        }
        if (versions.first > bundledVersionCode) {
            accPreferenceCategory.summary = getString(R.string.installed_possibly_incompatible, versions.second)
        }
    }

    @Suppress("DEPRECATION")
    private fun checkAcc() = lifecycleScope.launchWhenCreated {
        accPreferenceCategory.summary = getString(R.string.initializing)
        val message = try {
            AccHandler().initial(requireContext())
            null
        } catch (_: Command.FailedException) {
            getString(R.string.command_failed)
        } catch (e: Command.AccException) {
            e.localizedMessage
        }
        if (message != null) {
            accPreferenceCategory.summary = message
            return@launchWhenCreated
        }
        var i = 0
        while (isActive) {
            try {
                testVersion()
                return@launchWhenCreated
            } catch (e: Command.AccException) {
                if (i < 5) {
                    delay(1000)
                    i++
                    continue
                } else {
                    accPreferenceCategory.summary = e.localizedMessage
                    return@launchWhenCreated
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun updateInfo() = lifecycleScope.launchWhenStarted {
        while (isActive) {
            val properties = Command.getInfo()
            Log.d(TAG, "updateInfo ${properties.size}")
            for (property in properties) {
                val value = property.value as String
                if (value.isEmpty()) {
                    continue
                }
                val key = property.key as String
                val preference = findPreference<Preference>(key)

                Log.d(TAG, "Updating key $key with value $value")

                when (preference) {
                    is CustomEditTextPreference -> preference.text = value
                    is EditTextPreferencePlus -> preference.text = value
                    is Preference -> preference.summary = value
                }
            }
            delay(1000)
        }
    }

    private companion object {
        const val TAG = "SettingsFragment"
    }
}

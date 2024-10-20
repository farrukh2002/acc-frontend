package crazyboyfeng.accSettings

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.topjohnwu.superuser.Shell
import crazyboyfeng.accSettings.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity(),
    PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyApp_Preferences)
        super.onCreate(savedInstanceState)
        if (!Shell.rootAccess()) {
            val noRootView = LayoutInflater.from(this).inflate(R.layout.no_root, null)
            val contentFrameLayout = findViewById<ContentFrameLayout>(android.R.id.content)
            contentFrameLayout.addView(noRootView)
            return
        }
        else {
            supportFragmentManager.addOnBackStackChangedListener {
                val preferenceFragment =
                    supportFragmentManager.findFragmentById(android.R.id.content) as PreferenceFragmentCompat
                supportActionBar?.subtitle = preferenceFragment.preferenceScreen.title
            }
            supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
        }
    }

    // In SettingsActivity.kt
    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        // Get the fragment that needs to be displayed
        val fragment = Fragment.instantiate(this, pref.fragment!!, pref.extras)

        // Set the fragment's arguments (if any)
        fragment.arguments = pref.extras

        // Set the fragment's title (if any)
        supportActionBar?.title = pref.title

        // Replace the current fragment with the new one
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()

        return true
    }
}
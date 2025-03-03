package crazyboyfeng.accSettings.data

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceDataStore
import crazyboyfeng.accSettings.R
import crazyboyfeng.accSettings.acc.Command
import kotlinx.coroutines.*

class ConfigDataStore(private val context: Context) : PreferenceDataStore() {
    private var supportInVoltage: Boolean = false
    override fun putBoolean(key: String, value: Boolean) {
        Log.v(TAG, "putBoolean: $key=$value")
        CoroutineScope(Dispatchers.Default).launch {
            when (key) {
                context.getString(R.string.support_in_voltage) -> supportInVoltage = value
                else -> Command.setConfig(key, value.toString())
            }
            onConfigChangeListener?.onConfigChanged(key)
        }
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        Log.v(TAG, "getBoolean: $key=$defValue?")
        return runBlocking {
            when (key) {
                context.getString(R.string.support_in_voltage) -> supportInVoltage
                else -> {
                    val value = Command.getConfig(key)
                    if (value?.isEmpty() == true) {
                        defValue
                    } else {
                        value.toBoolean()
                    }
                }
            }
        }
    }

    override fun putInt(key: String, value: Int) {
        Log.v(TAG, "putInt: $key=$value")
        CoroutineScope(Dispatchers.Default).launch {
            Command.setConfig(key, value.toString())
            onConfigChangeListener?.onConfigChanged(key)
        }
    }

    override fun getInt(key: String, defValue: Int): Int {
        Log.v(TAG, "getInt: $key=$defValue?")
        return runBlocking {
            val value = Command.getConfig(key)
            (if (value?.isEmpty() == true) {
                defValue
            } else {
                value?.toInt()
            })!!
        }
    }

    override fun putString(key: String, value: String?) {
        Log.v(TAG, "putString: $key=$value")
        CoroutineScope(Dispatchers.Default).launch {
            Command.setConfig(key, value)
            onConfigChangeListener?.onConfigChanged(key)
        }
    }

    override fun getString(key: String, defValue: String?): String? {
        Log.v(TAG, "getString: $key=$defValue?")
        return runBlocking {
            val value = Command.getConfig(key)
            value?.ifEmpty {
                defValue
            }
        }
    }

    fun interface OnConfigChangeListener {
        fun onConfigChanged(key: String)
    }

    var onConfigChangeListener: OnConfigChangeListener? = null

    private companion object {
        const val TAG = "ConfigDataStore"
    }
}
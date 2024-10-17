package crazyboyfeng.accSettings.data

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceDataStore
import crazyboyfeng.accSettings.R
import crazyboyfeng.accSettings.acc.Command
import kotlinx.coroutines.*

class AccDataStore(private val context: Context) : PreferenceDataStore() {
    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        Log.v(TAG, "getBoolean: $key=$defValue?")
        return runBlocking {
            when (key) {
                context.getString(R.string.acc_daemon) -> Command.isDaemonRunning()
                context.getString(R.string.dark_mode) -> context.getSharedPreferences(
                    SHARED_PREFS_NAME,Context.MODE_PRIVATE).getBoolean(key, defValue)
                else -> super.getBoolean(key, defValue)
            }
        }
    }

    override fun putBoolean(key: String, value: Boolean) {
        Log.v(TAG, "putBoolean: $key=$value")
        CoroutineScope(Dispatchers.Default).launch {
            when (key) {
                context.getString(R.string.acc_daemon) -> Command.setDaemonRunning(value)
                context.getString(R.string.dark_mode) ->
                    context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(key, value)
                        .apply()
                else -> super.putBoolean(key, value)
            }
        }
    }

    private companion object {
        const val SHARED_PREFS_NAME = "acc_settings"
        const val TAG = "AccDataStore"
    }
}
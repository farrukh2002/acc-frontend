package crazyboyfeng.accSettings

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
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
        super.onCreate(savedInstanceState)
        if (!Shell.rootAccess()) {
            val textView = TextView(this)
            textView.apply {
                gravity = Gravity.CENTER
                textSize = 24f
                setPadding(0, 100, 0, 0)
                setTextColor(0xffff0000.toInt())
                text = getString(R.string.need_root_permission)
            }
            val contentFrameLayout = findViewById<ContentFrameLayout>(android.R.id.content)
            contentFrameLayout.addView(textView)
            return
        }
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
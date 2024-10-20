package crazyboyfeng.accSettings.fragment

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import crazyboyfeng.accSettings.R

class CustomPreference(context: Context, attrs: AttributeSet?) : Preference(context, attrs) {
    init {
        layoutResource = R.layout.custom_preference_layout
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val title = holder.itemView.findViewById<TextView>(R.id.custom_pref_title)
        val colon = holder.itemView.findViewById<TextView>(R.id.custom_pref_colon)
        val summary = holder.itemView.findViewById<TextView>(R.id.custom_pref_summary)

        title.text = this.title
        colon.visibility = View.GONE  // Always hide colon for CustomPreference
        summary.text = this.summary
    }
}

class CustomEditTextPreference(context: Context, attrs: AttributeSet?) : EditTextPreference(context, attrs) {
    init {
        layoutResource = R.layout.custom_preference_layout
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val title = holder.itemView.findViewById<TextView>(R.id.custom_pref_title)
        val colon = holder.itemView.findViewById<TextView>(R.id.custom_pref_colon)
        val summary = holder.itemView.findViewById<TextView>(R.id.custom_pref_summary)

        title.text = this.title
        colon.visibility = View.VISIBLE  // Show colon only if summary is not empty
        summary.text = this.summary
    }
}

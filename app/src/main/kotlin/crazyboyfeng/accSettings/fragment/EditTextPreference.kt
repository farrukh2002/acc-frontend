package crazyboyfeng.accSettings.fragment

import android.content.Context
import crazyboyfeng.accSettings.R
import android.util.AttributeSet
import androidx.preference.EditTextPreference

class EditTextPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextPreferenceStyle,
    defStyleRes: Int = 0
) : EditTextPreference(context, attrs, defStyleAttr, defStyleRes) {

    var formatSummary: Boolean
    var maxLength: Int
    var inputType: Int
    var hint: CharSequence?

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.EditTextPreferencePlus, defStyleAttr, defStyleRes
        )
        formatSummary = typedArray.getBoolean(R.styleable.EditTextPreferencePlus_formatSummary, false)
        maxLength = typedArray.getInt(R.styleable.EditTextPreferencePlus_android_maxLength, Int.MAX_VALUE)
        inputType = typedArray.getInt(R.styleable.EditTextPreferencePlus_android_inputType, InputType.TYPE_CLASS_TEXT)
        hint = typedArray.getString(R.styleable.EditTextPreferencePlus_android_hint)
        typedArray.recycle()
    }

    override fun getSummary(): CharSequence? {
        val superSummary = super.getSummary()
        if (!formatSummary || superSummary == null) {
            return superSummary
        }
        return String.format(superSummary.toString(), text)
    }
}
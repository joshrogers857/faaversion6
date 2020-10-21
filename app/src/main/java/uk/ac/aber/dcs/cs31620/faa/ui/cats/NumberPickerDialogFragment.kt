package uk.ac.aber.dcs.cs31620.faa.ui.cats

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import uk.ac.aber.dcs.cs31620.faa.R

/**
 * Fragment allows us to display a dialog containing a number picker. This means we
 * can simplify the UI in the main fragment display to be, say, a button, rather than embed the
 * awkward NumberPicker directly in the UI.
 *
 * @author Based on Java code from http://www.zoftino.com/android-numberpicker-dialog-example
 */

const val MIN_VALUE  = "min_value"
const val MAX_VALUE  = "max_value"
const val MESSAGE  = "message"
const val NUMBER_PICKER_KEY  = "number_picker_key"

class NumberPickerDialogFragment : DialogFragment() {

    private lateinit var numberPicker: NumberPicker
    private var valueChangeListener: NumberPicker.OnValueChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This is a way of recreating reference to the parent fragment, but
        // only if it's a value change listener. That way we can
        // inform the listener if the NumberPicker value changes
        if (this.parentFragment is NumberPicker.OnValueChangeListener){
            valueChangeListener = this.parentFragment as NumberPicker.OnValueChangeListener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        numberPicker = NumberPicker(this.activity)
        val args = this.arguments
        val minValue = args?.getInt(MIN_VALUE) ?: 1
        val maxValue = args?.getInt(MAX_VALUE) ?: 10
        val message = args?.getString(MESSAGE) ?: ""

        numberPicker.minValue = minValue
        numberPicker.maxValue = maxValue

        savedInstanceState?.let {
            numberPicker.value = it.getInt(NUMBER_PICKER_KEY, minValue)
        }

        val builder = AlertDialog.Builder(this.activity)
        builder.setTitle(R.string.choose_dialog_title)
        builder.setMessage(message)

        builder.setPositiveButton(R.string.ok_button_text){dialog, which ->
            valueChangeListener?.onValueChange(numberPicker, numberPicker.value, numberPicker.value)
        }

        builder.setNegativeButton(R.string.cancel_button_text){dialog, which ->}

        builder.setView(numberPicker)
        return builder.create()
    }

    /**
     * Called when the fragment view is about to be destroyed. Save the state so that we can recreate it when
     * the fragment view is rebuilt
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NUMBER_PICKER_KEY, numberPicker.value)
        super.onSaveInstanceState(outState)
    }
}
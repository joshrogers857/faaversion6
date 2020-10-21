/**
 * Represents the cats page. Currently shows three Spinners
 * and a TextView to allow searching in a later version.
 * Screen state is made persistent. A DialogFragment is used
 * to input search distance.
 * @author Chris Loftus
 * @version 2
 */
package uk.ac.aber.dcs.cs31620.faa.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import uk.ac.aber.dcs.cs31620.faa.R
import uk.ac.aber.dcs.cs31620.faa.databinding.FragmentCatsBinding

private const val PROXIMITY_KEY = "proximity"

class CatsFragment : Fragment(), NumberPicker.OnValueChangeListener {
    private lateinit var catsFragmentBinding: FragmentCatsBinding
    private lateinit var proximityButton: TextView
    private var proximityValue: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        catsFragmentBinding = FragmentCatsBinding.inflate(inflater, container, false)

        setupSpinner(
            view,
            catsFragmentBinding.breedsSpinner,
            R.array.breed_array
        )
        setupSpinner(
            view,
            catsFragmentBinding.genderSpinner,
            R.array.gender_array
        )
        setupSpinner(
            view,
            catsFragmentBinding.ageSpinner,
            R.array.age_range_array
        )

        proximityButton = catsFragmentBinding.proximityButton
        proximityButton.setOnClickListener { v ->
            showNumberPicker(v, getString(R.string.proximity_dialog_title))
        }

        savedInstanceState?.let {
            proximityValue = it.getInt(PROXIMITY_KEY,
                                       this.resources.getInteger(R.integer.min_proximity_distance))
        } ?: run {
            proximityValue =
                this.resources.getInteger(R.integer.min_proximity_distance)
        }
        proximityButton.text = this.getString(R.string.distance, proximityValue)

        return catsFragmentBinding.root
    }

    private fun showNumberPicker(v: View, title: String) {
        val newDialog = NumberPickerDialogFragment()
        val args = Bundle()
        args.putInt(MIN_VALUE, this.resources.getInteger(R.integer.min_proximity_distance))
        args.putInt(MAX_VALUE, this.resources.getInteger(R.integer.max_proximity_distance))
        args.putString(MESSAGE, this.getString(R.string.max_distance_text))

        newDialog.arguments = args
        newDialog.show(this.childFragmentManager, title)
    }

    private fun setupSpinner(view: View?, spinner: Spinner, arrayResourceId: Int) {
        spinner.setSelection(1)

        val adapter =
            ArrayAdapter.createFromResource(
                requireContext(),
                arrayResourceId,
                android.R.layout.simple_spinner_item
            )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // We don't need this but we have to provide
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(context, "Item $id selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        proximityValue = picker?.value ?: proximityValue
        proximityButton.text = this.getString(R.string.distance, proximityValue)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PROXIMITY_KEY, proximityValue)
        super.onSaveInstanceState(outState)
    }
}




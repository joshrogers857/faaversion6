/**
 * Represents the cats page. Currently shows three Spinners
 * and a TextView to allow searching in a later version.
 * Screen state is made persistent. A DialogFragment is used
 * to input search distance.
 * Also shows RecyclerView grid of cats, a FloatingActionButton
 * and a Snackbar.
 * @author Chris Loftus
 * @version 3
 */
package uk.ac.aber.dcs.cs31620.faa.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import uk.ac.aber.dcs.cs31620.faa.R
import uk.ac.aber.dcs.cs31620.faa.databinding.FragmentCatsBinding
import uk.ac.aber.dcs.cs31620.faa.model.CatList

private const val PROXIMITY_KEY = "proximity"

private const val GRID_COLUMN_COUNT = 2

class CatsFragment : Fragment(), NumberPicker.OnValueChangeListener {
    private lateinit var catsFragmentBinding: FragmentCatsBinding
    private lateinit var proximityButton: TextView
    private var proximityValue: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        catsFragmentBinding = FragmentCatsBinding.inflate(inflater, container, false)

        setupSearchFields()

        restoreInstanceState(savedInstanceState)

        addCatsRecyclerView()

        addSnackbar()

        return catsFragmentBinding.root
    }

    private fun addSnackbar() {
        val fab = catsFragmentBinding.fabAdd

        fab.setOnClickListener {
            val snackbar = Snackbar.make(it, "Create cat FAB", Snackbar.LENGTH_LONG)

            // Obtain the BottomNavigationView from the parent activity so that we
            // can anchor to it
            val bnv = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            snackbar.anchorView = bnv

            snackbar.setAction("Undo") {
                // Code to handle undo goes here
            }
            snackbar.show()
        }
    }

    private fun addCatsRecyclerView() {
        val listCats = catsFragmentBinding.catList
        listCats.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(context, GRID_COLUMN_COUNT)
        listCats.layoutManager = gridLayoutManager

        val cats = CatList().cats
        val catsRecyclerAdapter = CatsRecyclerWithListAdapter(context, cats.toMutableList())
        listCats.adapter = catsRecyclerAdapter

        catsRecyclerAdapter.clickListener = View.OnClickListener { v ->
            val nameView: TextView = v.findViewById(R.id.catNameTextView)
            Toast.makeText(context, "Cat ${nameView.text} clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            proximityValue = it.getInt(
                PROXIMITY_KEY,
                this.resources.getInteger(R.integer.min_proximity_distance)
            )
        } ?: run {
            proximityValue =
                this.resources.getInteger(R.integer.min_proximity_distance)
        }
        proximityButton.text = this.getString(R.string.distance, proximityValue)
    }

    private fun setupSearchFields() {
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




/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.shoppingList.listdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.shoppingList.R
import com.example.android.shoppingList.database.AppDatabase
import com.example.android.shoppingList.databinding.FragmentDetailBinding
import com.example.android.shoppingList.utils.optionsMenu
import kotlinx.android.synthetic.main.list_item_dialog.view.*
import kotlinx.android.synthetic.main.list_title_dialog.view.dialog_cancel_button
import kotlinx.android.synthetic.main.list_title_dialog.view.dialog_list_name
import kotlinx.android.synthetic.main.list_title_dialog.view.dialog_save_button

// A fragment to show items on the shopping list
class DetailFragment : Fragment() {

    lateinit var detailViewModel: DetailViewModel

    var archive: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(requireArguments())
        archive = arguments.archive

        // Create an instance of the ViewModel Factory.
        val dataSource = AppDatabase.getInstance(application).listItemDatabaseDao()
        val viewModelFactory = DetailViewModelFactory(arguments.listKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        detailViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(DetailViewModel::class.java)

        // Give binding object a reference to the viewmodel
        binding.detailViewModel = detailViewModel
        binding.archive = archive

        binding.lifecycleOwner = this

        // Add an Observer on navigateUp to be notified when up button tapped
        detailViewModel.navigateUp.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigateUp()
            }
        })

        // Set activity title and subtitle once current shoppingList is retrieved from the database
        detailViewModel.shoppingList.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.detail_title)
            (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = it?.listName

        })

        // Initiate adapter with the clicklistener.
        // When archive parameter is true editing the list is disabled
        val adapter = ListItemAdapter(archive, ListItemListener { view, listItemID ->
            when (view.getId()) {
                R.id.check_image -> detailViewModel.onImageClicked(listItemID)
                R.id.bin_image -> detailViewModel.onItemDeleteClicked(listItemID)
            }
        })

        // Add an Observer on showDialog to be notified when add button is tapped
        detailViewModel.showDialog.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                showDialog()
            }
        })

        // Give binding object a reference to the adapter
        binding.detailRecyclerview.adapter = adapter

        // Show overflow menu/up button
        setHasOptionsMenu(true)

        return binding.root


    }


    // Inflate overflow menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (!archive) inflater.inflate(R.menu.overflow_menu_detail, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle overflow menu selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_list -> detailViewModel.actionSelected(optionsMenu.DELETE_LIST)
            R.id.archive_list -> detailViewModel.actionSelected(optionsMenu.ARCHIVE_LIST)

            else -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun showDialog() {

        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.list_item_dialog, null)

        val numberPicker = mDialogView.findViewById<NumberPicker>(R.id.number_picker)
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Item name and quantity")
        //show dialog
        val mAlertDialog = mBuilder.show()

        //Save button click of custom layout
        mDialogView.dialog_save_button.setOnClickListener {
            // Get text from EditText of custom layout
            val name = mDialogView.dialog_list_name.text.toString()
            val quantity = mDialogView.number_picker.value

            if (!name.equals("")) { // dismiss the dialog if name not blank
                mAlertDialog.dismiss()
                detailViewModel.doneEnteringItem(name, quantity)
            }

        }

        //cancel button click of custom layout
        mDialogView.dialog_cancel_button.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
        detailViewModel.doneShowingDialog()
    }
}

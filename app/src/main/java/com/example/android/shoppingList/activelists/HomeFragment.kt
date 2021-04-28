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

package com.example.android.shoppingList.activelists

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.shoppingList.R
import com.example.android.shoppingList.database.AppDatabase
import com.example.android.shoppingList.databinding.FragmentHomeBinding
import com.example.android.shoppingList.utils.LiveDataEvents
import com.example.android.shoppingList.utils.optionsMenu
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_title_dialog.view.*

// A fragment to show list of active shopping lists that are stored in the database
class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment view
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = AppDatabase.getInstance(application).shoppingListDatabaseDao()
        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        homeViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(HomeViewModel::class.java)

        // Give binding object a reference to the viewmodel
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        val adapter = HomeAdapter(HomeListener { listID ->
            homeViewModel.onShoppingListClicked(listID)
        })

        homeViewModel.eventsToObserve.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LiveDataEvents.dialogEvent -> if (it.showDialog) {
                    showDialog(); homeViewModel.dialogReset()
                }
                is LiveDataEvents.snackBarEvent -> if (it.showSnackbar) {
                    showSnackBar(); homeViewModel.snackbarReset()
                }
                is LiveDataEvents.navigateToDetailViewEvent -> {
                    handleDetailNavigation(it)
                }
            }
        })

        // Give binding object a reference to the adapter
        binding.homeRecyclerview.adapter = adapter

        // Show overflow menu/up button
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun handleDetailNavigation(it: LiveDataEvents.navigateToDetailViewEvent) {
        it.shoppingList?.let {
            navigateToListDetails(it);
            homeViewModel.detailNavigationReset()
        }
    }

    private fun navigateToListDetails(ShoppingList: Long) {
        this.findNavController().navigate(
                HomeFragmentDirections
                        .actionHomeFragmentToDetailFragment(ShoppingList))
    }

    private fun showSnackBar() {
        Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                getString(R.string.cleared_message),
                Snackbar.LENGTH_SHORT // How long to display the message.
        ).show()
    }

    private fun showDialog() {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.list_title_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("List name")
        //show dialog
        val mAlertDialog = mBuilder.show()

        //Save button click of custom layout
        mDialogView.dialog_save_button.setOnClickListener {

            //get text from EditTexts of custom layout
            val name = mDialogView.dialog_list_name.text.toString()

            if (!name.equals("")) {
                mAlertDialog.dismiss()
                homeViewModel.doneEnteringListName(name)
            }

        }
        //cancel button click of custom layout
        mDialogView.dialog_cancel_button.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }

    // Inflate overflow menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu_list, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle overflow menu selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> homeViewModel.actionSelected(optionsMenu.DELETE_ALL_ACITVE_LISTS)
            else -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}

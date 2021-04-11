package com.example.android.shoppingList.archive

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.shoppingList.R
import com.example.android.shoppingList.database.AppDatabase
import com.example.android.shoppingList.databinding.FragmentArchiveBinding
import com.example.android.shoppingList.utils.optionsMenu
import com.google.android.material.snackbar.Snackbar

// A fragment to show archived shopping lists
class ArchiveFragment : Fragment() {

    lateinit var archiveViewModel: ArchiveViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentArchiveBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_archive, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).shoppingListDatabaseDao()
        val viewModelFactory = ArchiveViewModelFactory(dataSource, application)

        archiveViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ArchiveViewModel::class.java)

        // Give binding object a reference to the viewmodel
        binding.archiveViewModel = archiveViewModel
        binding.lifecycleOwner = this

        // Add an Observer on the state variable for showing a Snackbar message
        archiveViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                archiveViewModel.doneShowingSnackbar()
            }
        })

        val archive: Boolean = true;

        // Add an Observer on navigateToListDetails to be notified when to navigate to detail view
        archiveViewModel.navigateToListDetails.observe(viewLifecycleOwner, Observer { ShoppingList ->
            ShoppingList?.let {
                this.findNavController().navigate(
                        ArchiveFragmentDirections.actionArchiveToDetailFragment(ShoppingList, archive))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                archiveViewModel.onListDetailsNavigated()
            }
        })

        val adapter = ArchiveAdapter(ArchiveListener { listID ->
            archiveViewModel.onShoppingListClicked(listID)
        })

        // Give binding object a reference to the adapter
        binding.homeRecyclerview.adapter = adapter

        // Show overflow menu/up button
        setHasOptionsMenu(true)

        return binding.root

    }


    // Inflate overflow menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu_list, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle overflow menu selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> archiveViewModel.actionSelected(optionsMenu.DELETE_ALL_ARCHIVED_LISTS)
            else -> findNavController().navigateUp()
        }
        // detailViewModel.actionSelected(
        return super.onOptionsItemSelected(item)

    }

}
package com.example.simpleproduct.screens

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleproduct.R
import com.example.simpleproduct.adapter.ProductAdapter
import com.example.simpleproduct.base.BaseFragment
import com.example.simpleproduct.databinding.FragmentProductListBinding
import com.example.simpleproduct.model.ProductResponse
import com.example.simpleproduct.utils.InternetHelper
import com.example.simpleproduct.utils.Status
import com.example.simpleproduct.utils.hideKeyboard
import com.example.simpleproduct.view_model.ProductListViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Locale


class ProductListFragment : BaseFragment<FragmentProductListBinding>() {
    private val viewModel: ProductListViewModel by inject()
    private val isNetwork: InternetHelper by inject()
    private lateinit var adapter: ProductAdapter
    private var filterItems = ArrayList<ProductResponse>()

    /**
     * observe the data
     * */
    override fun initObservers(viewLifecycleOwner: LifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        dataBinding.searchView.visibility = View.VISIBLE
                        dataBinding.fabButton.visibility = View.VISIBLE
                        it.data?.let { data ->
                            filterItems = data
                            submitData()
                        }
                        dataBinding.rvProduct.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                        dataBinding.rvProduct.visibility = View.GONE
                        dataBinding.searchView.visibility = View.GONE
                        dataBinding.fabButton.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        //Handle Error
                        dataBinding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    override fun setUp() {
        dataBinding.viewModel = viewModel
        adapter = ProductAdapter {
            // item clicked
        }
        adapter.submitList(arrayListOf())
        dataBinding.rvProduct.adapter = adapter
        dataBinding.fabButton.setOnClickListener {
            findNavController().navigate(R.id.navToAddProductFragment)
        }
        dataBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchWithFilter(newText)
                return false
            }
        })
        // now creating the scroll listener for the recycler view
        dataBinding.rvProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // while scrolling hide the FAB and Search Bar
                if (dy > 10 && dataBinding.fabButton.isShown) {
                    dataBinding.fabButton.hide()
                    dataBinding.searchView.visibility = View.GONE
                }

                // if the recycler view is scrolled above show the FAB and Search Bar
                if (dy < -10 && !dataBinding.fabButton.isShown) {
                    dataBinding.fabButton.show()
                    dataBinding.searchView.visibility = View.VISIBLE
                }
                // if the the recycler view is at the first item always show FAB and Search Bar
                if (!recyclerView.canScrollVertically(-1)) {
                    dataBinding.fabButton.show()
                    dataBinding.searchView.visibility = View.VISIBLE
                }
            }
        })

    }

    /**
     * search the data based on filed value
     * */
    private fun searchWithFilter(text: String?) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<ProductResponse> = ArrayList()

        // running a for loop to compare elements.
        for (item in filterItems) {
            if (text != null) {
                if (item.product_name.lowercase(Locale.ROOT)
                        .contains(text.lowercase(Locale.ROOT))
                ) {
                    filteredlist.add(item)
                }
            }
        }
        if (filteredlist.isEmpty()) {
            hideKeyboard()
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
            adapter.submitList(emptyList())
        } else {
            adapter.filterList(filteredlist)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun submitData() {
        if (isNetwork.isNetworkConnected()) {
            adapter.submitList(filterItems)
            adapter.notifyDataSetChanged()
        } else {
            //
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_product_list


}
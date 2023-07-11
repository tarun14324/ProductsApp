package com.example.simpleproduct.screens

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.simpleproduct.R
import com.example.simpleproduct.adapter.ImageAdaptor
import com.example.simpleproduct.base.BaseFragment
import com.example.simpleproduct.databinding.FragmentAddProductBinding
import com.example.simpleproduct.utils.InternetHelper
import com.example.simpleproduct.utils.Status
import com.example.simpleproduct.utils.collect
import com.example.simpleproduct.utils.showToast
import com.example.simpleproduct.view_model.AddProductViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {

    private val viewModel: AddProductViewModel by inject()
    private val isNetwork: InternetHelper by inject()
    private var imageCount = 0
    private var imagePosition = -1



    /**
     * photo picker launcher
     * */
    private val photoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris != null) {
                val adapter = ImageAdaptor(uris) {
                    viewModel.imageUri.value= uris.getOrNull(0)?.path.toString()
                    imageCount = uris.count()
                    imagePosition = it
                    dataBinding.itemCount = uris.size

                }
                dataBinding.recyclerView.adapter = adapter
            }

        }

    /**
     * getting data from the viewmodel
     * */
    override fun initObservers(viewLifecycleOwner: LifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moveToSelectImageEvent.collect {
                openPhotoPicker()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addProducts.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.message?.let { it1 -> activity?.showToast(it1) }
                        findNavController().popBackStack()
                    }

                    Status.LOADING -> {
                        dataBinding.progressBar.visibility=View.VISIBLE
                    }

                    Status.ERROR -> {
                        it.message?.let { it1 -> activity?.showToast(it1) }
                    }
                }
            }
        }
    }

    /**
     * open launcher to select the only image formats
     * */
    private fun openPhotoPicker() {
        photoPickerLauncher.launch("image/*")
    }

    /**
     * set the initialized data
     * */
    override fun setUp() {
        dataBinding.viewModel = viewModel
        val productType = resources.getStringArray(R.array.product)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, productType)
        dataBinding.autoCompleteTextView.setAdapter(arrayAdapter)
        dataBinding.nextButton.setOnClickListener {
            if (imageCount > imagePosition - 1) {
                dataBinding.recyclerView.scrollToPosition(imagePosition + 1)
            }
        }
        dataBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        dataBinding.previousButton.setOnClickListener {
            if (imagePosition > 1) {
                dataBinding.recyclerView.scrollToPosition(imagePosition - 1)

            }
        }

    }

    override fun getLayoutResource(): Int = R.layout.fragment_add_product


}
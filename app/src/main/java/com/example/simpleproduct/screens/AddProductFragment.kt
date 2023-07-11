package com.example.simpleproduct.screens

import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.simpleproduct.R
import com.example.simpleproduct.adapter.ImageAdaptor
import com.example.simpleproduct.base.BaseFragment
import com.example.simpleproduct.databinding.FragmentAddProductBinding
import com.example.simpleproduct.utils.InternetHelper
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
                viewModel.imageUri.value=uris
                val adapter = ImageAdaptor(uris) {
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
            viewModel.errorMessage.collect {
               activity?.showToast(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moveToShowProductEvent.collect {
               findNavController().popBackStack()
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
        dataBinding.previousButton.setOnClickListener {
            if (imagePosition > 1) {
                dataBinding.recyclerView.scrollToPosition(imagePosition - 1)

            }
        }

    }

    override fun getLayoutResource(): Int = R.layout.fragment_add_product


}
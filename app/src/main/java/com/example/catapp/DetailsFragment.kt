package com.example.catapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.catapp.databinding.FragmentDetailsBinding
import com.example.catapp.ui.viewmodel.CatBreedsViewModel


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    // share catBreedsViewModel object that is scoped to MainActivity
    private lateinit var viewModel: CatBreedsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // use view binding in fragment
        _binding = FragmentDetailsBinding.bind(view)
        viewModel = (activity as MainActivity).catBreedsViewModel
        val args: DetailsFragmentArgs by navArgs()
        val breedId = args.selectedCatBreedId
        viewCatBreed(breedId)
    }

    private fun viewCatBreed(breedId: String) {
        // get cat breeds from API
        viewModel.getCatBreed(breedId)
        // observe catBreeds LiveData
        viewModel.catBreed.observe(viewLifecycleOwner) { response ->
            when (response) {
                // when response is successful
                is com.example.catapp.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        _binding!!.tvName.text = it[0].breeds?.get(0)?.name
                        _binding!!.tvDescription.text = it[0].breeds?.get(0)?.description
                        Glide.with(_binding!!.ivCatBreedImage.context).load(it[0].url)
                                .into(_binding!!.ivCatBreedImage)
                    }
                }
                // when response is error
                is com.example.catapp.data.util.Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                // when response is loading
                is com.example.catapp.data.util.Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        _binding!!.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        _binding!!.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
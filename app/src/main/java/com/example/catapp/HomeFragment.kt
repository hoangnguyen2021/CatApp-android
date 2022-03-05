package com.example.catapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.databinding.FragmentHomeBinding
import com.example.catapp.ui.adapter.CatBreedsAdapter
import com.example.catapp.ui.viewmodel.CatBreedsViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // share catBreedsViewModel object that is scoped to MainActivity
    private lateinit var viewModel: CatBreedsViewModel
    private lateinit var catBreedsAdapter: CatBreedsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // use view binding in fragment
        _binding = FragmentHomeBinding.bind(view)
        viewModel = (activity as MainActivity).catBreedsViewModel
        catBreedsAdapter = (activity as MainActivity).catBreedsAdapter
        catBreedsAdapter.setOnItemClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToDetailsFragment(it)
            findNavController().navigate(action)
        }
        initRecyclerView()
        viewCatBreeds()
    }

    private fun initRecyclerView() {
        _binding!!.rvCatBreeds.apply {
            // set adapter for recycler view
            adapter = catBreedsAdapter
            // set layout for recycler view
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun viewCatBreeds() {
        // get cat breeds from API
        viewModel.getCatBreeds()
        // observe catBreeds LiveData
        viewModel.catBreeds.observe(viewLifecycleOwner) { response ->
            when (response) {
                // when response is successful
                is com.example.catapp.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        // pass the article list to the AdapterHelper
                        catBreedsAdapter.differ.submitList(it)
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
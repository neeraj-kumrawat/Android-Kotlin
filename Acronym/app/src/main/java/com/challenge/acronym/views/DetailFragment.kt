package com.challenge.acronym.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.acronym.R
import com.challenge.acronym.adapters.AcronymAdapter
import com.challenge.acronym.api.RetrofitService
import com.challenge.acronym.databinding.FragmentDetailBinding
import com.challenge.acronym.repository.MainRepository
import com.challenge.acronym.room.AcronymDatabase
import com.challenge.acronym.utilities.Utilities
import com.challenge.acronym.viewmodel.MainViewModel
import com.challenge.acronym.viewmodel.MyViewModelFactory

/**
 * A simple [Fragment] subclass, Second screen where user will get the all meanings of acronym.
 * This is the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var acronymAdapter: AcronymAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup recycler view and attach with adapter
        setupRecyclerView()

        // Initialize viewModel
        initializeViewModel()

        // Observer the acronym meaning api response
        setupObserver()
    }

    private fun setupRecyclerView() {
        // Create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.rvAcronyms.layoutManager = layoutManager
        acronymAdapter = AcronymAdapter()
        // attach adapter to the recycler view
        binding.rvAcronyms.adapter = acronymAdapter
    }

    private fun initializeViewModel() {
        val acronymDatabase = AcronymDatabase.getDatabase(requireContext())
        viewModel =
            ViewModelProvider(
                this,
                MyViewModelFactory(
                    MainRepository(retrofitService, acronymDatabase),
                    acronymDatabase
                )
            )[MainViewModel::class.java]
    }

    private fun setupObserver() {
        viewModel.acronymsDetailList.observe(viewLifecycleOwner) {

            if (it.isNullOrEmpty()) {
                binding.textviewAcronymsFound.text = getString(R.string.no_acronyms_found)
            } else {
                acronymAdapter.setItems(it[0].lfs)
                val word: String = when (it[0].lfs.size) {
                    0 -> {
                        getString(R.string.no_acronyms_found)
                    }
                    1 -> {
                        getString(R.string.one_acronyms_found)
                    }
                    else -> {
                        "${it[0].lfs.size} ${getString(R.string.many_acronyms_found)}"
                    }
                }
                binding.textviewAcronymsFound.text = word
            }

        }

        viewModel.isLoading.observe(this) {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.textviewAcronymsFound.text = getString(R.string.api_error)
        }
        viewModel.getAbbreviations(arguments?.getString(Utilities.BUNDLE_KEY).toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
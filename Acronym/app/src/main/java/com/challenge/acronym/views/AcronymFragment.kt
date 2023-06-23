package com.challenge.acronym.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.acronym.R
import com.challenge.acronym.adapters.AcronymHistoryAdapter
import com.challenge.acronym.room.AcronymDatabase
import com.challenge.acronym.api.RetrofitService
import com.challenge.acronym.databinding.FragmentAcronymBinding
import com.challenge.acronym.repository.MainRepository
import com.challenge.acronym.utilities.Utilities
import com.challenge.acronym.viewmodel.MainViewModel
import com.challenge.acronym.viewmodel.MyViewModelFactory
import com.google.android.material.textfield.TextInputEditText


/**
 * A simple [Fragment] subclass, First screen where user can enter acronym and get the history of
 * previous searches. This is the default destination in the navigation.
 */
class AcronymFragment : Fragment() {

    private var _binding: FragmentAcronymBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var viewModel: MainViewModel
    private lateinit var acronymAdapter: AcronymHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAcronymBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view add listener
        initializeViews()

        // Initialize viewModel
        initializeViewModel()

        // Observer that will receive the history events from room
        setupHistoryObserver()
    }

    private fun initializeViews() {
        binding.buttonSearch.setOnClickListener {
            validateAndNavigate(binding.etAcronym.text.toString())
        }
        addedImeOptions(binding.etAcronym)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = GridLayoutManager(requireContext(), 4)
        acronymAdapter = AcronymHistoryAdapter()
        // attach adapter to the recycler view
        binding.rvHistory.adapter = acronymAdapter
    }

    private fun validateAndNavigate(acronym: String) {
        if (!isValidated(acronym)) return
        findNavController().navigate(
            R.id.action_AcronymFragment_to_DetailFragment,
            bundleOf(Utilities.BUNDLE_KEY to acronym)
        )
    }

    private fun isValidated(acronym: String): Boolean {
        if (acronym.isEmpty()) {
            binding.etAcronym.error = getString(R.string.empty_error)
            return false
        } else if (!Utilities.isInternetAvailable(requireContext())) {
                binding.etAcronym.error = getString(R.string.network_error)
                return false
            }
        return true
    }


    private fun addedImeOptions(etAcronym: TextInputEditText) {
        etAcronym.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                validateAndNavigate(etAcronym.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
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

    private fun setupHistoryObserver() {
        viewModel.abbreviationHistory.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) acronymAdapter.setItems(it)
        }
        viewModel.getAbbreviationHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
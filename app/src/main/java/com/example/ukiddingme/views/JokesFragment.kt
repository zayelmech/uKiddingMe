package com.example.ukiddingme.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukiddingme.R
import com.example.ukiddingme.adapter.JokesAdapter
import com.example.ukiddingme.databinding.FragmentJokesBinding
import com.example.ukiddingme.utils.UIState
import dagger.hilt.android.AndroidEntryPoint


class JokesFragment : BaseFragment() {

    private val binding by lazy {
        FragmentJokesBinding.inflate(layoutInflater)
    }

    private val jokesAdapter by lazy{
        JokesAdapter{
            //  todo here i ....
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.jokesRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = jokesAdapter
        }

        jokesViewModel.jokes.observe(viewLifecycleOwner){ state->
            when(state){
                is UIState.LOADING ->{
                    //LOADING SPINNER
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "LOADING")
                    binding.loadingSpinner.visibility = View.VISIBLE
                    binding.jokesRecycler.visibility = View.GONE
                }
                is UIState.SUCCESS ->{
                    //UPDATE RECYCLERVIEW
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "SUCCESS")

                    binding.loadingSpinner.visibility = View.GONE
                    binding.jokesRecycler.visibility = View.VISIBLE
                    jokesAdapter.updateNewJokes(state.response.value ?: emptyList())
                }
                is UIState.ERROR ->{
                    //SHOW ERROR DIALOG
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "ERROR")

                    binding.loadingSpinner.visibility = View.GONE
                    binding.jokesRecycler.visibility = View.GONE

                    showError(state.error.localizedMessage){
                        jokesViewModel.getJokesSet(20)
                    }
                }
            }

        }
        jokesViewModel.getJokesSet(20)
        return binding.root
    }

}
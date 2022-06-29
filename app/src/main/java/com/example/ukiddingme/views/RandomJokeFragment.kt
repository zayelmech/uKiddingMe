package com.example.ukiddingme.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.ukiddingme.R
import com.example.ukiddingme.databinding.FragmentRandomJokeBinding
import com.example.ukiddingme.model.SingleJoke
import com.example.ukiddingme.utils.UIState
import com.example.ukiddingme.viewmodel.JokesViewModel


class RandomJokeFragment : Fragment() {

    private val binding by lazy {
        FragmentRandomJokeBinding.inflate(layoutInflater)
    }
    private val jokesViewModel by lazy {
        ViewModelProvider(requireActivity())[JokesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        jokesViewModel.randomJoke.observe(viewLifecycleOwner){ state ->
            when(state){
                is UIState.LOADING ->{
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "LOADING")

                }
                is UIState.SUCCESS<*> ->{
                    (state as UIState.SUCCESS<SingleJoke>).response

                    binding.jokeTextView.text = state.response.value.joke

                }
                is UIState.ERROR ->{
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "ERROR")

                }
            }

        }

        jokesViewModel.getRandomJoke()

        binding.randomBtn.setOnClickListener {
            jokesViewModel.getRandomJoke()
        }

        return binding.root
    }

}
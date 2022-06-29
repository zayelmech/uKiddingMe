package com.example.ukiddingme.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ukiddingme.databinding.FragmentInputBinding
import com.example.ukiddingme.model.SingleJoke
import com.example.ukiddingme.utils.UIState
import com.example.ukiddingme.viewmodel.JokesViewModel

class InputFragment : Fragment() {

    private val binding by lazy {
        FragmentInputBinding.inflate(layoutInflater)
    }

    private val jokesViewModel by lazy {
        ViewModelProvider(requireActivity())[JokesViewModel::class.java]
    }

    private lateinit var newDialog: DialogMessage

    private var flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.submitButton.setOnClickListener {
            Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "SUBMIT WAS CLICKED")

            val name = binding.textInputName.text?.toString()
            val lastname = binding.textInputLastname.text?.toString()
            jokesViewModel.getCustomJoke(name?:"John", lastname?:"Doe")

            flag=true
        }

        jokesViewModel.customJoke.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.LOADING -> {
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "LOADING")
                    binding.loadingSpinner.visibility = View.VISIBLE

                }
                is UIState.SUCCESS<*> -> {
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "SUCCESS")

                    (state as UIState.SUCCESS<SingleJoke>).response
                    binding.loadingSpinner.visibility = View.GONE

                    newDialog = DialogMessage(state.response.value.joke)

                    if (flag) {
                        newDialog.show(
                            childFragmentManager,
                            DialogMessage.TAG
                        )
                    }


                    //binding.instructions.text = state.response.value.joke
                }
                is UIState.ERROR -> {
                    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "ERROR")

                    binding.loadingSpinner.visibility = View.GONE

                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
            flag = false
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "DESTROYED")
    }
}


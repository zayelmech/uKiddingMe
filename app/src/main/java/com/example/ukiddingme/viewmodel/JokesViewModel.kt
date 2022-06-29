package com.example.ukiddingme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ukiddingme.network.JokesRepository
import com.example.ukiddingme.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository

) : ViewModel() {

    private val _jokes: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val jokes: LiveData<UIState> get() = _jokes

    fun getJokesSet(qty: Int? = null) {
        //this is a global coroutine scope
        CoroutineScope(Dispatchers.IO).launch {
            //here you are in the worker thread
            delay(1000)
            try {
                val response = jokesRepository.getJokes(qty)
                if (response.isSuccessful) {
                    //here you still in worker thread
                    response.body()?.let {
                        //here you might need to change to main thread
                        withContext(Dispatchers.Main) {
                            //here you moved to the main thread
                            //you update de UI
                            _jokes.value = UIState.SUCCESS(it)

                        }
                        //propagate the success
                    } ?: throw Exception("DATA IS NULL")
                } else {
                    throw Exception(response.errorBody()?.toString())
                }

            } catch (e: Exception) {
                //propagate the error
                withContext(Dispatchers.Main) {
                    //here you moved to the main thread
                    //you update de UI
                    _jokes.value = UIState.ERROR(e)
                }
            }


        }

    }


    override fun onCleared() {
        super.onCleared()
        // here you remove the viewModel
    }
}
package com.example.ukiddingme.viewmodel

import android.util.Log
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

    private val _customJoke: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val customJoke : LiveData<UIState> get() = _customJoke

    private val _randomJoke: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val randomJoke : LiveData<UIState> get() = _randomJoke

    fun getJokesSet(qty: Int? = null) {
        //this is a global coroutine scope
        CoroutineScope(Dispatchers.IO).launch {
            //here you are in the worker thread
            delay(500)
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

    fun getCustomJoke(firstName: String, lastName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            try {
                val response = jokesRepository.getCustomJoke(firstName,lastName)
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) {
                            _customJoke.value = UIState.SUCCESS(it)
                        }
                    } ?: throw Exception("DATA IS NULL")
                } else {
                    throw Exception(response.errorBody()?.toString())

                }
            } catch (e: Exception) {
                Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", e.message.toString())

                withContext(Dispatchers.Main) {
                    _customJoke.value = UIState.ERROR(e)
                }
            }
        }
    }

    fun getRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            try {
                val response = jokesRepository.getRandomJoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) {
                            _randomJoke.value = UIState.SUCCESS(it)
                        }
                    } ?: throw Exception("DATA IS NULL")
                } else {
                    throw Exception(response.errorBody()?.toString())

                }
            } catch (e: Exception) {
                Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", e.message.toString())

                withContext(Dispatchers.Main) {
                    _randomJoke.value = UIState.ERROR(e)
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()

        // here you remove the viewModel
    }
}
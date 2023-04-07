package boot.counter.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import boot.counter.domain.GetBootEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    val getBootEventsUseCase: GetBootEventsUseCase
): ViewModel(), CoroutineScope {

    private val _bootMutableLiveData = MutableLiveData<String>()
    val bootLiveData: LiveData<String>
        get() = _bootMutableLiveData

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job + CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(TAG, "${throwable.message}")
        }

    var job = Job()

    fun getBootList() {
        launch {
            val bootList = getBootEventsUseCase.execute()
            Log.d(TAG, "BootList: $bootList")
            when(bootList.size){
                0 -> _bootMutableLiveData.postValue("No boots detected")
                else -> {
                    val resultString = StringBuilder()
                    bootList.forEachIndexed { index, l ->
                        resultString.append("${index + 1} - $l\n")
                    }
                    _bootMutableLiveData.postValue(resultString.toString())
                }
            }
        }
    }

    companion object{
        private const val TAG = "MainViewModel"
    }

}
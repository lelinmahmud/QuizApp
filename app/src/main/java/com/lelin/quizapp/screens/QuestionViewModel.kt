package com.lelin.quizapp.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelin.quizapp.data.DataOrException
import com.lelin.quizapp.model.QuestionItem
import com.lelin.quizapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) : ViewModel(){

    val data: MutableState<DataOrException<ArrayList<QuestionItem>,Boolean,Exception>> =
        mutableStateOf(DataOrException(null,true,Exception("")))

    init {
        getAllQuestion()
    }

    private fun getAllQuestion(){
        viewModelScope.launch {
            data.value.loading = true
            data.value = questionRepository.getAllQuestion()
            if (data.value.data.toString().isNotEmpty()){
                data.value.loading = false
            }
        }
    }

}
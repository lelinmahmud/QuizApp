package com.lelin.quizapp.repository

import android.util.Log
import com.lelin.quizapp.data.DataOrException
import com.lelin.quizapp.model.QuestionItem
import com.lelin.quizapp.network.QuestionApi
import javax.inject.Inject
import kotlin.Exception


class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {

    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>,
                Boolean,
                Exception>()

    suspend fun getAllQuestion(): DataOrException<ArrayList<QuestionItem>,Boolean,Exception>{
        try {
            dataOrException.loading = true
            dataOrException.data = questionApi.getAllQuestion()

            if (dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading = false
            }

        } catch (e: Exception){
            dataOrException.e = e
            Log.e("TAG", "getAllQuestion: error is ${dataOrException.e!!.localizedMessage}", )
        }

        return dataOrException
    }

}
package com.example.trab_sd

import kotlinx.coroutines.*

abstract class CoroutineAsyncTask<Params, Progress, Result>{

    open fun onPreExecute() { } //executa na thread principal
    abstract fun doInBackground(vararg params: String?): String? //executa na thread de segundo plano
    open fun onPostExecute(result: String?) { } //executa na thread principal

    fun execute() {

        GlobalScope.launch(Dispatchers.Main) {
            onPreExecute()
        }

        GlobalScope.launch(Dispatchers.Default) {

            while (NonCancellable.isActive) {
                val result = doInBackground()
                delay(5000)

                withContext(Dispatchers.Main) {
                    onPostExecute(result)
                }

            }

        }

    }

}
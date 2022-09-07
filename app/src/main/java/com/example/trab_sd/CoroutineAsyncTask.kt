package com.example.trab_sd

import kotlinx.coroutines.*

abstract class CoroutineAsyncTask<Params, Progress, Result>{

    open fun onPreExecute() { } //executa na thread principal
    abstract fun doInBackground(vararg params: String?): String? //executa na thread de fundo
    open fun onPostExecute(result: String?) { } //executa na thread principal

    fun execute() {

        GlobalScope.launch(Dispatchers.Main) {
            onPreExecute()
        }

        GlobalScope.launch(Dispatchers.Default) {
            val result = doInBackground()

            withContext(Dispatchers.Main) {
                onPostExecute(result)
            }
        }
    }
}
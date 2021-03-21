package mx.com.charlyescaz.web.api

import android.util.Log
import com.google.gson.GsonBuilder
import mx.com.charlyescaz.web.BuildConfig
import mx.com.charlyescaz.web.models.FileWS
import mx.com.charlyescaz.web.models.GenericResponseWS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object APIColaboradores {

    private const val TAG = "API_COLABORADORES"
    private val apiService: ColaboradoresService

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)

        val gson = GsonBuilder()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.api_base)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientBuilder.build())
            .build()

        apiService = retrofit.create(ColaboradoresService::class.java)
    }

    private fun <T>doRequest(operation: String, call: Call<T>, cb: (success: Boolean, data: T?) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                handleFailure(operation,t,cb)
            }
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    cb(true, response.body())
                } else {
                    handleUnsuccessful(operation, cb)
                }

            }

        })
    }

    private fun <T>handleUnsuccessful(operation: String,callback: (success: Boolean,data: T?) -> Unit) {
        Log.w(TAG,"$operation was unsuccessful")
        callback(false, null)
    }

    private fun <T>handleFailure(operation: String, t: Throwable,callback: (success: Boolean,data: T?) -> Unit) {
        Log.e(TAG, "$operation has failed")
        Log.e(TAG, "Message is: " + t.message)
        callback(false, null)
    }

    fun getPartners(cb: (success: Boolean, data: GenericResponseWS<FileWS>?) -> Unit) {
        doRequest(
            "Get Partners Data",
            apiService.getPartners(),
            cb
        )
    }
}
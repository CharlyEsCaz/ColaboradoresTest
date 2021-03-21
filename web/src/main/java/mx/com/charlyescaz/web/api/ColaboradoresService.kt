package mx.com.charlyescaz.web.api

import mx.com.charlyescaz.web.BuildConfig
import mx.com.charlyescaz.web.models.FileWS
import mx.com.charlyescaz.web.models.GenericResponseWS
import retrofit2.Call
import retrofit2.http.GET

interface ColaboradoresService {

    @GET("${BuildConfig.api_base}/s/5u21281sca8gj94/getFile.json?dl=0")
    fun getPartners(): Call<GenericResponseWS<FileWS>>
}
package co.kr.nawa.simpleportfolio.util.async

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.Response

import retrofit2.http.*
import java.util.*

interface ApiData {

    /*
    *   QueryMap == get에서 주소에 붙이
    *   FieldMap
    *   Body == 보디에 바로 던지기 (json)
    * */



    //json
    @GET
    fun getTOJosn(
        @Url url: String,
        @QueryMap body: HashMap<String,String>
    ): Observable<JsonElement>


    //json
    @Headers("Content-Type: application/json")
    @POST
    fun getJosn(
            @Url url: String,
            @Body body: HashMap<String,String>
    ): Observable<JsonElement>


    @Headers("Content-Type: application/json")
    @GET
    fun getType(
        @Url url: String,
        @QueryMap body: HashMap<String,String>
    ): Observable<JsonElement>


    @Headers("Content-Type: application/json")
    @POST
    fun getJosn2(
        @Url url: String,
        @Body body: HashMap<String,String>
    ): Observable<JsonElement>


    @Headers("Content-Type: application/json")
    @POST
    fun postToJson(
        @Url url: String,
        @HeaderMap headers: MutableMap<String,Any>,
        @Body body: MutableMap<String,Any>
    ): Observable<Response<JsonElement>>
}
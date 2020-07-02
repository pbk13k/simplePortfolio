package co.kr.nawa.simpleportfolio.util.async


import co.kr.nawa.simpleportfolio.BuildConfig
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

class RestHelper(var type: Type) {

    private val CONNECT_TIMEOUT = 100
    private val WRITE_TIMEOUT = 100
    private val READ_TIMEOUT = 100
    private val ERROR_MESSAGE = "에러가 발생했습니다."

    enum class Type constructor(val value: String) {
        MAIN("https://api.punkapi.com/v2/"),
        SUB("http://nawa.dothome.co.kr/api/")
    }

//    private var API_BASE_URL = "https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/"
    private var API_BASE_URL1 = "http://nawa.dothome.co.kr/api/"
    private var API_BASE_URL = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/"


    fun getRetrofit(): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor (object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                return chain.proceed(chain.request().newBuilder().build())

            }
        })
        httpClientBuilder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }

        val okHttpClient = httpClientBuilder.build()
        val baseUrl=type.value



        return Retrofit.Builder()
            .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient).build()
    }

}

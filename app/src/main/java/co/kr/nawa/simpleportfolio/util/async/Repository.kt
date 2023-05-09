package co.kr.nawa.simpleportfolio.util.async

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import co.kr.nawa.simpleportfolio.item.ResponseItem
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import co.kr.nawa.simpleportfolio.util.common.logD
import retrofit2.HttpException
import retrofit2.Response

class Repository(val retrofit: RestHelper) {

    val api: Api_Data

    init {
        api=retrofit.getRetrofit().create(Api_Data::class.java)
    }

    fun getTojsonOne(url:String,hashMap: HashMap<String,String>,key:String): Single<String> {
        val apilist  =api.getTOJosn(url,hashMap).map { res:JsonElement ->

//            var body=res.toString()

//            var typeToken=TypeToken.getParameterized(ArrayList::class.java, classOfT).type

//            var any:ArrayList<T> = Gson().fromJson(body,typeToken)

            return@map res.asJsonObject.get(key).asString
        }
        return apilist
    }


    fun <T>postToJsonObj(
        url:String,
        headers: MutableMap<String, Any>,
        hashMap: MutableMap<String,Any>,
        classOfT:Class<T>, key:String): Single<ResponseItem<T>> {

        return api.postToJson(url,headers,hashMap).map { res : Response<JsonElement> ->  T
            if (res.isSuccessful){

                if (res.body()!=null){
                    var body=res.body()!!.asJsonObject.get(key).asJsonObject.toString()
//                    return@map Gson().fromJson(body,classOfT)
                    return@map  ResponseItem(res.code(),Gson().fromJson(body,classOfT))
                }else{
                    throw HttpException(res)
                }
            }else{
                throw HttpException(res)
            }
        }
    }

    fun <T>  getTojson(url:String,hashMap: HashMap<String,String>,classOfT:Class<T>): Single<ArrayList<T>> {
        val apilist  =api.getTOJosn(url,hashMap).map { res:JsonElement ->  T

            var body=res.toString()

            var typeToken=TypeToken.getParameterized(ArrayList::class.java, classOfT).type

            var any:ArrayList<T> = Gson().fromJson(body,typeToken)

            return@map any
        }
        return apilist
    }

    //stores
    fun <T>  getjson(url:String,hashMap: HashMap<String,String>,classOfT:Class<T>): Single<ArrayList<T>> {
        val apilist  =api.getJosn(url,hashMap).map { res:JsonElement ->  T
            //var any:T=Gson().fromJson(res,classOfT)

//            var body=res.asJsonObject.get("list").asJsonArray.toString()
            var body=res.toString()
            //logD(body)
//            var any:T=Gson().fromJson(body,classOfT)
//            var typeToken=object:TypeToken<ArrayList<T>>(){}.type
            var typeToken=TypeToken.getParameterized(ArrayList::class.java, classOfT).type

            var any:ArrayList<T> = Gson().fromJson(body,typeToken)

            return@map any
        }
        return apilist
    }


    fun <T>getType(url:String,hashMap: HashMap<String,String>,classOfT:Class<T>): Single<ArrayList<T>> {
        val apilist  =api.getType(url,hashMap).map { res:JsonElement ->  T

            var body=res.asJsonObject.get("stores").asJsonArray.toString()
            logD(body)
//            var any:T=Gson().fromJson(body,classOfT)
            var typeToken=TypeToken.getParameterized(ArrayList::class.java, classOfT).type
            var any:ArrayList<T> = Gson().fromJson(body,typeToken)
            logD(any.get(0).toString())
            return@map any
        }
        return apilist
    }

    fun <T>getlist2(url:String,hashMap: HashMap<String,String>,classOfT:Class<T>): Single<T> {
        val apilist  =api.getJosn2(url,hashMap).map { res:JsonElement ->  T
            var any:T=Gson().fromJson(res,classOfT)

            return@map any
        }
        return apilist
    }


}
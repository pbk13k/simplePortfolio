package co.kr.nawa.simpleportfolio.util.login


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.item.SnsItem
import co.kr.nawa.simpleportfolio.util.`fun`.logD
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth


class Google_Login(fact: FragmentActivity, private val callback: (SnsItem) -> Unit) {

    private val TAG = "Google_Login"
    private val context: Context = fact.applicationContext
    private val googleSignInClient: GoogleSignInClient
    private val mFirebaseAuth = FirebaseAuth.getInstance()
    var fact:FragmentActivity = fact
    var fragment: Fragment?=null

    companion object {
       val google_Login_num=50
    }




    constructor(fragment: Fragment,fact: FragmentActivity,callback: (SnsItem) -> Unit) : this(fact,callback) {
        this.fragment=fragment
    }



    init {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(fact, gso);


//        mGoogleApiClient = GoogleApiClient.Builder(context)
//            .enableAutoManage(fact) { connectionResult -> Log.d(TAG, "onConnectionFailed:$connectionResult") }
//            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//            .build()
    }

    fun login() {

//        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleSignInClient)
        val signInIntent = googleSignInClient.signInIntent
        if(fragment==null){
            fact.startActivityForResult(signInIntent, google_Login_num)
        }else{
            fragment!!.startActivityForResult(signInIntent, google_Login_num)
        }


    }

    //접속후 확인
    fun signInWithCredential(credential: AuthCredential) {

        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                val mFirebaseUser = mFirebaseAuth.currentUser


                Log.i("getDisplayName",mFirebaseUser?.getDisplayName());
                //Log.i("getEmail",mFirebaseUser.getEmail());

//                msg.obj = mFirebaseUser!!.email

//                data.putString("name", mFirebaseUser.displayName)
                if (mFirebaseUser!=null){
                    callback(SnsItem(mFirebaseUser.email.toString(),SnsItem.Type.GOOGLE,true))
                }else{
                    callback(SnsItem("email 없음",SnsItem.Type.GOOGLE))
                }


            } else {
                val request = "요청 실패 되었습니다."
                logD(request)
                callback(SnsItem("",SnsItem.Type.GOOGLE))
                Log.i(TAG, "signInWithCredential", it.exception)
                //Toast.makeText(context, "Authentication failed.",Toast.LENGTH_SHORT).show()
            }
        }




    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}



 package com.example.bumpybelly

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import java.util.*
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


 class MainActivity : AppCompatActivity() {

     val MY_REQUEST_CODE: Int = 7117
     lateinit var providers : List<AuthUI.IdpConfig>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),// email build
            AuthUI.IdpConfig.FacebookBuilder().build(),// fb build
            AuthUI.IdpConfig.GoogleBuilder().build(),// Google build
            AuthUI.IdpConfig.PhoneBuilder().build()// Phone build
        )
        showSignInOptions()

        //event
        sign_out.setOnClickListener {
            //signout
            AuthUI.getInstance().signOut(this@MainActivity)
                .addOnCompleteListener {
                    sign_out.isEnabled = false
                    showSignInOptions()
                }
                .addOnFailureListener {
                    e ->   Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
        }

    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if(requestCode == MY_REQUEST_CODE ){

             val response = IdpResponse.fromResultIntent(data)
             if(resultCode == Activity.RESULT_OK){
                 val user = FirebaseAuth.getInstance().currentUser // get the current user
                 Toast.makeText(this, ""+user!!.email,Toast.LENGTH_SHORT).show()

                 sign_out.isEnabled = true
             }
             else{
                 Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
             }

         }
     }

     private fun showSignInOptions(){
         startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
             .setAvailableProviders(providers)
             .setLogo(R.drawable.pragnant)
             .setTheme(R.style.MyTheme)
             .build(), MY_REQUEST_CODE
         )
     }
}

package com.bjones.firebasetest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Error
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var providers : List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 7111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        ShowSignInOptions()

        btnSignOut.setOnClickListener{
            AuthUI.getInstance().signOut(this@MainActivity)
            ShowSignInOptions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_REQUEST_CODE)
        {
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK)
            {
                val user = FirebaseAuth.getInstance().currentUser;
                Toast.makeText(this,"" + user!!.email, Toast.LENGTH_SHORT).show()

                btnSignOut.isEnabled = true
            }
            else
            {
                Toast.makeText(this,"" + response!!.error!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ShowSignInOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme(R.style.MyTheme).build(),MY_REQUEST_CODE)
    }


}


package com.example.bumpy_belly2


    import android.app.Activity
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.Toast
    import androidx.navigation.NavController
    import androidx.navigation.Navigation
    import com.firebase.ui.auth.AuthUI
    import com.firebase.ui.auth.IdpResponse
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.ktx.firestore
    import com.google.firebase.ktx.Firebase
    import kotlinx.android.synthetic.main.activity_main.*
    import kotlinx.android.synthetic.main.fragment_home_page.*
    import java.util.*

    class MainActivity : AppCompatActivity() {
        var navController: NavController? = null

        var Nalogin: Boolean = false
        val MY_REQUEST_CODE: Int = 7117
        lateinit var providers : List<AuthUI.IdpConfig>



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            providers = Arrays.asList<AuthUI.IdpConfig>(
                AuthUI.IdpConfig.EmailBuilder().build(),// email build
                AuthUI.IdpConfig.GoogleBuilder().build(),// Google build
                AuthUI.IdpConfig.PhoneBuilder().build()// Phone build
            )


            // showSignInOptions()
        }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if(requestCode == MY_REQUEST_CODE ){

                val response = IdpResponse.fromResultIntent(data)
                if(resultCode == Activity.RESULT_OK){
                    val user = FirebaseAuth.getInstance().currentUser // get the current user
                    Toast.makeText(this, ""+user!!.email,Toast.LENGTH_SHORT).show()

                   val user2 = hashMapOf(
                        "FirstName" to "Ada",
                       "LastName" to "Lovelace",
                        "UserId" to "1815"
                    )
                    val db = Firebase.firestore
                    db.collection("Users").add(user2)

                    //weet niet of dit een goeie methode is om het te doen
                    nav_host_fragment.activity!!.setContentView(R.layout.fragment_home_page)
                }
                else{
                    Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
                }

            }
        }

        fun signout(){
            sign_out.setOnClickListener {
                //signout
                AuthUI.getInstance().signOut(this@MainActivity)
                    .addOnCompleteListener {
                        sign_out.isEnabled = false
                       // showSignInOptions()
                        //nav_host_fragment.activity!!.setContentView(R.layout.fragment_home_page)

                    }
                    .addOnFailureListener {
                            e ->   Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        fun showSignInOptions(){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.pragnant)
                .setTheme(R.style.MyTheme)
                .build(), MY_REQUEST_CODE
            )
        }
    }

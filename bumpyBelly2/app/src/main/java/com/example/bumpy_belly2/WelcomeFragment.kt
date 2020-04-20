package com.example.bumpy_belly2

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_welcome.view.*



class WelcomeFragment : Fragment() {
    var navController: NavController? = null

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    fun CheckPregnancie(){
        //De pregnancie uit de database
        val docRef = db.collection("Pregnanties").document(user?.uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    //Hier heeft hij de pregnancie gevonden.
                    //In deze case mag hij niet naar register form gaan
                    Log.d(MainActivity.TAG, "DocumentSnapshot data: ${document.data}")
                    navController!!.navigate(R.id.action_welcomeFragment_to_homePage)
                } else {
                    navController!!.navigate(R.id.action_welcomeFragment_to_zwangerschapRegistratieFragment)

                }
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.Login.setOnClickListener {
            (activity as MainActivity).showSignInOptions()
           }
        view.btnGa.setOnClickListener {
            //(activity as MainActivity).CheckPregnancie()
          CheckPregnancie()
            //navController!!.navigate(R.id.action_welcomeFragment_to_zwangerschapRegistratieFragment)
        }



        // Inflate the layout for this fragment
        return view

    }



}

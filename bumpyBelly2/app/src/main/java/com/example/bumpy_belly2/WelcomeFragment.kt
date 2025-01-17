package com.example.bumpy_belly2

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bumpy_belly2.MainActivity.Companion.TAG
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class WelcomeFragment : Fragment() {
    var navController: NavController? = null

    //Firestore database
    val db = FirebaseFirestore.getInstance()

    //De ingelogde user
    val user = FirebaseAuth.getInstance().currentUser

    var globalWekenKind = 0

    var zwangerschapId = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    //Bool om te laten weten of pregnancie bestaat of niet
    var gevonden = false

    //Kijk in database of pregnancie met uid van user al bestaat.
    // Zo ja ga naar homescherm
    //Zo nee ga naar registerscherm
    @RequiresApi(Build.VERSION_CODES.O)
    fun CheckPregnancie() {
        db.collection("Users").document(user?.uid.toString()).collection("Pregnanties")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document["Actief"] == true){

                        var formatter = DateTimeFormatter.ofPattern("dd MM yyyy")

                        val StartDate = LocalDate.parse(document["StartDate"].toString(), formatter)

                        var WekenKind  = ChronoUnit.WEEKS.between(StartDate, LocalDate.now()).toInt()
                        Log.d(TAG, WekenKind.toString())
                        Log.d(TAG, "weken kind  oproepen ${WekenKind}")

                        globalWekenKind = WekenKind
                        zwangerschapId = document.id

                        gevonden = true

                        //Check huidige locatie.
                        if (navController!!.currentDestination?.id != R.id.action_welcomeFragment_to_homePage) {
                            vulFacts()
                            navController!!.navigate(R.id.action_welcomeFragment_to_homePage)
                        }

                    }
                }
                if(!gevonden){

                    navController!!.navigate(R.id.action_welcomeFragment_to_zwangerschapRegistratieFragment)

                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }
    /************************************************************************************/

    @RequiresApi(Build.VERSION_CODES.O)
    fun vulFacts(){

        var MainAct = (activity as MainActivity)
        MainAct.HaalPregnancieOp(zwangerschapId)
        MainAct.GeefFactsEnFotoWeer(globalWekenKind)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.Login.setOnClickListener {
            (activity as MainActivity).showSignInOptions()
           }
        view.btnGa.setOnClickListener {
            //Kijk of pregnancie al bestaat of niet
            CheckPregnancie()
        }
        // Inflate the layout for this fragment
        return view

    }
}

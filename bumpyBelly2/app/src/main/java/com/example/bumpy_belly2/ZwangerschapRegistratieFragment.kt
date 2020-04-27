package com.example.bumpy_belly2

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import kotlinx.android.synthetic.main.fragment_zwangerschap_registratie.view.*
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_zwangerschap_registratie.*
import kotlinx.android.synthetic.main.fragment_zwangerschap_registratie.view.txtWeeks
import java.util.*


class ZwangerschapRegistratieFragment : Fragment() {

    var navController: NavController? = null
    private lateinit var calendar:Calendar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_zwangerschap_registratie, container, false)

        //Register zwangerschap
        view.btnRegister.setOnClickListener {
            if (txtFirstName.text.isNotEmpty() && txtLastName.text.isNotEmpty()){
            navController!!.navigate(R.id.action_zwangerschapRegistratieFragment_to_homePage)
            (activity as MainActivity).GeefFactsWeer()

            val user = FirebaseAuth.getInstance().currentUser
            val db = FirebaseFirestore.getInstance()
            val testUser = hashMapOf(
                "FirstName" to txtFirstName.text.toString(),
                "StartDate" to (txtWeeks.dayOfMonth.toString() + " " + txtWeeks.month.toString() + " " + txtWeeks.year.toString()),
                "LastName" to txtLastName.text.toString(),
                "Uid" to user?.uid.toString()
            )

            //Voeg het toe aan de database
            db.collection("Pregnanties").document()
                .set(testUser)
        }
else    {     val dialogBuilder = AlertDialog.Builder(activity!!)
                dialogBuilder.setMessage("All fields must be filled in")

                    .setCancelable(false)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener {
                            dialog, id ->
                        dialog.dismiss()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("ERROR")
                alert.show()       }
        }
        return view

    }
}

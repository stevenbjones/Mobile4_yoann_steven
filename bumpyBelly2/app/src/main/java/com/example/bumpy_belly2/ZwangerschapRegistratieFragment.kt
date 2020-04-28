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
import android.widget.*
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
    var optie: String =""




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
        val opties = arrayOf("Unknown","Boy", "Girl")
        val spinner = view.findViewById<Spinner>(R.id.spin)
        spinner?.adapter = ArrayAdapter(activity?.applicationContext, R.layout.support_simple_spinner_dropdown_item, opties) as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val dialogBuilder = AlertDialog.Builder(activity!!)
                dialogBuilder.setMessage("You must select a gender")
                    .setCancelable(false)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener {
                            dialog, id ->
                        dialog.dismiss()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("ERROR")
                alert.show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                optie = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity, optie, Toast.LENGTH_LONG).show()
            }
        }


        //Register zwangerschap
        view.btnRegister.setOnClickListener {
            if (txtFirstName.text.isNotEmpty()){
            navController!!.navigate(R.id.action_zwangerschapRegistratieFragment_to_homePage)
            (activity as MainActivity).GeefFactsWeer()

            val user = FirebaseAuth.getInstance().currentUser
            val db = FirebaseFirestore.getInstance()
            val Zwangerschap = hashMapOf(
                "Actief" to true,
                "StartDate" to (txtWeeks.dayOfMonth.toString() + " " + (txtWeeks.month + 1).toString() + " " + txtWeeks.year.toString())


            )

                // Referenctie naar de pregnacies van user
              var DBPregnanties =  db.collection("Users").document(user?.uid.toString()).collection("Pregnanties").document()
                DBPregnanties.set(Zwangerschap)

                //SubColletie van de User zijn pregnacie. Deze noemt children
                DBPregnanties.collection("Children").document()
                    .set( "Gender" to optie

                    )

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

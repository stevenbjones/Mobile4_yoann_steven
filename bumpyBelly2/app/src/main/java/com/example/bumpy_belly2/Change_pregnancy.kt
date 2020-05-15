package com.example.bumpy_belly2

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_change_pregnancy.*
import kotlinx.android.synthetic.main.fragment_change_pregnancy.view.*


/**
 * A simple [Fragment] subclass.
 */
class Change_pregnancy : Fragment() {

    var navController: NavController? = null
    var optie: String =""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_pregnancy, container, false)
        val opties = arrayOf("Unknown","Boy", "Girl")
        val spinner = view.findViewById<Spinner>(R.id.spinner)
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

        //Het liefst op deze methode verder werken, deze is het beste om zo smooth mogelijk een afbraak van een zwangerschap te bevestigen.

        view.btnSave.setOnClickListener {
           if(!checkBoxCancel.isChecked)
           {
               // Als de checkBox "cancel" niet gechecked is zou hij de aanpassingen moeten uitvoeren.
               //Functies oproepen die van de main afkomstig zijn crashen in deze methode.
               navController!!.navigate(R.id.action_change_pregnancy_to_homePage)
           }
            else
           {

               //Hier heb ik alles al gezet van de btn verwijder.
               //Maar ook hier crasht het weer doordat de main niet aanspreekbaar is.

               // var mainAct = (activity as MainActivity)
               //  mainAct.ZetPregnancieFalse()
               // mainAct.signout()
               navController!!.navigate(R.id.action_change_pregnancy_to_welcomeFragment)
           }

        }

        //Dit is om te testen of het met een btn wel ging --> dit gaat dus ook niet
        // Er is een probleem als je een functie van de main oproept --> app crasht onmiddelijk
        //deze methode enkel gebruiken om te testen, de knop is niet smooth genoeg om een zwangerschap afbraak te bevestigen.

        view.btntest.setOnClickListener {
          //  (activity as MainActivity).signout()
            navController!!.navigate(R.id.action_change_pregnancy_to_welcomeFragment)

        }





        // Inflate the layout for this fragment
        return view


    }

}

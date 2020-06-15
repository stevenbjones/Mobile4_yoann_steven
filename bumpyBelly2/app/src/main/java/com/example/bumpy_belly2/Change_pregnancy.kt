package com.example.bumpy_belly2

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_change_pregnancy.*
import kotlinx.android.synthetic.main.fragment_change_pregnancy.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


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

    @RequiresApi(Build.VERSION_CODES.O)
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

        view.btnSave.setOnClickListener {
           if(!checkBoxCancel.isChecked)
           {
               if (txtName.text.isNotEmpty()){
               val user = FirebaseAuth.getInstance().currentUser
               val db = FirebaseFirestore.getInstance()

               val Zwangerschap = hashMapOf<String,Any>(
                   "StartDate" to if(datePicker.dayOfMonth.toString().length == 1 && datePicker.month.toString().length == 1) {
                       ("0${datePicker.dayOfMonth} 0${datePicker.month + 1} ${datePicker.year}")
                   }
                   else if (datePicker.dayOfMonth.toString().length == 1 && datePicker.month.toString().length == 2 ){
                       ("0${datePicker.dayOfMonth} ${datePicker.month + 1} ${datePicker.year}")
                   }
                   else if (datePicker.dayOfMonth.toString().length == 2 && datePicker.month.toString().length == 1 ){
                       ("${datePicker.dayOfMonth} 0${datePicker.month + 1} ${datePicker.year}")
                   }
                   else{
                       ("${datePicker.dayOfMonth} ${datePicker.month + 1} ${datePicker.year}")
                   }
               )

               val SpecificatieChildren = hashMapOf<String,String>(
                   "Gender" to optie,
                   "Name" to txtName.text.toString()
               )

               var MainAct = (activity as MainActivity)
               db.collection("Users").document(user?.uid.toString()).collection("Pregnanties").document(MainAct.documentID)
                   .update(mapOf(
                       "Gender" to optie,
                       "Name" to txtName.text.toString()
                   ))
               db.collection("Users").document(user?.uid.toString()).collection("Pregnanties").document(MainAct.documentID)
                   .update(Zwangerschap)



               var formatter = DateTimeFormatter.ofPattern("dd MM yyyy")

               val StartDate = LocalDate.parse(Zwangerschap.get("StartDate").toString(), formatter)

               var WekenKind  = ChronoUnit.WEEKS.between(StartDate, LocalDate.now()).toInt()
               (activity as MainActivity).GeefFactsEnFotoWeer(WekenKind)

               navController!!.navigate(R.id.action_change_pregnancy_to_homePage)
               }
               else{
                   val dialogBuilder = AlertDialog.Builder(activity!!)
                   dialogBuilder.setMessage("All fields must be filled in")

                       .setCancelable(false)
                       .setPositiveButton("Ok", DialogInterface.OnClickListener {
                               dialog, id ->
                           dialog.dismiss()
                       })
                   val alert = dialogBuilder.create()
                   alert.setTitle("ERROR")
                   alert.show()
               }

            }

           else
           {
               var mainAct = (activity as MainActivity)
               mainAct.ZetPregnancieFalse()
               mainAct.signout()
               navController!!.navigate(R.id.action_change_pregnancy_to_welcomeFragment)
           }

        }
        // Inflate the layout for this fragment
        return view


    }

}

package com.example.bumpy_belly2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import kotlinx.android.synthetic.main.fragment_zwangerschap_registratie.view.*

class ZwangerschapRegistratieFragment : Fragment() {

    var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_zwangerschap_registratie, container, false)

        view.btnRegister.setOnClickListener {
            navController!!.navigate(R.id.action_zwangerschapRegistratieFragment_to_homePage)
            (activity as MainActivity).GeefFactsWeer()
        }
        return view

    }
}

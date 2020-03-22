package com.example.bumpybelly


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_welcome.view.*



class WelcomeFragment : Fragment() {
    var navController: NavController? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_welcome, container, false)


        view.Login.setOnClickListener {

            //navController!!.navigate(R.id.action_welcomeFragment2_to_homePage2)


        }
        // Inflate the layout for this fragment
        return view

    }



}


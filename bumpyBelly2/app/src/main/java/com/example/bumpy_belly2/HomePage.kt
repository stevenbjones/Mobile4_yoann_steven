package com.example.bumpy_belly2


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_home_page.view.*


/**
 * A simple [Fragment] subclass.
 */
class HomePage : Fragment() {
    var navController: NavController? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_page, container, false)

        view.sign_out.setOnClickListener {
            (activity as MainActivity).signout()
            navController!!.navigate(R.id.action_homePage_to_welcomeFragment)

        }
        view.btnKalender.setOnClickListener {
            navController!!.navigate(R.id.action_homePage_to_kalenderFragment)

        }

        // Inflate the layout for this fragment
        return view


    }
}

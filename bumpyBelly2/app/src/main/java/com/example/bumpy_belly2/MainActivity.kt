
package com.example.bumpy_belly2


    import android.app.Activity
    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.navigation.NavController
    import com.firebase.ui.auth.AuthUI
    import com.firebase.ui.auth.IdpResponse
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import com.google.firebase.firestore.FirebaseFirestore
    import com.google.firebase.firestore.Source

    import kotlinx.android.synthetic.main.activity_main.*
    import kotlinx.android.synthetic.main.fragment_home_page.*
    import kotlinx.android.synthetic.main.fragment_welcome.*
    import java.util.*


class MainActivity : AppCompatActivity() {

    //Zo kunnen we Tag gebruiken
    companion object {
          val TAG = "ClassName"
    }
        var navController: NavController? = null


    var Nalogin: Boolean = false
        val MY_REQUEST_CODE: Int = 7117

        //Instantie van firestore database
        val db = FirebaseFirestore.getInstance()

        lateinit var providers : List<AuthUI.IdpConfig>

    // get the current user
    val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            providers = Arrays.asList<AuthUI.IdpConfig>(
                AuthUI.IdpConfig.EmailBuilder().build(),// email build
                AuthUI.IdpConfig.GoogleBuilder().build(),// Google build
                AuthUI.IdpConfig.PhoneBuilder().build()// Phone build
            )

        }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if(requestCode == MY_REQUEST_CODE ){

                val response = IdpResponse.fromResultIntent(data)
                if(resultCode == Activity.RESULT_OK){

                    Toast.makeText(this, ""+user!!.email,Toast.LENGTH_SHORT).show()

                    //Functie om inglogde user toe te voegen aan database
                    VoegUserToeAanDataBase(user)

                    //VoegFactsToeAanDataBase()

                    //btn ga
                    val ga = findViewById<Button>(R.id.btnGa)
                    ga.visibility= View.VISIBLE




                    //weet niet of dit een goeie methode is om het te doen
                   // nav_host_fragment.activity!!.setContentView(R.layout.fragment_home_page)
                }
                else{
                    Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
                }

            }
        }

    //Met deze functie wordt de gebruiker aftgemeld
    fun signout(){
            sign_out.setOnClickListener {
                //signout
                AuthUI.getInstance().signOut(this@MainActivity)
                    .addOnCompleteListener {
                        sign_out.isEnabled = false
                        showSignInOptions()

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

        //Functie om facts toe te voegen aan database
        fun VoegFactsToeAanDataBase(){

            val Facts = arrayOf("Huilen in de baarmoeder. Baby’s oefenen in de baarmoeder al met huilen. Dit gebeurt meestal na ongeveer 28 weken zwangerschap. Ook is hij/zij in de buik al druk bezig met duimzuigen, schoppen en slikken.",
                "Eerste beweging. Vanaf 8 weken zwangerschap begint je baby al met bewegen. Deze bewegingen voel je nog niet, want je baby is dan nog maar zo groot als een bosbes.",
                " Aanmaak van urine. Vanaf de 13e week van je zwangerschap maakt je baby urine aan. Deze plast hij uit in het vruchtwater en drinkt hij vervolgens weer op. Maak je geen zorgen, dit kan geen kwaad.",
                "Smaken proeven in de baarmoeder. Je baby kan al verschillende smaken proeven wanneer hij nog in de baarmoeder zit. Wanneer jij verschillende dingen eet, gaat je vruchtwater ook andere smaken produceren. En daar geniet je baby lekker van!",
                " Geur. Na de geboorte gebruikt je baby de geur van jouw borst om de tepel te vinden. Na de bevalling scheiden je borsten een bepaalde geur af, dit helpt je baby om de borst te vinden en te drinken.",
                "255 baby’s per minuut. Wereldwijd worden er per minuut 255 baby’s geboren. Tijdens jouw bevalling puffen er dus heel wat dames mee.",
                "Een baby heeft ongeveer 10.000 smaakpapillen. Niet alleen op de tong maar ook aan de binnenkant van de wangen en het gehemelte.",
                "Een baby herkent direct na de geboorte de geur en de stem van zijn moeder. Het duurt een paar weken voordat hij het verschil kan zien tussen de moeder en iemand anders.",
                "Het eerste lachje komt zo tussen de 4 en 6 weken na de geboorte.",
                "Op het moment dat een baby wordt geboren heeft het een hartslag 180 slagen per minuut. Na een paar uur is dit gedaald naar 140 slagen. Als het kind 1 jaar is, is dit ongeveer 115 slagen per minuut.",
                "Tot de leeftijd van ongeveer 7 maanden kan een baby ademhalen en slikken tegelijk.",
                "De meeste baby’s verliezen al het haar waar mee ze geboren worden in de eerste vier maanden van hun leven.",
                "De eerste ontlasting van de future baby is al aanwezig vanaf 21 weken De ontlasting, ook wel ‘meconium’ genoemd, komt er pas uit na de geboorte. Voor de moeders in spé: schrik niet, het is zwart en plakt als een gek!",
                "1 op de 2000 baby’s wordt geboren met een tand. In veel gevallen moet die dan wel verwijderd worden, omdat zo’n tand vaak een onvolgroeide wortel heeft.",
                "De vingerafdruk van een baby wordt al bepaald in de eerste drie maanden van de zwangerschap.",
                "Je baby heeft al na 6 weken zwangerschap een eigen hartslag.",
                "Vanaf 23 weken zwangerschap kan je kindje schrikken van hard of onverwacht geluid.",
                "Je voelt het zelf op dat moment nog niet, maar je kindje reageert al na 10 weken als je zachtjes op je buik duwt.",
                "Vanaf 28 weken kan een baby dezelfde dingen ruiken die jij als moeder ruikt.",
                "De schoenmaat van een zwangere vrouw kan een hele maat groter worden door het vocht dat ze vasthoudt.",
                "Naweeën vinden een half uur na de bevalling plaats en kunnen soms wel 15 dagen duren. Ze zorgen er voor dat de baarmoeder weer op zijn plaats komt. Gewone pijnstillers kunnen de pijn verhelpen.",
                "Een verhoogde kans op een tweeling als er tweelingen in de directe familie voorkomen, vooral als er tweelingen zijn aan moeders kant",
                "Maak de fopspeen van je kindje vast aan een ballon en vertel je kleintje dat het op ontdekkingsreis gaat in de ruimte."
            )

            var x = 0
            while(x<Facts.count()){

                val Fact = hashMapOf(
                    "Fact" to Facts[x]
                )

                db.collection("Facts").document("$x")
                    .set(Fact)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                x++
            }

        }

    //Functie waardoor de facts gedisplayed worden op homescherm
    fun GeefFactsWeer (){

        //Haal fact 1 uit de database
        val docRef = db.collection("Facts").document("1")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                    //Vul textfield met het field FACT van fact1
                    findViewById<TextView>(R.id.TxtWeetjes).text = document.getString("Fact")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    //REgistreerd user met UID in de firestore database
    fun VoegUserToeAanDataBase(user: FirebaseUser) {
            //Maak hash om toe te kunnen voegen in database
            val User = hashMapOf(
                "Email" to user.email
            )

            //Voeg het toe aan de database
            db.collection("Users").document(user.uid)
                .set(User)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

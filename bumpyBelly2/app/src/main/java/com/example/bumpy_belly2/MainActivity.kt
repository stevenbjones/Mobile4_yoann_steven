
package com.example.bumpy_belly2


    import android.app.Activity
    import android.content.Intent
    import android.os.Build
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.TextView
    import android.widget.Toast
    import androidx.annotation.RequiresApi
    import androidx.appcompat.app.AppCompatActivity
    import androidx.navigation.NavController
    import com.firebase.ui.auth.AuthUI
    import com.firebase.ui.auth.IdpResponse
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import com.google.firebase.firestore.DocumentSnapshot
    import com.google.firebase.firestore.FirebaseFirestore
    import com.google.firebase.firestore.SetOptions
    import com.squareup.picasso.Picasso
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
                    // functie om de facts toe te voegen aan de database
                   // VoegFactsToeAanDataBase()



                    val ga = findViewById<Button>(R.id.btnGa)
                    ga.visibility= View.VISIBLE
                }
                else{
                    Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
                }
            }
        }

    //Met deze functie wordt de gebruiker afgemeld
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

            val Facts = arrayOf(
                "Het eerste lachje komt zo tussen de 4 en 6 weken na de geboorte.",
                "Je baby heeft al na 6 weken zwangerschap een eigen hartslag.",
                "Eerste beweging. Vanaf 8 weken zwangerschap begint je baby al met bewegen. Deze bewegingen voel je nog niet, want je baby is dan nog maar zo groot als een bosbes.",
                "Je voelt het zelf op dat moment nog niet, maar je kindje reageert al na 10 weken als je zachtjes op je buik duwt.",
                "De vingerafdruk van een baby wordt al bepaald in de eerste drie maanden van de zwangerschap.",
                "Aanmaak van urine. Vanaf de 13e week van je zwangerschap maakt je baby urine aan. Deze plast hij uit in het vruchtwater en drinkt hij vervolgens weer op. Maak je geen zorgen, dit kan geen kwaad.",
                "De eerste ontlasting van de future baby is al aanwezig vanaf 21 weken De ontlasting, ook wel ‘meconium’ genoemd, komt er pas uit na de geboorte. Voor de moeders in spé: schrik niet, het is zwart en plakt als een gek!",
                "Vanaf 23 weken zwangerschap kan je kindje schrikken van hard of onverwacht geluid.",
                "Huilen in de baarmoeder. Baby’s oefenen in de baarmoeder al met huilen. Dit gebeurt meestal na ongeveer 28 weken zwangerschap. Ook is hij/zij in de buik al druk bezig met duimzuigen, schoppen en slikken.",
                "Vanaf 28 weken kan een baby dezelfde dingen ruiken die jij als moeder ruikt.",
                "Tot de leeftijd van ongeveer 30 weken kan een baby ademhalen en slikken tegelijk.",
                "Smaken proeven in de baarmoeder. Je baby kan al verschillende smaken proeven wanneer hij nog in de baarmoeder zit. Wanneer jij verschillende dingen eet, gaat je vruchtwater ook andere smaken produceren. En daar geniet je baby lekker van!",
                " Geur. Na de geboorte gebruikt je baby de geur van jouw borst om de tepel te vinden. Na de bevalling scheiden je borsten een bepaalde geur af, dit helpt je baby om de borst te vinden en te drinken.",
                "255 baby’s per minuut. Wereldwijd worden er per minuut 255 baby’s geboren. Tijdens jouw bevalling puffen er dus heel wat dames mee.",
                "Een baby heeft ongeveer 10.000 smaakpapillen. Niet alleen op de tong maar ook aan de binnenkant van de wangen en het gehemelte.",
                "Een baby herkent direct na de geboorte de geur en de stem van zijn moeder. Het duurt een paar weken voordat hij het verschil kan zien tussen de moeder en iemand anders.",
                "Op het moment dat een baby wordt geboren heeft het een hartslag 180 slagen per minuut. Na een paar uur is dit gedaald naar 140 slagen. Als het kind 1 jaar is, is dit ongeveer 115 slagen per minuut.",
                "De meeste baby’s verliezen al het haar waar mee ze geboren worden in de eerste vier maanden van hun leven.",
                "1 op de 2000 baby’s wordt geboren met een tand. In veel gevallen moet die dan wel verwijderd worden, omdat zo’n tand vaak een onvolgroeide wortel heeft.",
                "De schoenmaat van een zwangere vrouw kan een hele maat groter worden door het vocht dat ze vasthoudt.",
                "Naweeën vinden een half uur na de bevalling plaats en kunnen soms wel 15 dagen duren. Ze zorgen er voor dat de baarmoeder weer op zijn plaats komt. Gewone pijnstillers kunnen de pijn verhelpen.",
                "Een verhoogde kans op een tweeling als er tweelingen in de directe familie voorkomen, vooral als er tweelingen zijn aan moeders kant",
                "Maak de fopspeen van je kindje vast aan een ballon en vertel je kleintje dat het op ontdekkingsreis gaat in de ruimte.",
                "Donkere lijn over je buik. Tijdens de zwangerschap kan er een donkere lijn op je buik ontstaan, ook wel de linea nigra genoemd. Deze streep zat er voor je zwangerschap ook al, maar krijgt door de veranderde hormoonhuishouding een verhoogde pigmentproductie. Sommige plekken op je huid worden daardoor donkerder en vallen meer op. Hetzelfde gebeurt met een zwangerschapsmasker.",
                "Groter hart. Tijdens de zwangerschap wordt je baby van genoeg voedingsstoffen en zuurstof voorzien. Hierdoor wordt er meer van je hart gevraagd en moet hij harder pompen. Hierdoor worden de vier holtes van het hart iets groter en de spierwanden worden dikker.",
                "Veranderde hersenen. Tijdens de zwangerschap verandert de structuur van je hersenen, vooral in delen met sociale functies. Deze veranderingen blijven tot minstens 2 jaar na de geboorte.",
                "De zwangerschapsgloed. De zogenaamde zwangerschapsgloed wordt veroorzaakt door je hormonen. Deze stimuleren de talgklieren in je huid om meer talg te produceren. Bij de een kan dit tot acné leiden, bij de ander gaat de huid iets meer glanzen dan normaal. Ben jij één van de gelukkigen?",
                "Volwassenen hebben 206 botten, een pasgeborene 300. In de loop van de tijd vergroeien er enkele botten zodat het totale aantal afneemt.",
                "Een baby die jonger is dan 4 maanden kan geen zout proeven.",
                "Baby’s geboren in mei zijn gemiddeld 200 gram zwaarder dan baby’s die in een andere maand worden geboren.",
                "Uit onderzoek is gebleken, dat baby’s die geboren worden met een keizersnede, vaker ademhalingsproblemen hebben dan vaginaal geboren baby’s. Waarschijnlijk heeft dit te maken met een bepaald hormoon dat enkel wordt aangemaakt gedurende een vaginale bevalling.",
                "Een pasgeborene laat al een voorkeur zien voor de moedertaal boven een andere taal. Men vermoedt dan ook dat het leren begrijpen van taal en spraak al begint in de baarmoeder.",
                "Uit onderzoek is gebleken dat kinderen die wel te eten krijgen, maar niet worden vastgehouden en aangeraakt, en met wie niet op een andere manier wordt gecommuniceerd, niet gedijen en dat hun lichamelijke en mentale ontwikkeling ernstig achterblijft.",
                "Baby’s kijken liever naar een echt gezicht dan naar een tekening van een gezicht. Tevens geven ze de voorkeur aan een lachend gezicht in plaats van dat van een boos gezicht.",
                "Uitgerekende datum. Slechts 5% van de baby’s wordt geboren op de uitgerekende datum. Grote kans dat je op je uitgerekende datum dus nog lekker een dagje kan Netflixen.",
                "De dagen voor, tijdens en na de bevalling soms wel de baby blues worden genoemd? Dit betekent dat je huilerig, somber en/of angstig kan zijn rond deze periode. Gelukkig gaan deze gevoelens meestal na een paar dagen over.",
                "Misselijkheid tijdens de zwangerschap wordt veroorzaakt door het hormoon HCG? Vrouwen die zwanger zijn van een tweeling hebben meer HCG in hun bloed dan zwangere vrouwen van één kind, waardoor zij vaak nog misselijker zijn tijdens de zwangerschap en meer braken.",
                "Tijdens het eerste trimester van je zwangerschap kun je opnieuw acné krijgen net zoals in je puberteit. Dit heeft alles te maken met je hormonen die overuren maken.",
                "Baby’s kunnen in je buik al de hik krijgen en dat kun je vaak goed voelen. Het is altijd even wennen aan de bewegingen in je buik, voordat je door hebt wat nou precies wat is. Maar hikken kun je herkennen aan dat je regelmatig een korte schokbeweging voelt. Het kan binnen een paar keer voorbij zijn, maar kan ook minuten lang aanhouden.",
                "Door de hormonen heb je tijdens je zwangerschap ook veel gevoeliger tandvlees. Omdat ontstekingen ook de kans op vroeggeboorte kunnen verhogen, is het belangrijk dat je je tanden extra goed verzorgt.",
                "Tijdens je zwangerschap droom je vaak extra heftig en levendig. Dit komt onder andere omdat je door je hormonen meer emoties meeneemt, die je ‘s nachts moet verweken. Daarnaast verandert je slaapritme ook en breng je meer tijd door in je REM slaap, wat de fase is waarin je droomt.",
                "Wanneer je tijdens je zwangerschap in de zon zit, kun je een zwangerschapsmasker krijgen. Dit zijn pigmentvlekken in je gezicht, die veroorzaakt worden door de hormonen die dan door je lijf gieren. Vaak zitten de donkere vlekken rond je bovenlip, voorhoofd en jukbeenderen."
            )
            var x = 0
            while(x<Facts.count()){

                val Fact = hashMapOf(
                    "Fact" to Facts[x],
                    "Week" to x
                )

                db.collection("Facts").document("$x")
                    .set(Fact)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                x++
            }
        }

   var documentID = ""
    //Functie waardoor de facts gedisplayed worden op homescherm
    @RequiresApi(Build.VERSION_CODES.O)
    fun GeefFactsEnFotoWeer (WekenKind: Int ){

        Log.d(TAG, "weken kind  data: ${WekenKind}")
        //Haal fact 1 uit de database
        val docRef = db.collection("Facts").document(WekenKind.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    Log.d(TAG, "weken kind  data: ${WekenKind}")

                    findViewById<TextView>(R.id.txtWeek).text =" Week ${WekenKind} :  "
                    findViewById<TextView>(R.id.TxtWeetjes).text = document.getString("Fact")


                    val docRef = db.collection("Pictures").document(WekenKind.toString())
                    docRef.get()
                        .addOnSuccessListener { document ->
                            var url = document.getString("Url")
                            findViewById<TextView>(R.id.txtFotoDetail).text =" FOTO DETAIL: steven <3 yoann"
                            var fotoView = findViewById<ImageView>(R.id.ImageWeek)
                            Picasso.get().load(url.toString()).into(fotoView)
                        }

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

    //Registreerd user met UID in de firestore database
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


    fun ZetPregnancieFalse(){

        val data = hashMapOf("Actief" to false)//

       db.collection("Users").document(user?.uid.toString()).collection("Pregnanties").document(documentID)
           .set(data, SetOptions.merge())

    }

    fun HaalPregnancieOp(document: String){
        documentID = document
    }
    }

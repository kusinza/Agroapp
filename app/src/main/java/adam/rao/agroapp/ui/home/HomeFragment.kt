package adam.rao.agroapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import adam.rao.agroapp.R
import adam.rao.agroapp.adapters.PlantAdapter
import adam.rao.agroapp.models.Notification
import adam.rao.agroapp.models.Plant
import adam.rao.agroapp.ui.output.landsize
import adam.rao.agroapp.ui.plantchoice.PlantChoiceViewModel
import adam.rao.agroapp.utils.addPlants
import adam.rao.agroapp.utils.getAuthInstance
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var btnProceed: Button
    private lateinit var etLandSize: TextInputEditText
    private lateinit var slideshowViewModel: PlantChoiceViewModel
    private lateinit var adapter: PlantAdapter
    private lateinit var plantRecyclerView: RecyclerView
    private  var plantList= ArrayList<Plant>()
    private  var plantList2= ArrayList<Plant>()
    private val spanCount = 2
    private lateinit var  mAuth: FirebaseUser

    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        btnProceed = root.findViewById(R.id.btnProceed)
        etLandSize = root.findViewById(R.id.land_size_input)

        btnProceed.setOnClickListener {
            findNavController().navigate(R.id.plant_choice)
            landsize=etLandSize.text.toString()
            btnProceed.hideKeyboard()
        }

        if(plantList.size == 0) {
            addPlants(plantList)
        }

        mAuth= getAuthInstance()
        db= FirebaseFirestore.getInstance()
        db.collection("notification").document(mAuth.uid).collection("current1")
            .get()
            .addOnSuccessListener { documents ->
                plantList2.clear()
                for (document in documents) {
                    Log.d("document_got", "${document.id} => ${document.data}")

                    val hashMap=document.data
                    for(a in hashMap)
                    {


                        for(i in plantList){
                            if(a.key.contains(i.name))
                                plantList2.add(Plant(a.key,i.imageId))
                        }
                    }

                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("document_fail", "Error getting documents: ", exception)
            }
        adapter = PlantAdapter(context!!.applicationContext, plantList2,2)
        plantRecyclerView = root.findViewById(R.id.plant_recycler_view)
        plantRecyclerView.adapter = adapter
        plantRecyclerView.layoutManager = GridLayoutManager(context!!.applicationContext, spanCount)


        return root
    }

    override fun onStart() {
        super.onStart()
        db.collection("notification").document(mAuth.uid).collection("current1")
            .get()
            .addOnSuccessListener { documents ->
                plantList2.clear()
                for (document in documents) {
                    Log.d("document_got", "${document.id} => ${document.data}")



                    val hashMap=document.data as HashMap<String,Array<String>>
                    for(a in hashMap)
                    {
                        for(i in plantList) {
                            if (a.key.contains(i.name))
                                plantList2.add(
                                    Plant(
                                        a.key,
                                        i.imageId
                                    )
                                )
                        }
                    }


                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("document_fail", "Error getting documents: ", exception)
            }
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
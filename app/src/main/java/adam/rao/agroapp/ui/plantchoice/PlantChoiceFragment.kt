package adam.rao.agroapp.ui.plantchoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import adam.rao.agroapp.R
import adam.rao.agroapp.adapters.PlantAdapter
import adam.rao.agroapp.models.Plant
import adam.rao.agroapp.utils.addPlants
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlantChoiceFragment : Fragment() {

    private lateinit var slideshowViewModel: PlantChoiceViewModel
    private lateinit var adapter: PlantAdapter
    private lateinit var plantRecyclerView: RecyclerView
    private lateinit var plantList: ArrayList<Plant>
    private val spanCount = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(PlantChoiceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_plant_choice, container, false)

        if(plantList.size == 0) {
            addPlants(plantList)
        }

        adapter = PlantAdapter(context!!.applicationContext, plantList)
        plantRecyclerView = root.findViewById(R.id.plant_recycler_view)
        plantRecyclerView.adapter = adapter
        plantRecyclerView.layoutManager = GridLayoutManager(context!!.applicationContext, spanCount)

        return root
    }
}
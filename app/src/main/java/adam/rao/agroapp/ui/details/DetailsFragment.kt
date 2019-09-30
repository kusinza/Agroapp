package adam.rao.agroapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import adam.rao.agroapp.R
import adam.rao.agroapp.utils.genRandomNum
import android.widget.Button
import androidx.navigation.fragment.findNavController

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsViewModel =
            ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_details, container, false)

        val manureQuantity = root.findViewById<TextView>(R.id.manure_quantity)
        val waterQuantity = root.findViewById<TextView>(R.id.water_quantity)
        val btnProceed = root.findViewById<Button>(R.id.btnContinue)

        val manure = genRandomNum()
        val waterNeeded = genRandomNum()

        manureQuantity.text = manure.toString()
        waterQuantity.text = waterNeeded.toString()

        btnProceed.setOnClickListener {
            findNavController().navigate(R.id.seed_input)
        }

        return root
    }
}
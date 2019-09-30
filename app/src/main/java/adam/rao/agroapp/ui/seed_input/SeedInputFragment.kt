package adam.rao.agroapp.ui.seed_input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import adam.rao.agroapp.R
import android.widget.Button
import androidx.navigation.fragment.findNavController

class SeedInputFragment : Fragment() {

    private lateinit var seedInputViewModel: SeedInputViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        seedInputViewModel =
            ViewModelProviders.of(this).get(SeedInputViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_seed_input, container, false)
        val btnSeedOutput = root.findViewById<Button>(R.id.btnOutput)
        btnSeedOutput.setOnClickListener {
            findNavController().navigate(R.id.expected_output)
        }
        return root
    }
}
package adam.rao.agroapp.ui.output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import adam.rao.agroapp.R
import adam.rao.agroapp.utils.expectedOutput

var landsize=""
class OutputFragment : Fragment() {

    private lateinit var outputViewModel: OutputViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        outputViewModel =
            ViewModelProviders.of(this).get(OutputViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_output, container, false)
        val outputQuantity = root.findViewById<TextView>(R.id.output_quantity)
        val quantity = expectedOutput()

        outputQuantity.text = quantity.toString()

        return root
    }
}
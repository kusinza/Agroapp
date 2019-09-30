package adam.rao.agroapp.adapters

import adam.rao.agroapp.R
import adam.rao.agroapp.models.Plant
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PlantAdapter(private val context: Context, private val plantList: ArrayList<Plant>) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var plantName: TextView = itemView.findViewById(R.id.tv_plant_name)
        var plantImage: ImageView = itemView.findViewById(R.id.plant_image)

        override fun onClick(v: View?) {
            v?.findNavController()?.navigate(R.id.details)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).
                inflate(R.layout.plant_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantName.text = plant.name
        Picasso.get().load(plant.imageId).
            placeholder(R.drawable.ic_launcher_background).into(holder.plantImage)
    }
}
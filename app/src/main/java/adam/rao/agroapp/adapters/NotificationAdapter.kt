package adam.rao.agroapp.adapters

import adam.rao.agroapp.R
import adam.rao.agroapp.models.Notification
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(private val context: Context, private val notificationList: ArrayList<Notification>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var notificationTitle: TextView = itemView.findViewById(R.id.tv_notification_title)
        var notificationDescription: TextView = itemView.findViewById(R.id.tv_notification_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).
                inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.notificationTitle.text = notification.title
        holder.notificationDescription.text ="${notification.description} \n ${notification.harvest}"
    }
}
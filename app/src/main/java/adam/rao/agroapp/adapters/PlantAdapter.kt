package adam.rao.agroapp.adapters

import adam.rao.agroapp.MyNotificationPublisher
import adam.rao.agroapp.R
import adam.rao.agroapp.models.Plant
import adam.rao.agroapp.ui.output.landsize
import adam.rao.agroapp.utils.getAuthInstance
import android.app.Activity
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService


class user_info_{
    //lateinit var name:String

    var notification=ArrayList<Any>()
    init {

    }
}
class PlantAdapter(private val context: Context, private val plantList: ArrayList<Plant>,private val caller:Int) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(){

    private lateinit var  mAuth: FirebaseUser

    private lateinit var db: FirebaseFirestore
    var mUserInfo=user_info_()
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

        mAuth= getAuthInstance()
        db=FirebaseFirestore.getInstance()
        val plant = plantList[position]
        holder.plantName.text = plant.name
        Picasso.get().load(plant.imageId).
            placeholder(R.drawable.ic_launcher_background).into(holder.plantImage)
        if(caller==1)
        holder.plantImage.setOnClickListener {
            holder.plantImage.findNavController().navigate(R.id.details)
            val harvest=(0..100).shuffled().first().toString()
            db.collection("notification").document(mAuth.uid).collection("current1").add(mapOf(plant.name to listOf<String>(landsize.toString(),harvest )))

            scheduleNotification(getNotification("Check land, harvest in $harvest Days ",plant.name),System.currentTimeMillis())
        }
        else{
            holder.plantImage.setOnClickListener {
                holder.plantImage.findNavController().navigate(R.id.nav_notification)}
        }


    }
    private fun getNotification(content: String,title:String): Notification {
        val builder = NotificationCompat.Builder(context, "default")
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setSmallIcon(adam.rao.agroapp.R.drawable.agro_icon)
        builder.setAutoCancel(true)
        builder.setChannelId("10001")
        return builder.build()
    }
    private fun scheduleNotification ( notification:Notification ,  delay:Long) {
      val notificationIntent =  Intent( context, MyNotificationPublisher::class.java)
      notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID , 1 ) ;
      notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION , notification) ;
      val pendingIntent = PendingIntent.getBroadcast ( context, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
      val alarmManager =  context.getSystemService(Context. ALARM_SERVICE ) as AlarmManager ;

      alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , SystemClock.elapsedRealtime()+3000 , pendingIntent) ;
   }
}
package adam.rao.agroapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import adam.rao.agroapp.R
import adam.rao.agroapp.adapters.NotificationAdapter
import adam.rao.agroapp.models.Notification
import adam.rao.agroapp.utils.ViewDialog
import adam.rao.agroapp.utils.addNotifications
import adam.rao.agroapp.utils.getAuthInstance
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var notificationRecyclerView: RecyclerView
    private var notificationList= ArrayList<Notification>()
    private lateinit var adapter: NotificationAdapter

    private lateinit var  mAuth: FirebaseUser

    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        if(notificationList.size == 0){
           // addNotifications(notificationList)
        }

        mAuth= getAuthInstance()
        db=FirebaseFirestore.getInstance()

        val viewDialog= ViewDialog(activity!!)
        viewDialog.showDialog()
        db.collection("notification").document(mAuth.uid).collection("current1")
            .get()
            .addOnSuccessListener { documents ->
                viewDialog.hideDialog()
                for (document in documents) {
                    Log.d("document_got", "${document.id} => ${document.data}")

                    val hashMap=document.data as HashMap<String,ArrayList<String>>
                    for(a in hashMap)
                    {
                    notificationList.add(Notification(a.key,"Land Size: ${a.value[0]}","Harvest In: ${a.value[1]} Days"))
                    }

                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("document_fail", "Error getting documents: ", exception)
            }
        adapter = NotificationAdapter(context!!.applicationContext, notificationList)
        notificationRecyclerView = root.findViewById(R.id.notification_recycler_view)
        notificationRecyclerView.adapter = adapter
        notificationRecyclerView.layoutManager = LinearLayoutManager(context!!.applicationContext)


        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}
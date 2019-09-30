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
import adam.rao.agroapp.utils.addNotifications
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<Notification>
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        if(notificationList.size == 0){
            addNotifications(notificationList)
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
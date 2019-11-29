package adam.rao.agroapp.utils

import adam.rao.agroapp.R
import adam.rao.agroapp.models.Notification
import adam.rao.agroapp.models.Plant

fun addPlants(plantList: ArrayList<Plant>) {
    plantList.add(Plant("Maize", R.drawable.maize))
    plantList.add(Plant("Cassava", R.drawable.cassava))
    plantList.add(Plant("Sorghum", R.drawable.sorghum))
    plantList.add(Plant("Beans", R.drawable.beans))
}

fun addNotifications(notificationList: ArrayList<Notification>) {
    notificationList.add(Notification("Test One", "Test Notification",""))
    notificationList.add(Notification("Test Two", "Test Notification",""))
    notificationList.add(Notification("Test Three", "Test Notification",""))
    notificationList.add(Notification("Test Four", "Test Notification",""))
    notificationList.add(Notification("Test Five", "Test Notification",""))
    notificationList.add(Notification("Test Six", "Test Notification",""))
    notificationList.add(Notification("Test Seven", "Test Notification",""))
    notificationList.add(Notification("Test Eight", "Test Notification",""))
    notificationList.add(Notification("Test Nine", "Test Notification",""))
    notificationList.add(Notification("Test Ten", "Test Notification",""))
}
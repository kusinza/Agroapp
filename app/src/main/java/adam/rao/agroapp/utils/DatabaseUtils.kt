package adam.rao.agroapp.utils

import adam.rao.agroapp.models.Details
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private val mDb = FirebaseDatabase.getInstance()
private val mDbRef = mDb.reference

fun insertToDatabase(child: String, value: Details) {
    mDbRef.child(child).setValue(value)
}

fun readFromDatabase(value: Details) {
    mDbRef.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(value: DataSnapshot) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
        override fun onCancelled(error: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })
}
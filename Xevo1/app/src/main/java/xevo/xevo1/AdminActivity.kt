package xevo.xevo1

import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.content_admin.*
import xevo.xevo1.models.ApplicationAdapter
import xevo.xevo1.models.ApplicationData

class AdminActivity : AppCompatActivity() {

    lateinit var applicationAdapter: ApplicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // add close button
        setSupportActionBar(adminToolbar)
        @TargetApi(21)
        adminToolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
        adminToolbar.setNavigationOnClickListener { _ ->
            finish()
        }

        adminRecycler.layoutManager = LinearLayoutManager(this)

        val database = FirebaseDatabase.getInstance().reference
        applicationAdapter = ApplicationAdapter { item -> println(item) }

        adminRecycler.adapter = applicationAdapter

        database!!.child(getString(R.string.db_pending_app)).addValueEventListener(dataListener)
    }

    val dataListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            dataSnapshot!!.children.mapNotNull {
                val data = ApplicationData(it.key)
                applicationAdapter.add(data)
            }
        }

        override fun onCancelled(error: DatabaseError?) {}
    }
}

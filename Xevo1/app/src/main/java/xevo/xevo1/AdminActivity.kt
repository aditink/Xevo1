package xevo.xevo1

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.content_admin.*
import xevo.xevo1.Database.DatabaseInteractor
import xevo.xevo1.R.id.adminRecycler
import xevo.xevo1.R.id.adminToolbar
import xevo.xevo1.models.ApplicationAdapter
import xevo.xevo1.models.ApplicationData

class AdminActivity : DatabaseInteractor() {

    lateinit var applicationAdapter: ApplicationAdapter
    override val TAG = "AdminActivity"


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

        title = "Admin"

        adminRecycler.layoutManager = LinearLayoutManager(this)

        val database = FirebaseDatabase.getInstance().reference
        applicationAdapter = ApplicationAdapter { item ->
            startActivityForResult(Intent(this, ApplicationViewActivity::class.java).apply {
                putExtra(APPLICATION_DATA, item)
            }, APPLICATION_VIEW_RETURN_ID)
        }

        adminRecycler.adapter = applicationAdapter

        database!!.child(getString(R.string.db_pending_app)).addValueEventListener(dataListener)

        // add delete all data button
        deleteAllButton.setOnClickListener( { view -> deleteAllData() });
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            APPLICATION_VIEW_RETURN_ID -> {
                if (resultCode == Activity.RESULT_OK) {
                    val item: ApplicationData = data!!.getParcelableExtra<ApplicationData>(APPLICATION_DATA)
                    println(item.userId)
                    applicationAdapter.remove(item)
                }
            }
        }
    }

    companion object {
        const val APPLICATION_DATA = "xevo.xevo1.APPLICATION_DATA"
        const val APPLICATION_VIEW_RETURN_ID = 1
    }
}

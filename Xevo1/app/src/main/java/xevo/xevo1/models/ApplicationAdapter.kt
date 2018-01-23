package xevo.xevo1.models

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.admin_application_item.view.*
import xevo.xevo1.R

/**
 * Created by samthomas on 1/18/18.
 */

class ApplicationAdapter(val listener: (ApplicationData) -> Unit) : RecyclerView.Adapter<ApplicationAdapter.ViewHolder>() {

    private val items : SortedList<ApplicationData>
    private val ref : DatabaseReference

    init {
        items = SortedList(ApplicationData::class.java, object : SortedListAdapterCallback<ApplicationData>(this) {
            override fun compare(o1: ApplicationData?, o2: ApplicationData?): Int {
                o1!!;o2!!

                return o1.userId.compareTo(o2.userId)
            }

            override fun areContentsTheSame(oldItem: ApplicationData?, newItem: ApplicationData?): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: ApplicationData?, item2: ApplicationData?): Boolean {
                item1!!; item2!!
                return item1.userId == item2.userId
            }
        })

        ref = FirebaseDatabase.getInstance().reference
    }

    // Adding a method to ViewGroup
    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.admin_application_item), ref)

    public fun add(data: ApplicationData) {
        println(data.userId)
        items.add(data)
    }

    public fun remove(data: ApplicationData) {
        items.remove(data)
    }

    /**
     * Holds the View for each item in the list.
     */
    class ViewHolder(itemView: View, val ref: DatabaseReference) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: ApplicationData, listener: (ApplicationData) -> Unit) {

            ref.child("Users/%s/".format(data.userId)).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    dataSnapshot!!
                    data.email = dataSnapshot.child("email").getValue(String::class.java)!!
                    data.firstName = dataSnapshot.child("firstName").getValue(String::class.java)!!
                    data.lastName = dataSnapshot.child("lastName").getValue(String::class.java)!!

                    itemView.adminApplicationName.text = "%s %s".format(data.firstName, data.lastName)
                    itemView.adminApplicationEmail.text = data.email

                    itemView.setOnClickListener { listener(data) }
                }

                override fun onCancelled(p0: DatabaseError?) {}
            })
        }
    }

}
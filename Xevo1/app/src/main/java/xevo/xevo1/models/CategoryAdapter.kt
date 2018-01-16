package xevo.xevo1.models

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.category_card.view.*
import xevo.xevo1.GlideApp
import xevo.xevo1.R

/**
 * Recycler Adapter class. Handles showing cases in the list view.
 */
class CategoryAdapter(val items: List<CategoryData>, val listener: (CategoryData) -> Unit): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    // Adding a method to ViewGroup
    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.category_card))

    /**
     * Class handles each individual item in the list view.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CategoryData, listener: (CategoryData) -> Unit) = with(itemView) {
            if (item.photoUri != "") {
                GlideApp.with(itemView.context)
                        .load(FirebaseStorage.getInstance().reference.child(item.photoUri))
                        .into(categoryImage)
            } else {
                categoryImage.setImageResource(R.drawable.placeholder2)
            }
            categoryTitle.text = item.displayString
            countTextView.text = resources.getString(R.string.format_unanswered).format(item.unanswered)
            cardView.setCardBackgroundColor(Color.parseColor("#%s".format(item.color)))

            setOnClickListener { listener(item) }
        }
    }
}
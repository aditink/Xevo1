package xevo.xevo1.models

import android.graphics.Color
import android.support.v4.content.res.ResourcesCompat.getColor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.category_card.view.*
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
            if (item.photoUri == null) {
                categoryImage.setImageResource(R.drawable.placeholder2)
            } else {
                categoryImage.setImageURI(item.photoUri)
            }
            categoryTitle.text = item.displayString
            countTextView.text = "11 new"
            cardView.setCardBackgroundColor(Color.parseColor("#%s".format(item.color)))

            setOnClickListener { listener(item) }
        }
    }
}
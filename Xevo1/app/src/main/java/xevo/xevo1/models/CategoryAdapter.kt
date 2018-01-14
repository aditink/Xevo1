package xevo.xevo1.models

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.case_list_item.view.*
import kotlinx.android.synthetic.main.category_card.view.*
import xevo.xevo1.R
import xevo.xevo1.enums.CaseType

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
            categoryTitle.text = item.category

            setOnClickListener { listener(item) }
        }
    }
}
package xevo.xevo1.models

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.case_list_item.view.*
import xevo.xevo1.Database.DatabaseModels.CaseOverview
import xevo.xevo1.R
import xevo.xevo1.enums.CaseType

/**
 * Recycler Adapter class. Handles showing cases in the list view.
 */
class CaseAdapter(val items: List<CaseOverview>, val listener: (CaseOverview) -> Unit): RecyclerView.Adapter<CaseAdapter.ViewHolder>() {

    // Adding a method to ViewGroup
    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.case_list_item))

    /**
     * Class handles each individual item in the list view.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CaseOverview, listener: (CaseOverview) -> Unit) = with(itemView) {
            caseListTitle.text = item.title
            caseListDescription.text = item.description
            when (item.caseType) {
                CaseType.QUICK_HIT -> caseListIcon.setImageResource(R.drawable.quick_mode_on_48)
                CaseType.DEEP_DIVE -> caseListIcon.setImageResource(R.drawable.talk_48)
                CaseType.HEAVY_LIFT -> caseListIcon.setImageResource(R.drawable.businessman_48)
                else -> caseListIcon.setImageResource(R.drawable.quick_mode_on_48)
            }

            setOnClickListener { println(item); listener(item) }
        }
    }
}
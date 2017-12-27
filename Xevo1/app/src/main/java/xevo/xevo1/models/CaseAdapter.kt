package xevo.xevo1.models

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.case_list_item.view.*
import xevo.xevo1.R

class CaseAdapter(val items: List<CaseData>, val listener: (CaseData) -> Unit): RecyclerView.Adapter<CaseAdapter.ViewHolder>() {

    // Adding a method to ViewGroup
    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.case_list_item))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CaseData, listener: (CaseData) -> Unit) = with(itemView) {
            caseListTitle.text = item.title
            caseListDescription.text = item.description
            setOnClickListener { listener(item) }
        }
    }
}
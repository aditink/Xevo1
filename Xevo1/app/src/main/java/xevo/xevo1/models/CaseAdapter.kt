package xevo.xevo1.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.case_list_item.view.*
import xevo.xevo1.R

class CaseAdapter(context: Context, items: List<CaseData>) : BaseAdapter() {

    val mLayoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val mContext: Context = context
    val mItems: List<CaseData> = items

    override fun getItem(index: Int): CaseData {
        return mItems[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getView(index: Int, view: View?, parent: ViewGroup?): View {
        val v = mLayoutInflator.inflate(R.layout.case_list_item, parent, false)
        v.case_list_primary.text = mItems[index].title
        v.case_list_secondary.text = mItems[index].description
        return v
    }
}
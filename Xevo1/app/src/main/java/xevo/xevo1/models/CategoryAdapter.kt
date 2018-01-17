package xevo.xevo1.models

import android.graphics.Color
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.category_card.view.*
import xevo.xevo1.GlideApp
import xevo.xevo1.R

/**
 * Recycler Adapter class. Handles showing cases in the list view.
 */
class CategoryAdapter(val listener: (CategoryData) -> Unit,
                      val starListener: (CategoryData, Boolean) -> Unit): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val sortedList: SortedList<CategoryData>
    private val idMap: HashMap<String, CategoryData> = HashMap()

    init {
        sortedList = SortedList(CategoryData::class.java, object : SortedListAdapterCallback<CategoryData>(this) {
            // compare favorite, then unanswered (maybe change later)
            override fun compare(o1: CategoryData?, o2: CategoryData?): Int {
                o1!!; o2!!

                if (areItemsTheSame(o1, o2)) return 0;

                if (o1.favorite == o2.favorite) {
                    return o1.displayString.compareTo(o2.displayString)
                } else {
                    return if (o1.favorite) -1 else 1
                }
            }

            // objects are actually equal
            override fun areContentsTheSame(oldItem: CategoryData?, newItem: CategoryData?): Boolean {
                println("contents: " + (oldItem == newItem))
                return oldItem == newItem
            }

            // objects are logicall equally (i.e ids are the same, but contents aren't)
            override fun areItemsTheSame(item1: CategoryData?, item2: CategoryData?): Boolean {
                println("items: " + (item1?.dbString == item2?.dbString))
                return item1?.dbString == item2?.dbString
            }
        })
    }

    // Adding a method to ViewGroup
    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sortedList[position], listener, starListener)

    override fun getItemCount(): Int = sortedList.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.category_card))

    fun add(data: CategoryData) {
        println("Add: " + data)
        sortedList.add(data)
    }

    fun updateStar(data: CategoryData) {
        for (i in (0..sortedList.size()-1)) {
            if (sortedList[i].dbString == data.dbString) {
                sortedList.updateItemAt(i, data)
            }
        }
    }

    /**
     * Class handles each individual item in the list view.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CategoryData, listener: (CategoryData) -> Unit,
                 starListener: (CategoryData, Boolean) -> Unit) = with(itemView) {
            favoriteButton.setFavorite(item.favorite, false)
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
            favoriteButton.setOnClickListener { _ -> starListener(item, !item.favorite)}
        }
    }
}
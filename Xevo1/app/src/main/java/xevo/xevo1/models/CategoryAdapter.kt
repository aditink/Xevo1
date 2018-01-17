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
 * Recycler Adapter class for the Category View.
 */
class CategoryAdapter(private val listener: (CategoryData) -> Unit,
                      private val starListener: (CategoryData, Boolean) -> Unit): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    // Structure that holds the CategoryData
    private val sortedList: SortedList<CategoryData>

    init {
        sortedList = SortedList(CategoryData::class.java, object : SortedListAdapterCallback<CategoryData>(this) {
            // sort by favorite, and then by alphabet
            override fun compare(o1: CategoryData?, o2: CategoryData?): Int {
                o1!!; o2!! // non-null check

                if (areContentsTheSame(o1, o2)) return 0

                if (o1.favorite == o2.favorite) {
                    return o1.displayString.compareTo(o2.displayString)
                } else {
                    return if (o1.favorite) -1 else 1
                }
            }

            // objects are actually equal
            override fun areContentsTheSame(oldItem: CategoryData?, newItem: CategoryData?): Boolean {
                oldItem!!; newItem!!
                return oldItem.dbString == newItem.dbString && oldItem.unanswered == newItem.unanswered
            }

            // objects are logically equally (i.e ids are the same, but contents aren't)
            override fun areItemsTheSame(item1: CategoryData?, item2: CategoryData?): Boolean {
                return item1?.dbString == item2?.dbString
            }
        })
    }

    // Adding a method to ViewGroup
    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sortedList[position], listener, starListener)

    override fun getItemCount(): Int = sortedList.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.category_card))

    /**
     * Adds data to the SortedList. There is a check to ensure
     * that an item doesn't get added twice. If an item already
     * exists, it is updated instead.
     */
    fun add(data: CategoryData) {
        for (i in (0..sortedList.size()-1)) {
            if (sortedList[i].dbString == data.dbString) {
                data.favorite = sortedList[i].favorite
                sortedList.updateItemAt(i, data)
                return
            }
        }
        sortedList.add(data)
    }

    /**
     * Update an item when it is favorited.
     */
    fun updateStar(data: CategoryData) {
        for (i in (0..sortedList.size()-1)) {
            if (sortedList[i].dbString == data.dbString) {
                sortedList.updateItemAt(i, data)
                break
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
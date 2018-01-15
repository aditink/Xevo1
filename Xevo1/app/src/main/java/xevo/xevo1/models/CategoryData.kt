package xevo.xevo1.models

import android.net.Uri

/**
 * Created by samthomas on 1/14/18.
 */
data class CategoryData(
        val category: String,
        val photoUri: Uri?,
        val color: Int
)
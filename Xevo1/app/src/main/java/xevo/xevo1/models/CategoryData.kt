package xevo.xevo1.models

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class that holds data about Categories.
 * The Parcelize annotation automagically turns
 * this into a parcel which we can pass to Intents.
 * The Suppression is there because without it
 * AndroidStudio incorrectly thinks that there is an
 * error there.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CategoryData(
        val displayString: String = "",
        val color: String = "",
        var unanswered: Int = 0,
        var favorite: Boolean = false,
        val photoUri: String = "",
        var dbString: String = ""
) : Parcelable
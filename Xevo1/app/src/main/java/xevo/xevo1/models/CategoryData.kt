package xevo.xevo1.models

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by samthomas on 1/14/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CategoryData(
        val displayString: String = "",
        val color: String = "",
        var unanswered: Int = 0,
        val photoUri: String = "",
        var dbString: String = ""
) : Parcelable
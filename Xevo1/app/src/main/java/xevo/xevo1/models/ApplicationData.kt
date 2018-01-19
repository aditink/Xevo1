package xevo.xevo1.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by samthomas on 1/18/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ApplicationData (
        val userId: String = ""
) : Parcelable
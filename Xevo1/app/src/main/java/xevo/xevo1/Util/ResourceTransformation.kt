package xevo.xevo1.Util

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue

/**
 * Created by samthomas on 1/14/18.
 */

class ResourceTransformation() {

    companion object {
        /**
         * Takes a drawable resource id and converts it
         * to a URI.
         */
        public fun drawableToUri(resources: Resources, drawableId: Int): Uri {
            return Uri.parse("%s://%s/%s/%s".format(ContentResolver.SCHEME_ANDROID_RESOURCE,
                    resources.getResourcePackageName(drawableId),
                    resources.getResourceTypeName(drawableId),
                    resources.getResourceEntryName(drawableId)))
        }

        /**
         * Converting dp to pixel
         */
        public fun dpToPx(resources: Resources, dp: Int): Int {
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics))
        }
    }
}
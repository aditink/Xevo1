package xevo.xevo1.Util

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri

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
    }
}
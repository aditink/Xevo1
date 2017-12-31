package xevo.xevo1

import android.support.v4.app.Fragment

/**
 * XevoFragment. Subclass of [Fragment] and adds the title
 * field and the fragmentTag field to the Fragment class
 * so that it is easier to display Fragments in Main.
 */
abstract class XevoFragment : Fragment() {
    public abstract val title: Int
    public abstract val fragmentTag: String
    public abstract val expandable: Boolean
}
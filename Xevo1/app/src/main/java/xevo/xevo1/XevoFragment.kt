package xevo.xevo1

import android.support.v4.app.Fragment

/**
 * Created by samthomas on 12/17/17.
 */
abstract class XevoFragment : Fragment() {
    public abstract val title : Int
    public abstract val fragmentTag : String
}
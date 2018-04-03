package xevo.xevo1.Util

import android.content.Intent
import android.support.v7.app.AppCompatActivity
/**
 * Created by aditi on 4/2/18.
 */
abstract class XevoActivity : AppCompatActivity() {

    /**
     * Start new activity of type activity_name, clear the last numToClear activities.
     * numToClear must be between 0 and 2, inclusive.
     */
    fun startNewActivity(activity_name : Class<*>, numToClear : Int = 0,
                         extras : Array<Extra> = arrayOf<Extra>()){
        val intent = Intent(this, activity_name)
        if (numToClear == 2) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        for (extra in extras) {
            intent.putExtra(extra.name, extra.value)
        }
        startActivity(intent)
        if (numToClear == 1) {
            finish()
        }
    }
}
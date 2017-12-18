package xevo.xevo1

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_quick_hit.*

/**
 * Minimal Activity for Quick Hit
 */
class QuickHit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_hit)
        setSupportActionBar(toolbar)
    }
}

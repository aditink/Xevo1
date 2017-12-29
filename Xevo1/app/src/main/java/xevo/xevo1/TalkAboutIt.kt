package xevo.xevo1

import android.annotation.TargetApi
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quick_hit.*

import kotlinx.android.synthetic.main.activity_talk_about_it.*

/**
 * Minimal Activity for Talk About It
 */
class TalkAboutIt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_about_it)
        setSupportActionBar(talkAboutItToolbar)

        @TargetApi(21)
        talkAboutItToolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
        talkAboutItToolbar.setNavigationOnClickListener { _ ->
            finish()
        }
    }

}

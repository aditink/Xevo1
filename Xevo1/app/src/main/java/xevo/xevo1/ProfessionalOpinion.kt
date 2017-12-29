package xevo.xevo1

import android.annotation.TargetApi
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_professional_opinion.*
import kotlinx.android.synthetic.main.activity_talk_about_it.*

/**
 * Minimal Activity for ProfessionalOpinion.
 */
class ProfessionalOpinion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional_opinion)
        setSupportActionBar(professionalToolbar)

        @TargetApi(21)
        professionalToolbar.navigationIcon = getDrawable(R.drawable.ic_close_white_24dp)
        professionalToolbar.setNavigationOnClickListener { _ ->
            finish()
        }
    }

}

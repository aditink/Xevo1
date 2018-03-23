package xevo.xevo1.Payment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import xevo.xevo1.R
import android.webkit.WebView



class PaymentPage : AppCompatActivity() {

    private var amount = 0;
    private var default = 5;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_page)
        val myWebView = findViewById<View>(R.id.webview) as WebView
        myWebView.loadUrl("http://www.example.com")
        amount = intent.getIntExtra("Amount", default)
    }
}

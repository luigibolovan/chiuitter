package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.view_home.*
import ro.upt.ac.chiuitter.ComposeActivity.Companion.EXTRA_TEXT

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_home)

        ibt_share.setOnClickListener { shareChiuit(txv_content.text.toString()) }
        fab_add.setOnClickListener { composeChiuit() }
    }

    /*
    Defines text sharing/sending *implicit* intent, opens the application chooser menu,
    and starts a new activity which supports sharing/sending text.
     */
    private fun shareChiuit(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        val intentChooser = Intent.createChooser(sendIntent, "")

        startActivity(intentChooser)
    }

    /*
    Defines an *explicit* intent which will be used to start ComposeActivity.
     */
    private fun composeChiuit() {
        val composeIntent = Intent(this, ComposeActivity::class.java)

        // We start a new activity that we expect to return the acquired text as the result.
        startActivityForResult(composeIntent, COMPOSE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            val receivedText : String? = data.getStringExtra(EXTRA_TEXT)

            if(!receivedText.isNullOrEmpty()){
                txv_content.text = receivedText
            }

        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}

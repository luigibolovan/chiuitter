package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chiuit.*
import kotlinx.android.synthetic.main.view_home.*
import ro.upt.ac.chiuitter.ComposeActivity.Companion.EXTRA_TEXT

class HomeActivity : AppCompatActivity() {

    private val dummyChiuitStore = DummyChiuitStore()

    private lateinit var listAdapter: ChiuitRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_home)

        fab_add.setOnClickListener(View.OnClickListener { composeChiuit() })

        initList()
    }

    private fun initList() {
        val chiuitList = dummyChiuitStore.getAllData()

        listAdapter = ChiuitRecyclerViewAdapter(chiuitList.toMutableList()){
            shareChiuit(it.description)
        }
        rv_chiuit_list.adapter = listAdapter
        rv_chiuit_list.layoutManager = LinearLayoutManager(this)
    }

    /*
    Defines text sharing/sending *implicit* intent, opens the application chooser menu,
    and starts a new activity which supports sharing/sending text.
     */
    private fun shareChiuit(text: String) {
        val sendIntent = Intent().apply {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }

        }

        val intentChooser = Intent.createChooser(sendIntent, "")

        startActivity(intentChooser)
    }

    /*
     * Defines an *explicit* intent which will be used to start ComposeActivity.
     */
    private fun composeChiuit() {
        val composeActivityIntent = Intent(this, ComposeActivity::class.java)

        // We start a new activity that we expect to return the acquired text as the result.
        startActivityForResult(composeActivityIntent, COMPOSE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            val receivedTxt : String? = data.getStringExtra(EXTRA_TEXT);

            if(!receivedTxt.isNullOrEmpty()){
                listAdapter.addItem(Chiuit(receivedTxt))
            }
        }
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}

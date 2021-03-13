package com.nick_sib.refactoringdevelop.view.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.nick_sib.core.BaseActivity
import com.nick_sib.model.AppStateList
import com.nick_sib.model.ThrowableInternet
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.core.adapter.MainAdapter
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
//import com.nick_sib.historyscreen.HistoryActivity
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import com.nick_sib.utils.viewById
import org.koin.android.viewmodel.ext.android.viewModel

private const val HISTORY_ACTIVITY_PATH = "com.nick_sib.historyscreen.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"

class MainActivity: BaseActivity<AppStateList, String>() {


    private lateinit var splitInstallManager: SplitInstallManager

    override val model: MainViewModel by viewModel()

    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.rv_search_result)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)


    private lateinit var binding: ActivityMainBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.subscribe().observe(this@MainActivity, { renderData(it) })
        model.internetState.observe(this, { showInternetState(it) } )

        searchFAB.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.instance()
            searchDialogFragment.setOnSearchClickListener {
                model.getData(it)
            }
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        adapter = MainAdapter { data ->
            startActivity(com.nick_sib.descriptionscreen.DescriptionActivity.getIntent(this@MainActivity, data))
        }

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mainActivityRecyclerView.addItemDecoration(itemDecorator)

        mainActivityRecyclerView.adapter = adapter
    }

    override fun renderData(dataModel: AppStateList) {
        when (dataModel) {
            is AppStateList.Success -> {
                hideLoadingDialog()
                hideErrorDialog()
                adapter.data = dataModel.data
            }
            is AppStateList.Error -> {
                if (dataModel.error is ThrowableInternet)
                    showErrorDialog(
                        binding.root,
                        R.string.dialog_message_device_is_offline)
                else showErrorDialog(binding.root, dataModel.error.message, null)
            }
            is AppStateList.Loading -> {
                hideErrorDialog()
                showLoadingIndocator()
            }
        }
    }

    private fun showInternetState(state: Boolean){
        if (state) hideErrorDialog() else
            showErrorDialog(
                binding.root,
                R.string.dialog_message_device_is_offline
            )
    }

    override fun hideLoadingDialog(){
        loadDialog.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
    }

    private fun showLoadingIndocator(){
        loadDialog.visibility = View.VISIBLE
        binding.searchFab.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                        .build()

                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Log.d("myLOG", "Couldn't download feature: " + it.message)
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                true
            }
//            R.id.menu_history -> {
//                startActivity(Intent(this, HistoryActivity::class.java))
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }
}
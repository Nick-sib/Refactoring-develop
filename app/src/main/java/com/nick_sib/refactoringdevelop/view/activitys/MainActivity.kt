package com.nick_sib.refactoringdevelop.view.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.nick_sib.core.BaseActivity
import com.nick_sib.model.AppStateList
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.core.adapter.MainAdapter
import com.nick_sib.descriptionscreen.DescriptionActivity
import com.nick_sib.model.ThrowableInternet
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.di.injectDependencies
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import com.nick_sib.utils.viewById
import org.koin.android.scope.currentScope

private const val HISTORY_ACTIVITY_PATH = "com.nick_sib.historyscreen.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"

class MainActivity: BaseActivity<AppStateList, String>() {

    private lateinit var splitInstallManager: SplitInstallManager

    override lateinit var model: MainViewModel

    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.rv_search_result)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
    private var themeMenu: MenuItem? = null

    private lateinit var binding: ActivityMainBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectDependencies()
        val viewModel: MainViewModel by currentScope.inject()
        model = viewModel
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
            startActivity(DescriptionActivity.getIntent(this@MainActivity, data))
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
                showErrorDialog(binding.root, dataModel.error)
            }
            is AppStateList.Loading -> {
                hideErrorDialog()
                showLoadingIndocator()
            }
        }
    }

    private fun showInternetState(state: Boolean){
        if (state) hideErrorDialog() else
            showErrorDialog(binding.root, ThrowableInternet())
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
        themeMenu = menu?.findItem(R.id.menu_day_night)
        model.themeState.observe(this, {
            themeMenu?.icon = ContextCompat.getDrawable(
                this, if (it) R.drawable.ic_day else R.drawable.ic_night
            )
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_day_night -> {
                model.switchTheme()
                true
            }
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }
}
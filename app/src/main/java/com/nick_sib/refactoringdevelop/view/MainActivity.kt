package com.nick_sib.refactoringdevelop.view


import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.presenter.IPresenter
import com.nick_sib.refactoringdevelop.presenter.MainPresenterImpl
import com.nick_sib.refactoringdevelop.view.adapter.MainAdapter
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.base.IView
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import com.nick_sib.refactoringdevelop.view.main.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override lateinit var model: MainViewModel
//    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.instance()
//            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
//    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
//        object : MainAdapter.OnListItemClickListener {
//            override fun onItemClick(data: DataModel) {
//                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
//            }
//        }
//    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
//        object : SearchDialogFragment.OnSearchClickListener {
//            override fun onClick(searchWord: String) {
//                isNetworkAvailable = isOnline(applicationContext)
//                if (isNetworkAvailable) {
//                    model.getData(searchWord, isNetworkAvailable)
//                } else {
//                    showNoInternetConnectionDialog()
//                }
//            }
//        }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })

        findViewById<FloatingActionButton>(R.id.search_fab).setOnClickListener(fabClickListener)
//        findViewById<RecyclerView>(R.id.main_activity_recyclerview).layoutManager = LinearLayoutManager(applicationContext)
//        findViewById<RecyclerView>(R.id.main_activity_recyclerview).adapter = adapter
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showAlertDialog(
                        getString(R.string.dialog_tittle_sorry),
                        getString(R.string.empty_server_response_on_success)
                    )
                } else {
//                    adapter.setData(data)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
//                    findViewById<ProgressBar>(R.id.progress_bar_horizontal).apply {
//                        visibility = View.VISIBLE
//                        progress = appState.progress
//                    }
//                    findViewById<ProgressBar>(R.id.progress_bar_round).visibility = View.GONE
//                    progress_bar_horizontal.
                } else {
//                    findViewById<ProgressBar>(R.id.progress_bar_horizontal).visibility = View.GONE
//                    findViewById<ProgressBar>(R.id.progress_bar_round).visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
//                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
//        findViewById<FrameLayout>(R.id.loading_frame_layout).visibility = View.GONE
    }

    private fun showViewLoading() {
//        findViewById<FrameLayout>(R.id.loading_frame_layout).visibility = View.VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}
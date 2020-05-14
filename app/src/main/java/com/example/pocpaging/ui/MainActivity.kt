package com.example.pocpaging.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pocpaging.R
import com.example.pocpaging.base.ViewState
import com.example.pocpaging.ui.paging.MovieListAdapter
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.state.observe(this, Observer {
            renderState(it)
        })

        mainViewModel.movieList.observe(this, Observer {
            adapter.submitList(it)
        })

        initView()
        actions()

    }

    private fun initView() {
        rc_movie_list.layoutManager = GridLayoutManager(this, 2)
        adapter = MovieListAdapter()
        rc_movie_list.adapter = adapter
    }

    private fun actions() {
        sw_refresh.setOnRefreshListener {
            mainViewModel.refresh()
        }
    }

    private fun renderState(state: ViewState?) {
        when (state) {
            is ViewState.LOADED -> {
                shimmer_list_loading.visibility = View.GONE
                sw_refresh.isRefreshing = false
                rc_movie_list.visibility = View.VISIBLE
            }
            is ViewState.Error -> {
                sw_refresh.isRefreshing = false
                Alerter.create(this)
                    .setTitle(R.string.app_name)
                    .setText(state.remoteError ?: getString(state.localError!!))
                    .setBackgroundColorInt(baseContext.getColor(R.color.red))
                    .show()
            }
            is ViewState.EMPTY_LIST -> {
                // show empty screen.
            }
        }

    }

}

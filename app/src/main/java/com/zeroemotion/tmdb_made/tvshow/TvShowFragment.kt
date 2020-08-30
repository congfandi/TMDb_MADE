package com.zeroemotion.tmdb_made.tvshow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zeroemotion.tmdb_made.R
import com.zeroemotion.tmdb_made.core.data.Resource
import com.zeroemotion.tmdb_made.databinding.FragmentTvShowBinding
import kotlinx.android.synthetic.main.fragment_tv_show.*
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by viewModel()
    private lateinit var dataBinding: FragmentTvShowBinding
    private val tvShowAdapter = TvShowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_show, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTvShow.apply {
            adapter = tvShowAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        observeTvShowList()

    }

    private fun observeTvShowList() {
        viewModel.tvShowList.observe(viewLifecycleOwner, Observer { tvshow ->
            Log.e("TvShowFragment", tvshow.message.toString())
            if (tvshow != null) {
                when (tvshow) {
                    is Resource.Loading -> tvShowLoading.visibility = View.VISIBLE
                    is Resource.Error -> {
                        tvShowLoading.visibility = View.GONE
                        tvShowError.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        tvShowLoading.visibility = View.GONE
                        tvShowError.visibility = View.GONE
                        rvTvShow.visibility = View.VISIBLE
                        tvShowAdapter.setData(tvshow.data)
                    }
                }
            }
        })
    }

}
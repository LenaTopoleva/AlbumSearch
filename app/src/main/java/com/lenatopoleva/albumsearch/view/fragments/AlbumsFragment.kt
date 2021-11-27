package com.lenatopoleva.albumsearch.view.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lenatopoleva.albumsearch.databinding.FragmentAlbumsBinding
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.imageloader.IImageLoader
import com.lenatopoleva.albumsearch.utils.network.isOnline
import com.lenatopoleva.albumsearch.utils.ui.BackButtonListener
import com.lenatopoleva.albumsearch.view.adapters.AlbumListAdapter
import com.lenatopoleva.albumsearch.view.base.BaseFragment
import com.lenatopoleva.albumsearch.viewmodel.fragments.AlbumsViewModel
import org.koin.android.ext.android.getKoin

class AlbumsFragment: BaseFragment<AppState>(), BackButtonListener {

    companion object{
        fun newInstance() = AlbumsFragment()
    }

    override val model: AlbumsViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(AlbumsViewModel::class.java)
    }

    private val imageLoader: IImageLoader<ImageView> by lazy {
        getKoin().get()
    }

    private var _binding: FragmentAlbumsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding
        get() = _binding!!

    private val observer = Observer<AppState> { renderData(it)  }

    private var adapter: AlbumListAdapter? = null
    private val onListItemClickListener: AlbumListAdapter.OnListItemClickListener =
        object : AlbumListAdapter.OnListItemClickListener {
            override fun onItemClick(albumId: Int) {
                model.albumClicked(albumId)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Subscribe on appStateLiveData changes
        model.subscribe().observe(viewLifecycleOwner, observer)
    }

    override fun onResume() {
        super.onResume()
        initSearch()
    }

    private fun initSearch() {
        with(binding) {
            searchTextInputLayout.editText?.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    isNetworkAvailable = isOnline(requireActivity().applicationContext)
                    model.getData(searchInput.text.toString(), isNetworkAvailable)
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun hideLoader() {
        binding.progressBar.visibility = View.GONE
        binding.albumsListRv.visibility = View.VISIBLE
    }

    override fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
        binding.albumsListRv.visibility = View.GONE
    }

    override fun handleData(data: List<Media>) {
        setDataToAdapter(data)
    }

    private fun setDataToAdapter(data: List<Media>){
        if (adapter == null){
            binding.albumsListRv.layoutManager = GridLayoutManager(context, 3)
            binding.albumsListRv.adapter = AlbumListAdapter(onListItemClickListener, data, imageLoader)
        } else adapter!!.setData(data)
    }

    override fun backPressed() = model.backPressed()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.lenatopoleva.albumsearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.albumsearch.databinding.FragmentAlbumDetailsBinding
import com.lenatopoleva.albumsearch.model.data.AppState
import com.lenatopoleva.albumsearch.model.data.Media
import com.lenatopoleva.albumsearch.model.data.entity.Album
import com.lenatopoleva.albumsearch.model.imageloader.IImageLoader
import com.lenatopoleva.albumsearch.utils.COLLECTION
import com.lenatopoleva.albumsearch.utils.mapToAlbum
import com.lenatopoleva.albumsearch.utils.network.isOnline
import com.lenatopoleva.albumsearch.utils.ui.BackButtonListener
import com.lenatopoleva.albumsearch.view.adapters.TrackListAdapter
import com.lenatopoleva.albumsearch.view.base.BaseFragment
import com.lenatopoleva.albumsearch.viewmodel.fragments.AlbumDetailsViewModel
import org.koin.android.ext.android.getKoin

class AlbumDetailsFragment: BaseFragment<AppState>(), BackButtonListener {

    companion object{
        private const val ALBUM_ID = "albumId"
        fun newInstance(albumId: Int) =
                AlbumDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ALBUM_ID, albumId)
                    }
                }
    }

    override val model: AlbumDetailsViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(AlbumDetailsViewModel::class.java)
    }

    val imageLoader: IImageLoader<ImageView> by lazy {
        getKoin().get()
    }

    private var _binding: FragmentAlbumDetailsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding
        get() = _binding!!

    private val observer = Observer<AppState> { renderData(it)  }

    private var adapter: TrackListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
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
        val albumId = this.arguments?.getInt(ALBUM_ID)
        albumId?.let {
            isNetworkAvailable = isOnline(requireActivity().applicationContext)
            model.getData(it, isNetworkAvailable)
        }
    }

    override fun hideLoader() {
        binding.views.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showLoader() {
        binding.views.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun handleData(data: List<Media>) {
        updateUi(data)
        setDataToAdapter(data)
    }

    private fun updateUi(data: List<Media>){
        val album: Album
        if (data.first().wrapperType == COLLECTION) {
            album = data.first().mapToAlbum()
            with(binding){
                albumInfoTv.text = album.collectionName
                artistInfoTv.text = album.artistName
                genreInfoTv.text = album.primaryGenreName
                countryInfoTv.text = album.country
                releaseDateInfoTv.text = album.releaseDate.substring(0, 10)
                imageLoader.loadInto(album.artworkUrl100, artworkIv)
            }
        }
    }

    private fun setDataToAdapter(data: List<Media>){
        if (adapter == null){
            binding.trackListRv.layoutManager = LinearLayoutManager(context)
            binding.trackListRv.adapter = TrackListAdapter(data.subList(1, data.size))
        } else adapter!!.setData(data)
    }

    override fun backPressed() = model.backPressed()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
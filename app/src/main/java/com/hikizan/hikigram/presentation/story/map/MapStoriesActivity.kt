package com.hikizan.hikigram.presentation.story.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityMapStoriesBinding
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.presentation.story.DetailStoryActivity
import com.hikizan.hikigram.presentation.view_model.StoryViewModel
import com.hikizan.hikigram.utils.ext.imageUrlToBitmap
import com.hikizan.hikigram.utils.ext.orZero
import com.hikizan.hikigram.utils.ext.setupHikizanToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MapStoriesActivity : HikizanActivityBase<ActivityMapStoriesBinding>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val storyViewModel: StoryViewModel by viewModel()
    
    override fun initViewBinding(): ActivityMapStoriesBinding {
        return ActivityMapStoriesBinding.inflate(layoutInflater)
    }

    private var mapStoryLocations = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        initIntent()
        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    override fun initIntent() {
    }

    override fun initUI() {
        binding?.apply {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this@MapStoriesActivity)
            
            setupHikizanToolbar(
                toolbarLayout = toolbarLayoutMap,
                title = "Map Stories",
                isChild = true
            )
        }
    }

    override fun initAction() {}

    override fun initProcess() {
        storyViewModel.fetchStoriesWithLocation()
    }

    override fun initObservers() {
        binding?.apply {
            storyViewModel.storiesWithLocationResult.observe(this@MapStoriesActivity) { listStory ->
                when (listStory) {
                    is State.Loading -> {
                        showLoading()
                    }
                    is State.Success -> {
                        hideLoading()
                        mapStoryLocations.addAll(listStory.data)
                        Timber.d("list story = ${listStory.data.size}")
                    }
                    is State.Error -> {
                        hideLoading()
                        val snackbar = Snackbar.make(
                            mapsContainer,
                            listStory.message.toString(), Snackbar.LENGTH_LONG
                        )
                            .setAction(getString(R.string.action_retry)) {
                                storyViewModel.fetchStoriesWithLocation()
                            }
                        snackbar.show()
                    }
                    is State.Empty -> {
                        hideLoading()
                        val snackbar = Snackbar.make(
                            mapsContainer,
                            getString(R.string.message_empty_state), Snackbar.LENGTH_LONG
                        )
                            .setAction(getString(R.string.action_retry)) {
                                storyViewModel.fetchStoriesWithLocation()
                            }
                        snackbar.show()
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        val jakarta = LatLng(-6.200000, 106.816666)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 5f))
        getMyLocation()

        if (mapStoryLocations.isNotEmpty()) {
            addStoriesMarker(mapStoryLocations)

            mMap.setOnMarkerClickListener { marker ->
                Timber.d("onMarkClicked: ELSE CONDITION: marker id = ${marker.snippet.toString()}")
                binding?.apply {
                    val snackbar = Snackbar.make(
                        mapsContainer,
                        getString(R.string.message_marker_clicked_instruction, marker.title),
                        Snackbar.LENGTH_LONG
                    ).setAction(getString(R.string.action_detail)) {
                        DetailStoryActivity.start(this@MapStoriesActivity, marker.snippet.toString())
                    }
                    snackbar.show()
                }
                false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun addStoriesMarker(storiesPlace: List<Story>) {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    storiesPlace.firstOrNull()?.lat.orZero(),
                    storiesPlace.firstOrNull()?.lng.orZero()
                ),
                15f
            )
        )

        storiesPlace.forEach { story ->
            val latitudeLongitude = LatLng(story.lat.orZero(), story.lng.orZero())

            this@MapStoriesActivity.lifecycleScope.launch {
                try {
                    val photoBitmap = withContext(Dispatchers.IO) {
                        story.photoUrl.imageUrlToBitmap(
                            this@MapStoriesActivity
                        )
                    }

                    mMap.addMarker(
                        MarkerOptions()
                            .position(latitudeLongitude)
                            .title(story.name)
                            .snippet(story.id)
                            .icon(BitmapDescriptorFactory.fromBitmap(
                                photoBitmap
                            ))
                    )

                } catch (e: Exception) {
                    Timber.d(e)
                }
            }

        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, MapStoriesActivity::class.java)
            )
        }
    }
}
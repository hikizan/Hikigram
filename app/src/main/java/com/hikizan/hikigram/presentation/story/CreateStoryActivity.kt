package com.hikizan.hikigram.presentation.story

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityCreateStoryBinding
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.presentation.main.MainActivity
import com.hikizan.hikigram.presentation.view_model.StoryViewModel
import com.hikizan.hikigram.utils.constants.AppConstants
import com.hikizan.hikigram.utils.ext.emptyString
import com.hikizan.hikigram.utils.ext.orEmptyString
import com.hikizan.hikigram.utils.ext.setupHikizanToolbar
import com.hikizan.hikigram.utils.ext.showToast
import com.hikizan.hikigram.utils.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CreateStoryActivity : HikizanActivityBase<ActivityCreateStoryBinding>() {

    companion object {

        fun start(context: Context) {
            context.startActivity(
                Intent(context, CreateStoryActivity::class.java)
            )
        }
    }

    private val storyViewModel: StoryViewModel by viewModel()

    override fun initViewBinding(): ActivityCreateStoryBinding {
        return ActivityCreateStoryBinding.inflate(layoutInflater)
    }

    private var mFile: File? = null

    private var imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding?.imgNewPhotoStory?.setImageURI(it.data?.data)
            mFile = it.data?.data?.let { imageUri ->
                uriToFile(imageUri, this)
            }
            hideLoading()
        } else {
            hideLoading()
        }
    }

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
            setupHikizanToolbar(
                toolbarLayout = layoutToolbarCreateStory,
                title = getString(R.string.title_new_story),
                isChild = true
            )
        }
    }

    override fun initAction() {
        binding?.apply {
            btnCamera.setOnClickListener {
                ImagePicker.with(this@CreateStoryActivity)
                    .cameraOnly()
                    .compress(AppConstants.MAXIMAL_IMAGE_FILE_SIZE)
                    .maxResultSize(1080, 1080)
                    .createIntent {
                        imageResult.launch(it)
                        showLoading()
                    }
            }
            btnGallery.setOnClickListener {
                ImagePicker.with(this@CreateStoryActivity)
                    .galleryOnly()
                    .compress(AppConstants.MAXIMAL_IMAGE_FILE_SIZE)
                    .maxResultSize(1080, 1080)
                    .createIntent {
                        imageResult.launch(it)
                        showLoading()
                    }
            }
            btnCreateNewStory.setOnClickListener {
                validateData(
                    mFile,
                    etNewDescStory.text.toString()
                )
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        binding?.apply {
            storyViewModel.createStoryResult.observe(this@CreateStoryActivity) { uploadResult ->
                when (uploadResult) {
                    is State.Loading -> {
                        showLoading()
                    }
                    is State.Success -> {
                        hideLoading()
                        MainActivity.startNewTask(this@CreateStoryActivity)
                    }
                    is State.Error -> {
                        hideLoading()
                        this@CreateStoryActivity.showToast(
                            uploadResult.message ?: getString(R.string.message_error_state)
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun validateData(
        photoFile: File?, description: String? = emptyString
    ) = binding?.apply {
        if (photoFile == null) {
            this@CreateStoryActivity.showToast(
                getString(R.string.message_image_empty)
            )
        } else if (description == emptyString) {
            tilNewDescStory.error = getString(R.string.message_description_empty)
        } else {
            storyViewModel.createStory(
                description.orEmptyString(),
                photoFile
            )
        }
    }
}
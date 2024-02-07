package com.ssafy.travelcollector.fragment.board

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.CommentAdapter
import com.ssafy.travelcollector.adapter.ImageSliderAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentHeritagePostBinding
import com.ssafy.travelcollector.dto.Comment
import com.ssafy.travelcollector.util.TimeConverter
import kotlinx.coroutines.launch
import retrofit2.http.Url

class BoardPostFragment : BaseFragment<FragmentHeritagePostBinding>(FragmentHeritagePostBinding::bind,
    R.layout.fragment_heritage_post
){

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val bottomSheet by lazy{ binding.root.findViewById<LinearLayout>(R.id.bottom_sheet)}
    private val commentRecyclerView by lazy{binding.root.findViewById<RecyclerView>(R.id.bottom_sheet_rv)}

    private val imageAdapter: ImageSliderAdapter by lazy{
        ImageSliderAdapter()
    }

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initView()

    }

    private fun initView(){
        binding.heritagePostVp2Images.offscreenPageLimit = 1
        binding.heritagePostVp2Images.adapter = imageAdapter

        lifecycleScope.launch{
            boardViewModel.boardDetail.collect{
                binding.heritagePostTvTitle.text = it.title
                binding.heritagePostText.text = it.content
                binding.heritagePostTvDay.text = TimeConverter.timeMilliToDateString(it.date)
                imageAdapter.submitList(it.images.map { Uri.parse(it.url)  })
                boardViewModel.loadComments(it.id)
            }
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.heritagePostEtComment.setOnEditorActionListener { _, id, _ ->
            if(id == EditorInfo.IME_ACTION_DONE){
                addComment()
            }
            true
        }
        binding.heritagePostBtnAddComment.setOnClickListener {
            addComment()
        }
        binding.heritagePostBtnShowAllComments.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initAdapter(){
        imageAdapter.imageBinder = object :ImageSliderAdapter.ImageBinder{
            override fun imageBind(url: String, imageView: ImageView) {
                Glide.with(requireContext())
                    .load(url)
                    .into(imageView)
            }
        }

        lifecycleScope.launch {
            boardViewModel.comments.collect{
                commentAdapter.submitList(it)
            }
        }

        commentAdapter.eventListener = object : CommentAdapter.EventListener{
            override fun delete(position: Int) {
                val comment = commentAdapter.currentList[position]
                boardViewModel.deleteComment(
                    comment.boardId, comment.id
                )
            }

        }
        commentRecyclerView.adapter = commentAdapter
    }

    private fun addComment(){
        lifecycleScope.launch {
            boardViewModel.addComment(
                boardViewModel.boardDetail.value.id,
                Comment(content = binding.heritagePostEtComment.text.toString())
            )
        }
        binding.heritagePostEtComment.setText("")
    }

}
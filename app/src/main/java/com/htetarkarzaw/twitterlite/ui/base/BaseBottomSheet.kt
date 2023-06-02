package com.htetarkarzaw.twitterlite.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.htetarkarzaw.twitterlite.InflateSheet
import com.htetarkarzaw.twitterlite.R

abstract class BaseBottomSheet<VB : ViewBinding>(
    private val inflate: InflateSheet<VB>
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    lateinit var mContext: Context
    open fun initialize() {}
    open fun setupListener() {}
    open fun setupView() {}
    open fun observe() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        initialize()
        setupListener()
        setupView()
        observe()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

    fun screenHeight(contentView: View) {
        val layoutParams =
            (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior
        if (behavior is BottomSheetBehavior) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

        val tv = TypedValue()
        var actionBarHeight = 0
        if (requireActivity().theme.resolveAttribute(
                com.google.android.material.R.attr.actionBarSize,
                tv,
                true
            )
        ) {
            actionBarHeight =
                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        val parent = contentView.parent as View
        parent.fitsSystemWindows = true
        val bottomSheetBehavior = BottomSheetBehavior.from(parent)
        contentView.measure(0, 0)
        val diametric = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(diametric)
        val screenHeight = diametric.heightPixels
        bottomSheetBehavior.peekHeight = screenHeight
        layoutParams.height = screenHeight - actionBarHeight - statusBarHeight
        parent.layoutParams = layoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
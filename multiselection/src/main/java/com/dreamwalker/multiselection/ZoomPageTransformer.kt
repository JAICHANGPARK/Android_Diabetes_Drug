package com.dreamwalker.multiselection

import android.support.v4.view.ViewPager
import android.view.View
import com.dreamwalker.multiselection.util.mix
import com.dreamwalker.multiselection.util.setScaleXY
import com.dreamwalker.multiselection.util.smoothstep


class ZoomPageTransformer(val pageWidth: Float) : ViewPager.PageTransformer {

    companion object {
        const val MIN_ZOOM = 0.8f
    }

    private val sidebarWidth: Float

    init {
        sidebarWidth = 1f - pageWidth
    }

    override fun transformPage(page: View, position: Float) {
        val scale = when {
            // left page is never scaled
            position <= 0 -> 1f
            else -> position.smoothstep(pageWidth, sidebarWidth).mix(1f, MIN_ZOOM)
        }

        page.pivotX = 0f
        page.setScaleXY(scale)
    }

}
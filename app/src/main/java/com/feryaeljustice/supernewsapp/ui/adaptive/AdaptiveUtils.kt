/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.ui.adaptive

import androidx.compose.material3.adaptive.HingeInfo
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.window.core.layout.WindowSizeClass

val LocalDeviceType = staticCompositionLocalOf<DeviceType> {
    error("No DeviceType provided")
}

sealed class DeviceType : Comparable<DeviceType> {
    abstract val minWidth: Int
    abstract val minHeight: Int
    abstract val rank: Int

    override fun compareTo(other: DeviceType): Int = this.rank - other.rank

    data class Compact(
        override val minWidth: Int,
        override val minHeight: Int,
        override val rank: Int = 0
    ) : DeviceType()

    data class Medium(
        override val minWidth: Int,
        override val minHeight: Int,
        override val rank: Int = 1
    ) : DeviceType()

    data class Foldable(
        override val minWidth: Int,
        override val minHeight: Int,
        override val rank: Int = 2,
        val isTableTop: Boolean,
        val hingeList: List<HingeInfo>
    ) : DeviceType()

    data class Expanded(
        override val minWidth: Int,
        override val minHeight: Int,
        override val rank: Int = 3
    ) : DeviceType()

    data class Large(
        override val minWidth: Int,
        override val minHeight: Int,
        override val rank: Int = 4
    ) : DeviceType()

    data class ExtraLarge(
        override val minWidth: Int,
        override val minHeight: Int,
        override val rank: Int = 5
    ) : DeviceType()

    companion object {
        val Compact = Compact(minWidth = 0, minHeight = 0)
        val Medium = Medium(minWidth = 0, minHeight = 0)
        val Foldable =
            Foldable(minWidth = 0, minHeight = 0, isTableTop = false, hingeList = emptyList())
        val Expanded = Expanded(minWidth = 0, minHeight = 0)
        val Large = Large(minWidth = 0, minHeight = 0)
        val ExtraLarge = ExtraLarge(minWidth = 0, minHeight = 0)
    }
}

fun getDeviceType(
    windowAdaptiveInfo: WindowAdaptiveInfo
): DeviceType {
    val width = windowAdaptiveInfo.windowSizeClass.minWidthDp
    val height = windowAdaptiveInfo.windowSizeClass.minHeightDp

    val aspectRatio = width.toFloat() / height.toFloat()

    // Foldable check (remains first)
    if (windowAdaptiveInfo.windowPosture.hingeList.isNotEmpty()) {
        return DeviceType.Foldable(
            minWidth = width,
            minHeight = height,
            isTableTop = windowAdaptiveInfo.windowPosture.isTabletop,
            hingeList = windowAdaptiveInfo.windowPosture.hingeList
        )
    }

    // Device classification based on width and aspect ratio
    return when {
        width >= WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND -> DeviceType.Expanded(width, height)
        width >= WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND -> {
            if (aspectRatio < 1.6f) DeviceType.Medium(width, height)
            else DeviceType.Compact(width, height)
        }

        else -> DeviceType.Compact(width, height)
    }
}

infix fun DeviceType.greaterThan(other: DeviceType): Boolean = this > other
infix fun DeviceType.greaterThanOrEqual(other: DeviceType): Boolean = this >= other
infix fun DeviceType.smallerThan(other: DeviceType): Boolean = this < other
infix fun DeviceType.smallerThanOrEqual(other: DeviceType): Boolean = this <= other

val DeviceType.isLandscapePhone: Boolean
    get() = minWidth > minHeight
            && minHeight < 500
            && (minWidth.toFloat() / minHeight.toFloat()) >= 1.8f
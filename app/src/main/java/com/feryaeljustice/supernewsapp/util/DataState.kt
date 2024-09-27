/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.util

sealed class DataState<T> {

    data class Loading<T>(val isLoading: Boolean) : DataState<T>()

    data class Success<T>(val data: T) : DataState<T>()

    data class Response<T>(val uiComponent: UIComponent, val error: Exception? = null) :
        DataState<T>()
}

sealed class UIComponent {

    data class Toast(val message: String) : UIComponent()

    data class Dialog(val title: String, val message: String) : UIComponent()

    data class None(val message: String? = null) : UIComponent()

}
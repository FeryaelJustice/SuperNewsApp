/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeldev.supernewsapp.domain.usecase.app_entry

import com.feryaeldev.supernewsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(private val localUserManager: LocalUserManager) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}
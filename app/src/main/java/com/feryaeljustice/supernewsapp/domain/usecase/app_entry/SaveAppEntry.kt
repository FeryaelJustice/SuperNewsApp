package com.feryaeljustice.supernewsapp.domain.usecase.app_entry

import com.feryaeljustice.supernewsapp.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppEntry
@Inject
constructor(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}

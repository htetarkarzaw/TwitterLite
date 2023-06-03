package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.domain.repository.AuthRepository
import javax.inject.Inject

class logoutUsecase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke() {
        return repo.logout()
    }
}
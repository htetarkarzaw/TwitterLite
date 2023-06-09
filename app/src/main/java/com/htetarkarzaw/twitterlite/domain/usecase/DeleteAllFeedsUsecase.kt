package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import javax.inject.Inject

class DeleteAllFeedsUsecase @Inject constructor(private val repo: FeedRepository) {
    suspend operator fun invoke() {
        return repo.deleteAllFeeds()
    }
}
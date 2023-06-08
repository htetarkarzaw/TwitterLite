package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFeedsUsecase @Inject constructor(private val repo: FeedRepository) {
    operator fun invoke(feedVO: FeedVO): Flow<Resource<String>> {
        return repo.deleteFeed(feedVO = feedVO)
    }
}
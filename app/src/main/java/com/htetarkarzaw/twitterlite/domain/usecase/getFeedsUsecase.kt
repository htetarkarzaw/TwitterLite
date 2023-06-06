package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import com.htetarkarzaw.twitterlite.domain.vo.FeedVO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getFeedsUsecase @Inject constructor(private val repo: FeedRepository) {
    suspend operator fun invoke(): Flow<Resource<List<FeedVO>>> {
        return repo.getFeeds()
    }
}
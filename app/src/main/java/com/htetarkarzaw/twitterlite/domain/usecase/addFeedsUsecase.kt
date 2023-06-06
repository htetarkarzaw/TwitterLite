package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.domain.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class addFeedsUsecase @Inject constructor(private val repo: FeedRepository) {
    suspend operator fun invoke(feedCriteria: FeedCriteria): Flow<Resource<String>> {
        return repo.addFeeds(feedCriteria)
    }
}
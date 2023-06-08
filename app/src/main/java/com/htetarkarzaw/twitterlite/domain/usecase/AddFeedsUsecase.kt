package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.data.Resource
import com.htetarkarzaw.twitterlite.data.firebase.criteria.FeedCriteria
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFeedsUsecase @Inject constructor(private val repo: FeedRepository) {
    operator fun invoke(feedCriteria: FeedCriteria): Flow<Resource<String>> {
        return repo.addFeed(feedCriteria)
    }
}
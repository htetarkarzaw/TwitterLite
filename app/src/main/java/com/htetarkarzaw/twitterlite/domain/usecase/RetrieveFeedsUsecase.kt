package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RetrieveFeedsUsecase @Inject constructor(private val repo: FeedRepository) {
    suspend operator fun invoke(): Flow<List<FeedVO>> {
        return repo.retrieveFeeds().map { list ->
            list.map { it.toVO() }.sortedByDescending { it.date }
        }
    }
}
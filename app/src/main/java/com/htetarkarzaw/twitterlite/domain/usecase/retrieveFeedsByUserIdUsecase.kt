package com.htetarkarzaw.twitterlite.domain.usecase

import com.htetarkarzaw.twitterlite.data.firebase.vo.FeedVO
import com.htetarkarzaw.twitterlite.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class retrieveFeedsByUserIdUsecase @Inject constructor(private val repo: FeedRepository) {
    suspend operator fun invoke(userId: String): Flow<List<FeedVO>> {
        return repo.retrieveFeedsById(userId).map { list ->
            list.map { it.toVO() }.sortedByDescending { it.date }
        }
    }
}
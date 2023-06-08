package com.htetarkarzaw.twitterlite.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.htetarkarzaw.twitterlite.domain.repository.AuthRepositoryImpl
import com.klt.test_shared.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private var repo: AuthRepositoryImpl? = null

    @get: Rule
    val testRule = TestCoroutinesRule()

    @Before
    fun setup() {
        firebaseAuth = mock(FirebaseAuth.getInstance())
        storageRef = mock(FirebaseStorage.getInstance().reference)
        firestore = mock(FirebaseFirestore.getInstance())
        repo = AuthRepositoryImpl(
            firebaseAuth = firebaseAuth,
            storageRef = storageRef,
            firestore = firestore
        )
    }

    @After
    fun teardown() {
        repo = null
    }

    @Test
    fun getFeed() = runTest {
    }
}
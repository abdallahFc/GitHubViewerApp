package com.example.githubviewerapp.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.githubviewerapp.data.source.local.entities.RepositoryEntity
import com.example.githubviewerapp.domain.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class GithubDataBaseTest {
    private lateinit var db: GithubDataBase
    private lateinit var repositoryDao: RepositoryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), GithubDataBase::class.java
        ).allowMainThreadQueries().build()
        repositoryDao = db.repositoryDao
    }

    @Test
    fun insertAndRetrieveRepositories() = runBlocking {
        val repositories = listOf(
            RepositoryEntity("1", "name1", "description1",
                User("id","user","imageUrl")),
            RepositoryEntity("2", "name1", "description1",
                User("id","user","imageUrl")),
        )

        repositoryDao.insertRepositories(repositories)

        val retrievedRepositories = repositoryDao.getAllRepositories()

        assert(retrievedRepositories.size == repositories.size)
        assert(retrievedRepositories.containsAll(repositories))
    }

    @Test
    fun insertAndDeleteRepositories() = runBlocking {
        val repositories = listOf(
            RepositoryEntity("1", "name1", "description1",
                User("id","user","imageUrl")),
            RepositoryEntity("2", "name1", "description1",
                User("id","user","imageUrl")),
        )

        repositoryDao.insertRepositories(repositories)

        repositoryDao.deleteAllRepositories()

        val retrievedRepositories = repositoryDao.getAllRepositories()

        assert(retrievedRepositories.isEmpty())
    }


}

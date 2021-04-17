package com.cartrackers.app.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cartrackers.app.CoroutineTestRule
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.di.*
import com.cartrackers.app.features.home.HomeViewModel
import com.cartrackers.baseplate_persistence.module.databaseModule
import io.kotlintest.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.*
import org.junit.rules.TestRule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExperimentalCoroutinesApi
class RoomUnitTest: KoinTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel: HomeViewModel by inject()

    private lateinit var userList: List<User>

    @BeforeEach
    fun initialized() {
        /** for dependency injection **/
        startKoin {
            printLogger()
            androidContext(provideAndroidContext())
            modules(listOf(
                networkModule,
                repositoryModule,
                mapperModule,
                viewModelModule,
                databaseModule
            ))
        }
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun endTest(){
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    @Order(1)
    fun `initialized viewModel get all user`() = runBlocking {
        viewModel.getUserFromDomain()
    }

    @Test
    @Order(2)
    fun `start collect all user data from domain`() = runBlocking {
        viewModel.listDomainState.take(2).collect { result ->
            if(result is State.Data) {
                val data = result.data
                if(data.isNotEmpty()) {
                    userList = data
                }
                data.count() shouldBe 10
                println(data)
            }
        }
    }

    @Test
    @Order(3)
    fun `get first element of user list from domain`() {
        val user = userList.first()
        user.name shouldBe "Leanne Graham"
        user.company.name shouldBe "Romaguera-Crona"
        println(user)
   }

    /**
     * TODO mock room unit test
     * **/

    @Test
    @Order(4) @Disabled
    fun `insert elements to userDao`() {
        for(i in 1..userList.count()) {
            val user = userList[i]
            val password = "password$i"
            viewModel.insertUserToDB(user, password)
        }
    }

    @Test
    @Order(5) @Disabled
    fun `retrieve all user from userDao`() {
        viewModel.getUserFromDb()
    }

    @Test
    @Order(6) @Disabled
    fun `start collecting data from userDao`() = runBlocking {
        viewModel.listModelState.take(2).collect { result ->
            if(result is State.Data) {
                val data = result.data
                data.count() shouldBe 10
                println(data)
            }
        }
    }
}
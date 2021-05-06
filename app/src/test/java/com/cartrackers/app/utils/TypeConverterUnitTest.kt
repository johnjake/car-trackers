package com.cartrackers.app.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cartrackers.app.CoroutineTestRule
import com.cartrackers.app.data.vo.Address
import com.cartrackers.app.data.vo.Coordinates
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.di.networkModule
import com.cartrackers.app.di.repositoryModule
import com.cartrackers.app.di.viewModelModule
import com.cartrackers.app.extension.*
import com.cartrackers.app.features.home.HomeViewModel
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.string.shouldBeEqualIgnoringCase
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.reflect.KClass

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TypeConverterUnitTest: KoinTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel: HomeViewModel by inject()

    private lateinit var userList: List<User>
    private lateinit var userString: String

    @ExperimentalCoroutinesApi
    @BeforeEach
    fun initialized() {
        /** for dependency injection **/
        startKoin {
            printLogger()
            modules(listOf(
                networkModule,
                repositoryModule,
                viewModelModule
            ))
        }
        Dispatchers.setMain(dispatcher)
    }

    @ExperimentalCoroutinesApi
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
    fun `start collect user data`() = runBlocking {
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
    fun `type converter from list to string`() {
        val result = userList.toStringType()
        userString = result
        println(result)
        result shouldNotBe result.isEmpty()
    }

    @Test
    @Order(4)
    fun `type converter from string to list`() {
        val resultList = userString.toListType<User>()
        println(resultList.count())
        userList.size shouldBeExactly resultList.size
    }

    @Test
    @Order(5)
    fun `type converter from object class to string`() {
        val expected = "{\"lat\":7.6576,\"lng\":56.4456}"
        val coordinates = Coordinates(lat = 7.6576, lng = 56.4456)
        val json = coordinates.toJsonType()
        json?.shouldBeEqualIgnoringCase(expected)
    }

    @Test
    @Order(6)
    fun `type converter from json type to object class`() {
        val coordinates = Coordinates(lat = 7.6576, lng = 56.4456)
        val json = coordinates.toJsonType()
        val classType = json?.toClassType<Coordinates>()
        println(classType?.lat)
    }

    @Test
    @Order(7)
    fun `type converter address to string type then to class`() {
        val address = Address(
            street= "Kulas Light",
            suite= "Apt. 556",
            city= "Gwenborough",
            zipcode= "92998-3874",
            Coordinates (lat= -37.3159,lng= 81.1496))
        val json = address.toJsonType()
        val classType = json?.toClassType<Address>()
        classType?.geo?.lat shouldBe -37.3159
        println(json)
    }

    @Test
    @Order(8)
    fun `determined the type of class`() {
        val addressType: KClass<Address> = Address::class
        val type = addressType.toCheckClassType()
        type shouldBeEqualIgnoringCase "Data"
    }
}
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

internal class ApiClientTest {
    val config = Config(BaseUrl("https://www.google.de"), defaultApiKey)

    @Test
    fun testClientGet() {
        runBlocking {
            val client = ApiClient(config)
            val checkResult = client.checkConnection()
            assertEquals(ConnectionCheckResult.Success, checkResult)
        }
    }
}

val testServerPort = System.getProperty("testServerPort").toInt()
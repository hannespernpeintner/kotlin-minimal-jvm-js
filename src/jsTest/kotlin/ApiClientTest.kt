import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlin.test.Test
import kotlin.test.assertEquals

fun runTest(block: suspend () -> Unit): dynamic = GlobalScope.promise { block() }

class ApiClientTest {
    @Test
    fun testClientGet() = runTest {
        val config = Config(BaseUrl("https://www.google.de"), defaultApiKey)
        val client = ApiClient(config)
        GlobalScope.launch {
            assertEquals(ConnectionCheckResult.Success, client.checkConnection())
        }
    }
}

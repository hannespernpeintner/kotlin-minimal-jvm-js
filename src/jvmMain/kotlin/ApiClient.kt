import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.OK

actual data class ApiClient actual constructor(val config: Config) {
    val client = HttpClient(Apache) {
        expectSuccess = false
    }

    actual suspend fun checkConnection(): ConnectionCheckResult {
        val statusCode = client.get<HttpStatusCode>(config.baseUrl.value) {
            header("Authorization", config.apiKey)
        }
        return if(statusCode == OK) ConnectionCheckResult.Success else ConnectionCheckResult.Failure(statusCode)
    }

}
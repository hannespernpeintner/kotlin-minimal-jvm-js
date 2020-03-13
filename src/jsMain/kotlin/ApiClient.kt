import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

actual class ApiClient actual constructor(val config: Config) {
    val client = HttpClient()

    actual suspend fun checkConnection(): ConnectionCheckResult {
        val statusCode = client.get<HttpStatusCode>(config.baseUrl.value)
        return if(statusCode == HttpStatusCode.OK) ConnectionCheckResult.Success else ConnectionCheckResult.Failure(statusCode)
    }
}
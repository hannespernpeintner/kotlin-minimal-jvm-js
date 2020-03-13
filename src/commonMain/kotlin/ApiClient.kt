import io.ktor.http.HttpStatusCode

data class Config(
    val baseUrl: BaseUrl,
    val apiKey: String
)

data class BaseUrl(val value: String) {
    init {
        require(!value.endsWith("/")) { "Invalid string for baseUrl: $value" }
    }
}

expect class ApiClient(config: Config) {
    suspend fun checkConnection(): ConnectionCheckResult
}

sealed class ConnectionCheckResult {
    object Success: ConnectionCheckResult()
    data class Failure(val httpStatus: HttpStatusCode): ConnectionCheckResult()
}

val defaultApiKey = "hurzelpurz"
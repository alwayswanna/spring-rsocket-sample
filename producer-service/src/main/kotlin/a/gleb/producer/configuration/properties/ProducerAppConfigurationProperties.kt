package a.gleb.producer.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.cors.CorsConfiguration

@ConfigurationProperties("producer-app")
data class ProducerAppConfigurationProperties(
    val consumerServerUrl: String,
    val consumerServerPort: Int,
    val cors: AppCorsConfiguration
)

class AppCorsConfiguration: CorsConfiguration()
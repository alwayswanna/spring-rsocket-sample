package a.gleb.producer.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("producer-app")
data class ProducerAppConfigurationProperties(
    val consumerServerUrl: String,
    val consumerServerPort: Int
)

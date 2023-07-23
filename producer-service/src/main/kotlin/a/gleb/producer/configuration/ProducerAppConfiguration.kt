package a.gleb.producer.configuration

import a.gleb.producer.configuration.properties.ProducerAppConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeTypeUtils
import reactor.util.retry.Retry
import java.time.Duration

@Configuration
@EnableConfigurationProperties(ProducerAppConfigurationProperties::class)
class ProducerAppConfiguration {

    @Bean
    fun requester(rSocketStrategies: RSocketStrategies, properties: ProducerAppConfigurationProperties): RSocketRequester {
        return RSocketRequester.builder()
            .rsocketConnector {
                it.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
            }
            .rsocketStrategies(rSocketStrategies)
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .tcp(properties.consumerServerUrl, properties.consumerServerPort)
    }

    @Bean
    fun rSocketStrategies(): RSocketStrategies {
        return RSocketStrategies.builder()
            .decoder(Jackson2JsonDecoder())
            .encoder(Jackson2JsonEncoder())
            .build()
    }
}
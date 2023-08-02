package a.gleb.producer.configuration

import a.gleb.producer.configuration.properties.ProducerAppConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.util.MimeTypeUtils
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import reactor.util.retry.Retry
import java.time.Duration

@Configuration
@EnableConfigurationProperties(ProducerAppConfigurationProperties::class)
class ProducerAppConfiguration(
    private val properties: ProducerAppConfigurationProperties
) {

    @Bean
    fun requester(
        rSocketStrategies: RSocketStrategies,
        properties: ProducerAppConfigurationProperties
    ): RSocketRequester {
        return RSocketRequester.builder()
            .rsocketConnector {
                it.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
            }
            .rsocketStrategies(rSocketStrategies)
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .tcp(properties.consumerServerUrl, properties.consumerServerPort)
    }

    @Bean
    fun securityConfiguration(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity.anonymous { it.disable() }
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfiguration(properties)) }
            .build()
    }

    @Bean
    fun rSocketStrategies(): RSocketStrategies {
        return RSocketStrategies.builder()
            .decoder(Jackson2JsonDecoder())
            .encoder(Jackson2JsonEncoder())
            .build()
    }

    @Bean
    fun corsConfiguration(properties: ProducerAppConfigurationProperties): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", properties.cors)
        return source
    }
}
package org.levons.bbs.common.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfigure {
    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean<*> {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.setAllowedOriginPatterns(listOf("*"))
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        corsConfiguration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        val corsFilter = CorsFilter(source)

        val filterRegistrationBean = FilterRegistrationBean(corsFilter)

        filterRegistrationBean.order = Ordered.HIGHEST_PRECEDENCE

        return filterRegistrationBean
    }
}


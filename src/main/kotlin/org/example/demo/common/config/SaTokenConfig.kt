package org.example.demo.common.config

import cn.dev33.satoken.config.SaTokenConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class SaTokenConfigure {
    @Autowired
    fun configSaToken(config: SaTokenConfig) {
        config.setTokenName("saToken")
        config.setTimeout(30 * 24 * 60 * 60)
        config.setActiveTimeout(-1)
        config.setIsConcurrent(true)
        config.setIsShare(true)
        config.setTokenStyle("uuid")
        config.setIsLog(false)
    }
}
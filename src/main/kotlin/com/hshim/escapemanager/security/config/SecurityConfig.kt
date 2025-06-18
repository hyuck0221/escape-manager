package com.hshim.escapemanager.security.config

import com.hshim.escapemanager.annotation.role.Public
import com.hshim.escapemanager.security.component.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    @Value("\${spring.security.enabled:true}") private val securityEnabled: Boolean,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val requestMappingHandlerMapping: RequestMappingHandlerMapping,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager =
        configuration.authenticationManager

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val publicUrls = getPublicUrls()
        if (!securityEnabled) {
            http.authorizeHttpRequests { it.anyRequest().permitAll() }
                .csrf { it.disable() }
            return http.build()
        }

        http
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        "/",
                        "/error",
                    ).permitAll()
                    .requestMatchers(*publicUrls.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .csrf { it.disable() }
        return http.build()
    }

    private fun getPublicUrls(): List<String> {
        val publicUrls = mutableListOf<String>()
        requestMappingHandlerMapping.handlerMethods.forEach { (mapping, handlerMethod) ->
            if (handlerMethod.beanType.isAnnotationPresent(Public::class.java) ||
                handlerMethod.method.isAnnotationPresent(Public::class.java)
            ) publicUrls.addAll(mapping.patternValues)
        }
        return publicUrls
    }
}
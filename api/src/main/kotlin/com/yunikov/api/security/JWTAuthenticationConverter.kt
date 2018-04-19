package com.yunikov.api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class JWTAuthenticationConverter(private val jwtClient: JWTClient): java.util.function.Function<ServerWebExchange, Mono<Authentication>> {

    override fun apply(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
                .flatMap { webExchange -> extractToken(webExchange) }
                .map { token -> Pair(token, jwtClient.verify(token)) }
                .map { pair -> UsernamePasswordAuthenticationToken(pair.second.body.subject, pair.first, authorities(pair)) as Authentication }
                .doOnError { error -> throw BadCredentialsException("Invalid JWT", error) }
    }

    private fun authorities(pair: Pair<String, Jws<Claims>>): MutableList<GrantedAuthority>? {
        val list = pair.second.body.get("roles", ArrayList::class.java)
                .map { role -> role as String }
                .toTypedArray()

        return AuthorityUtils.createAuthorityList(*list)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun extractToken(webExchange: ServerWebExchange): Mono<String> {
        val bearer = webExchange.request
                .headers
                .getFirst(HttpHeaders.AUTHORIZATION)

        if (!bearer.isNullOrEmpty() && bearer.length > "Bearer ".length) {
            val token = bearer.substring("Bearer ".length, bearer.length)

            if (!token.isEmpty()) {
                return Mono.just(token)
            }
        }

        val queryToken = webExchange.request.queryParams.getFirst("token")
        if (!queryToken.isNullOrEmpty()) {
            return Mono.just(queryToken)
        }

        return Mono.empty()
    }
}
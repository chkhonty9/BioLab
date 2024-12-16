package org.cn.gatewayservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;


@ConfigurationProperties(prefix = "rsa")

public record RsaConfig(RSAPublicKey publicKey ) {
}

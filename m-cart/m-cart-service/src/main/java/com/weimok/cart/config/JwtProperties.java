package com.weimok.cart.config;

import com.weimok.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author itsNine
 * @create 2019-07-10-22:38
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "mall.jwt")
public class JwtProperties {

    private String pubKeyPath;

    private PublicKey publicKey;

    private String cookieName;




    //对象实例化后，读取公钥和私钥
    @PostConstruct
    public void init() throws Exception{
        try {
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥失败！", e);
            throw new RuntimeException();
        }
    }
}

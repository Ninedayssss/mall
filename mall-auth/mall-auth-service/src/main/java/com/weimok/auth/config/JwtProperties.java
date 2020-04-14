package com.weimok.auth.config;

import com.weimok.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author itsNine
 * @create 2020-04-10-11:02
 */
@Component
@Data
@ConfigurationProperties(prefix = "mall.jwt")
public class JwtProperties {
    private String secret;
    private String pubKeyPath;
    private String priKeyPath;
    private Integer expire;
    private String cookieName;
    private Integer cookieMaxAge;


    private PublicKey publicKey;
    private PrivateKey privateKey;

    //对象实例化后，读取公钥和私钥
    @PostConstruct
    public void init() throws Exception{
        //公钥私钥不存在的话 先生成
        File pubPath = new File(pubKeyPath);
        File priPath = new File(priKeyPath);
        if (!pubPath.exists() || !priPath.exists()){
            //生成公钥和私钥
            RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);
        }
        //读取公钥和私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }
}

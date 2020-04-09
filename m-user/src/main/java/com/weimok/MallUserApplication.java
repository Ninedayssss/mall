package  com.weimok;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author itsNine
 * @create 2020-03-17-14:19
 */
@SpringBootApplication
@MapperScan("com.weimok.user.mapper")
@EnableDubbo
public class MallUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class);
    }
}

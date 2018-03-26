package com.peng;

import com.didispace.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableSwagger2Doc
@SpringBootApplication
@EnableEurekaClient
@MapperScan("mapper")
public class LogindemoApplication {

	public static void main(String[] args) {
		//热部署开关
		//System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(LogindemoApplication.class, args);
	}

}

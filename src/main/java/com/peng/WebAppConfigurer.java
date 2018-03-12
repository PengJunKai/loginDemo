package com.peng;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.peng.config.BootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 拦截器注册类
 * Created by Rukiy on 2017-12-04
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private BootConfig bootConfig;

	/**
	 * 设置欢迎页
	 *
	 * @param registry
	 */
	@Override
	public void addViewControllers (ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/" + bootConfig.getHomePage());
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	@Override
	public void configureMessageConverters (List<HttpMessageConverter<?>> converters) {
			super.configureMessageConverters(converters);
	/*
	1.需要先定义一个convert转换消息的对象；
	2.添加fastjson的配置信息，比如是否要格式化返回的json数据
	3.在convert中添加配置信息
	4.将convert添加到converters中
	 */
			//1.定义一个convert转换消息对象
			FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
			//2.添加fastjson的配置信息，比如：是否要格式化返回json数据
			FastJsonConfig fastJsonConfig = new FastJsonConfig();
			fastJsonConfig.setSerializerFeatures(//SerializerFeature.WriteMapNullValue, // 输出空置字段
							//SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
							//SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
							//SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
							//SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
							SerializerFeature.WriteDateUseDateFormat,
							SerializerFeature.DisableCircularReferenceDetect);
			SerializeConfig config = new SerializeConfig();
//				config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
//				config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
			fastJsonConfig.setSerializeConfig(config);
			fastConverter.setFastJsonConfig(fastJsonConfig);
			converters.add(fastConverter);
	}
}

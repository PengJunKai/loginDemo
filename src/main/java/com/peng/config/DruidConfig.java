package com.peng.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;

/**
 * <p>数据库数据源配置</p>
 * <p>说明:这个类中包含了许多默认配置,若这些配置符合您的情况,您可以不用管,若不符合,建议不要修改本类,建议直接在"application.yml"中配置即可</p>
 * Created by Rukiy on 2017-11-21
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidConfig {

		private String url;

		private String username;

		private String password;

		private String driverClassName = "com.mysql.jdbc.Driver";

		private Integer initialSize = 2;

		private Integer minIdle = 1;

		private Integer maxActive = 20;

		private Integer maxWait = 60000;

		private Integer timeBetweenEvictionRunsMillis = 60000;

		private Integer minEvictableIdleTimeMillis = 300000;

		private String validationQuery = "SELECT 'x'";

		private Boolean testWhileIdle = true;

		private Boolean testOnBorrow = false;

		private Boolean testOnReturn = false;

		private Boolean poolPreparedStatements = true;

		private Integer maxPoolPreparedStatementPerConnectionSize = 20;

		private String filters = "stat";

		private String connectionProperties;


		@Bean(initMethod = "init", destroyMethod = "close")   //声明其为Bean实例
		@Primary  //在同样的DataSource中，首先使用被标注的DataSource
		public DruidDataSource dataSource () {
				DruidDataSource dataSource = new DruidDataSource();
				dataSource.setUrl(url);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
				dataSource.setDriverClassName(driverClassName);
				dataSource.setInitialSize(initialSize);     //定义初始连接数
				dataSource.setMinIdle(minIdle);             //最小空闲
				dataSource.setMaxActive(maxActive);         //定义最大连接数
				dataSource.setMaxWait(maxWait);             //最长等待时间
				// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
				dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
				// 配置一个连接在池中最小生存的时间，单位是毫秒
				dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
				dataSource.setValidationQuery(validationQuery);
				dataSource.setTestWhileIdle(testWhileIdle);
				dataSource.setTestOnBorrow(testOnBorrow);
				dataSource.setTestOnReturn(testOnReturn);
				// 打开PSCache，并且指定每个连接上PSCache的大小
				dataSource.setPoolPreparedStatements(poolPreparedStatements);
				dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
				dataSource.setConnectionProperties(connectionProperties);
				try {
						dataSource.setFilters(filters);
				} catch (SQLException e) {
						e.printStackTrace();
				}
				return dataSource;
		}


		/**
		 * 注册一个StatViewServlet
		 *
		 * @return
		 */
		@Bean
		public ServletRegistrationBean DruidStatViewServle () {
				ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
				//添加初始化参数：initParams
				/* 白名单，如果不配置或value为空，则允许所有 */
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,192.0.0.1");
				/* 黑名单，与白名单存在相同IP时，优先于白名单 */
//        servletRegistrationBean.addInitParameter("deny","192.0.0.1");
				/* 用户名 */
//        servletRegistrationBean.addInitParameter("loginUsername", "admin");
				/* 密码 */
//        servletRegistrationBean.addInitParameter("loginPassword", "dbadmin");
				/* 禁用页面上的“Reset All”功能 */
				servletRegistrationBean.addInitParameter("resetEnable", "false");
				return servletRegistrationBean;
		}
		/**
		 * 监听Spring
		 * 1.定义拦截器
		 * 2.定义切入点
		 * 3.定义通知类
		 *
		 * @return
		 */
		@Bean
		public DruidStatInterceptor druidStatInterceptor () {
				return new DruidStatInterceptor();
		}

		@Bean
		public JdkRegexpMethodPointcut druidStatPointcut () {
				JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
				String[] patterns = new String[]{
								"com.sobey.*.*.controller.*"
				};
				druidStatPointcut.setPatterns(patterns);
				return druidStatPointcut;
		}

		@Bean
		public Advisor druidStatAdvisor () {
				return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
		}

		public String getUrl () {
				return url;
		}

		public void setUrl (String url) {
				this.url = url;
		}

		public String getUsername () {
				return username;
		}

		public void setUsername (String username) {
				this.username = username;
		}

		public String getPassword () {
				return password;
		}

		public void setPassword (String password) {
				this.password = password;
		}

		public String getDriverClassName () {
				return driverClassName;
		}

		public void setDriverClassName (String driverClassName) {
				this.driverClassName = driverClassName;
		}

		public Integer getInitialSize () {
				return initialSize;
		}

		public void setInitialSize (Integer initialSize) {
				this.initialSize = initialSize;
		}

		public Integer getMinIdle () {
				return minIdle;
		}

		public void setMinIdle (Integer minIdle) {
				this.minIdle = minIdle;
		}

		public Integer getMaxActive () {
				return maxActive;
		}

		public void setMaxActive (Integer maxActive) {
				this.maxActive = maxActive;
		}

		public Integer getMaxWait () {
				return maxWait;
		}

		public void setMaxWait (Integer maxWait) {
				this.maxWait = maxWait;
		}

		public Integer getTimeBetweenEvictionRunsMillis () {
				return timeBetweenEvictionRunsMillis;
		}

		public void setTimeBetweenEvictionRunsMillis (Integer timeBetweenEvictionRunsMillis) {
				this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}

		public Integer getMinEvictableIdleTimeMillis () {
				return minEvictableIdleTimeMillis;
		}

		public void setMinEvictableIdleTimeMillis (Integer minEvictableIdleTimeMillis) {
				this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}

		public String getValidationQuery () {
				return validationQuery;
		}

		public void setValidationQuery (String validationQuery) {
				this.validationQuery = validationQuery;
		}

		public Boolean getTestWhileIdle () {
				return testWhileIdle;
		}

		public void setTestWhileIdle (Boolean testWhileIdle) {
				this.testWhileIdle = testWhileIdle;
		}

		public Boolean getTestOnBorrow () {
				return testOnBorrow;
		}

		public void setTestOnBorrow (Boolean testOnBorrow) {
				this.testOnBorrow = testOnBorrow;
		}

		public Boolean getTestOnReturn () {
				return testOnReturn;
		}

		public void setTestOnReturn (Boolean testOnReturn) {
				this.testOnReturn = testOnReturn;
		}

		public Boolean getPoolPreparedStatements () {
				return poolPreparedStatements;
		}

		public void setPoolPreparedStatements (Boolean poolPreparedStatements) {
				this.poolPreparedStatements = poolPreparedStatements;
		}

		public Integer getMaxPoolPreparedStatementPerConnectionSize () {
				return maxPoolPreparedStatementPerConnectionSize;
		}

		public void setMaxPoolPreparedStatementPerConnectionSize (Integer maxPoolPreparedStatementPerConnectionSize) {
				this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
		}

		public String getFilters () {
				return filters;
		}

		public void setFilters (String filters) {
				this.filters = filters;
		}

		public String getConnectionProperties () {
				return connectionProperties;
		}

		public void setConnectionProperties (String connectionProperties) {
				this.connectionProperties = connectionProperties;
		}

}

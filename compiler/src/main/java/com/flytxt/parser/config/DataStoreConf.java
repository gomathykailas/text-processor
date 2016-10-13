package com.flytxt.parser.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
public class DataStoreConf {
	@Primary
	@Bean(name="datasource")
	@ConfigurationProperties(prefix="test.datasource")
	@Profile("test")
	public DataSource forTest(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name="datasource")
	@ConfigurationProperties(prefix="dev.datasource")
	@Profile("dev")
	public DataSource forDev(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name="datasource")
	@ConfigurationProperties(prefix="prod.datasource")
	@Profile("prod")
	public DataSource forProd(){
		return DataSourceBuilder.create().build();
	}

}
package com.softserveinc.todosoap.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.softserveinc.todosoap.dao")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Autowired
	private Environment env;

	@Override
	protected String getKeyspaceName() {
		return env.getProperty("spring.data.cassandra.keyspace-name");
	}
}

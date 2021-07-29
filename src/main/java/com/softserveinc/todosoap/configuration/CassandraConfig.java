package com.softserveinc.todosoap.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableCassandraRepositories(basePackages = "com.softserveinc.todosoap.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	private String keyspaceName;

	@Override
	@NonNull
	protected String getKeyspaceName() {
		return keyspaceName;
	}
}

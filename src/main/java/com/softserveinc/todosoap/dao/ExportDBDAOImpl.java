package com.softserveinc.todosoap.dao;

import com.softserveinc.todosoap.models.ExportDBClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ExportDBDAOImpl implements ExportDBDAO{

	private final CassandraOperations cassandraTemplate;

	private final CqlTemplate cqlTemplate;

	@Autowired
	public ExportDBDAOImpl(CassandraOperations cassandraTemplate, CqlTemplate cqlTemplate) {
		this.cassandraTemplate = cassandraTemplate;
		this.cqlTemplate = cqlTemplate;
	}

	@Override
	public void create(ExportDBClaim claim) {
		cassandraTemplate.insert(claim);
	}

	@Override
	public String checkStatusExportDB(String userEmail, UUID id) {

		String query = "SELECT status FROM todosoap.exportdbclaim WHERE useremail = ? AND id = ? ALLOW FILTERING;";
		return cqlTemplate.queryForObject(query, String.class, userEmail, id);
	}

	@Override
	public ExportDBClaim findById(UUID id) {

		return cassandraTemplate.selectOneById(id, ExportDBClaim.class);
	}
}

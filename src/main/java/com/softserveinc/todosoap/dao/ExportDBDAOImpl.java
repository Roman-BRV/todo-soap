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
	public String checkStatus(UUID id) {

		String query = "SELECT status FROM todosoap.exportdbclaim WHERE id = ? ALLOW FILTERING;";
		return cqlTemplate.queryForObject(query, String.class, id);
	}

	@Override
	public ExportDBClaim findById(UUID id) {

		return cassandraTemplate.selectOneById(id, ExportDBClaim.class);
	}

	@Override
	public boolean changeStatus(String claimId, String status) {

		String query = "UPDATE todosoap.exportdbclaim SET status = ? WHERE id = ?;";
		return cqlTemplate.execute(query, status, UUID.fromString(claimId));
	}

	@Override
	public boolean completeClaim(String claimId, String status, String filepath) {

		String query = "UPDATE todosoap.exportdbclaim SET status = ?, resultpath = ? WHERE id = ?;";
		return cqlTemplate.execute(query, status, filepath, UUID.fromString(claimId));
	}
}

package com.softserveinc.todosoap.repository;

import com.softserveinc.todosoap.models.ExportTodosClaim;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface ExportTodosRepository extends CassandraRepository<ExportTodosClaim, UUID> {

}

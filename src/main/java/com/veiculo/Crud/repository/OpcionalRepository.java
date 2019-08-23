package com.veiculo.Crud.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.veiculo.Crud.model.Opcional;

public interface OpcionalRepository extends CrudRepository<Opcional, String> {
	Opcional findById(long id);
	List<Opcional> findAll();
}

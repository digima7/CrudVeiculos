package com.veiculo.Crud.repository;

import org.springframework.data.repository.CrudRepository;
import com.veiculo.Crud.model.Veiculo;

public interface VeiculoRepository extends CrudRepository<Veiculo, String> {
	Veiculo findById(long id);
	
}

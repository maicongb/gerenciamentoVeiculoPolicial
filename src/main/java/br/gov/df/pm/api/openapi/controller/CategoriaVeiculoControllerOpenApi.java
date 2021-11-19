package br.gov.df.pm.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.df.pm.api.exceptionhandler.Problem;
import br.gov.df.pm.api.model.CategoriaVeiculoModel;
import br.gov.df.pm.api.model.input.AtivarDesativarCategoriaVeiculoInput;
import br.gov.df.pm.api.model.input.CategoriaVeiculoInput;
import br.gov.df.pm.domain.repository.filter.CategoriaVeiculoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "CategoriaVeiculo")
public interface CategoriaVeiculoControllerOpenApi {
	
	@ApiOperation("Filtra categoria do veículo por NOME e/ou ATIVO")
	Page<CategoriaVeiculoModel> filtrar(CategoriaVeiculoFilter filtro, 
								Pageable pageable);
	
	@ApiOperation("Busca categoria do veículo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da categoria do veículo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Categoria do veículo não encontrada", response = Problem.class)
	})
	CategoriaVeiculoModel buscar(
			@ApiParam(value = "ID de uma categoria de veículo", example = "1")
			Long categoriaVeiculoId);
	
	@ApiOperation("Adiciona categoria do veículo")
	CategoriaVeiculoModel adicionar(@ApiParam(name = "corpo", 
			value = "Representação de uma nova categoria de veículo")
			CategoriaVeiculoInput categoriaVeiculoInput);
	
	@ApiOperation("Atualiza categoria do veículo por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Categoria do veículo atualizada"),
		@ApiResponse(code = 404, message = "Categoria do veículo não encontrada", response = Problem.class)
	})
	CategoriaVeiculoModel atualizar(
			
			@ApiParam(value = "ID de uma categoria de veículo", example = "1")
			Long categoriaVeiculoId,
	
			@ApiParam(name = "corpo", value = "Representação de uma categoria de veículo com os novos dados")
			CategoriaVeiculoInput categoriaVeiculoInput);
	
	@ApiOperation("Remove categoria do veículo por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Categoria do veículo excluída"),
		@ApiResponse(code = 404, message = "Categoria do veículo não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de uma categoria de veículo", example = "1")
			Long categoriaVeiculoId);
	
	@ApiOperation("Atualiza status ATIVO da categoria do veículo para ativo ou inativo")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Status da categoria do veículo atualizado(s)"),
		@ApiResponse(code = 404, message = "Categoria do veículo não encontrada", response = Problem.class)
	})
	void atualizarStatus(
			
			@ApiParam(name = "corpo", 
			value = "Representação de status da categoria de veículo")
			
			AtivarDesativarCategoriaVeiculoInput 
			ativarDesativarCategoriaVeiculo);
	
}



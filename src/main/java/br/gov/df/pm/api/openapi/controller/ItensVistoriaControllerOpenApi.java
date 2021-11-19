package br.gov.df.pm.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.df.pm.api.exceptionhandler.Problem;
import br.gov.df.pm.api.model.ItensVistoriaModel;
import br.gov.df.pm.api.model.input.AtivarDesativarItensVistoriaInput;
import br.gov.df.pm.api.model.input.ItensVistoriaInput;
import br.gov.df.pm.domain.repository.filter.ItensVistoriaFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "ItensVistoria")
public interface ItensVistoriaControllerOpenApi {
	
	
	@ApiOperation("Filtra itens da vistoria por NOME, STATUS(ativo ou inativo), Tipo de Vistoria")
	Page<ItensVistoriaModel> filtrar(@ApiParam(name = "corpo", 
			value = "Representação dos dados a serem filtrados")
			ItensVistoriaFilter filtro, Pageable pageable);

	@ApiOperation("Busca item da vistoria por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID dos item da vistoria inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Item da vistoria não encontrada", response = Problem.class)
	})
	ItensVistoriaModel buscar(
			@ApiParam(value = "ID de uma categoria de veículo", example = "1")
			Long itensVistoriaId);

	@ApiOperation("Adiciona categoria do veículo")
	ItensVistoriaModel adicionar(@ApiParam(name = "corpo", 
			value = "Representação de uma novo item de vistoria")
			ItensVistoriaInput itensVistoriaInput);

	@ApiOperation("Atualiza item da vistoriao por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Item da vistoria atualizado"),
		@ApiResponse(code = 404, message = "Item da vistoria não encontrado", response = Problem.class)
	})
	ItensVistoriaModel atualizar(
			
			@ApiParam(name = "corpo", value = "Representação de um item de vistoria com os novos dados")
			ItensVistoriaInput itensVistoriaInput, 
			
			@ApiParam(value = "ID de um item de vistoria", example = "1")
			Long itensVistoriaId);

	
	@ApiOperation("Remove item da vistoria por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Item da vistoria excluído"),
		@ApiResponse(code = 404, message = "Item da vistora não encontrado", response = Problem.class)
	})
	void remover(Long itensVistoriaId);

	@ApiOperation("Atualiza status ATIVO do item da vistoria para ativo ou inativo")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Status do item da vistoria atualizado(s)"),
		@ApiResponse(code = 404, message = "Item da vistoria não encontrado", response = Problem.class)
	})
	void atualizarStatus(AtivarDesativarItensVistoriaInput ativarDesativarItensVistoriaInput);

}
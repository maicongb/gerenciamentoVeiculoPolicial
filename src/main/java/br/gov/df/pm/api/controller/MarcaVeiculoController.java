package br.gov.df.pm.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.df.pm.api.ResourceUriHelper;
import br.gov.df.pm.api.assembler.MarcaVeiculoInputDisassembler;
import br.gov.df.pm.api.assembler.MarcaVeiculoModelAssembler;
import br.gov.df.pm.api.model.MarcaVeiculoModel;
import br.gov.df.pm.api.model.input.AtivarDesativarMarcaVeiculoInput;
import br.gov.df.pm.api.model.input.MarcaVeiculoInput;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.TipoVeiculoNaoEncontradoException;
import br.gov.df.pm.domain.model.MarcaVeiculo;
import br.gov.df.pm.domain.repository.MarcaVeiculoRepository;
import br.gov.df.pm.domain.repository.filter.MarcaVeiculoFilter;
import br.gov.df.pm.domain.service.CadastroMarcaVeiculoService;
import br.gov.df.pm.infrastructure.repository.spec.MarcaVeiculoSpecs;

@RestController
@RequestMapping(path = "/marca-veiculo", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarcaVeiculoController {
	
	@Autowired
	private CadastroMarcaVeiculoService cadastroMarcaVeiculo;
	
	@Autowired
	private MarcaVeiculoModelAssembler marcaVeiculoModelAssembler;
	
	@Autowired
	private MarcaVeiculoInputDisassembler marcaVeiculoInputDisassembler;
	
	@Autowired
	private MarcaVeiculoRepository marcaVeiculoRepository;
	
	@GetMapping
	public Page<MarcaVeiculoModel> listar(MarcaVeiculoFilter filtro,
			@PageableDefault(size=10) Pageable pageable) {
		
		Page<MarcaVeiculo> marcaVeiculoPage = 
				marcaVeiculoRepository.findAll(MarcaVeiculoSpecs.usandoFiltro(filtro), pageable);
		
		List<MarcaVeiculoModel> marcaVeiculoModel = marcaVeiculoModelAssembler
				.toCollectionModel(marcaVeiculoPage.getContent());
		
		Page<MarcaVeiculoModel> marcaVeiculoModelPage = new PageImpl<>(marcaVeiculoModel, 
				pageable, marcaVeiculoPage.getTotalElements());
		
		return marcaVeiculoModelPage;
	}
	
	@GetMapping("/{marcaVeiculoId}")
	public MarcaVeiculoModel buscar(@PathVariable Long marcaVeiculoId) {
		MarcaVeiculo marcaVeiculo = cadastroMarcaVeiculo.buscarOuFalhar(marcaVeiculoId);
		
		return marcaVeiculoModelAssembler.toModel(marcaVeiculo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MarcaVeiculoModel adicionar(@RequestBody @Valid MarcaVeiculoInput marcaVeiculoInput) {
		
		MarcaVeiculo marcaVeiculo = marcaVeiculoInputDisassembler
				.toDomainObject(marcaVeiculoInput);
		
		marcaVeiculo = cadastroMarcaVeiculo.salvar(marcaVeiculo);
		
		MarcaVeiculoModel marcaVeiculoModel = marcaVeiculoModelAssembler.toModel(marcaVeiculo);
		
		//ADICIONAR A URI http://localhost:8080/marca-veiculo/1 NO HEADER
		ResourceUriHelper.addUriInResponseHeader(marcaVeiculoModel.getId());
		
		return marcaVeiculoModel;
		
	}
	
	@PutMapping("/{marcaVeiculoId}")
	public MarcaVeiculoModel atualizar(@RequestBody @Valid MarcaVeiculoInput marcaVeiculoInput,
				@PathVariable Long marcaVeiculoId) {
		
		try {
			
			MarcaVeiculo marcaVeiculoAtual = cadastroMarcaVeiculo
					.buscarOuFalhar(marcaVeiculoId);
			
			marcaVeiculoInputDisassembler.copyToDomainObject(marcaVeiculoInput, marcaVeiculoAtual);
			
			marcaVeiculoAtual = cadastroMarcaVeiculo.salvar(marcaVeiculoAtual);
			
			return marcaVeiculoModelAssembler.toModel(marcaVeiculoAtual);
			
		} catch (TipoVeiculoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{marcaVeiculoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long marcaVeiculoId) {
		cadastroMarcaVeiculo.excluir(marcaVeiculoId);
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarDesativarMarcaVeiculoInput 
			ativarDesativarMarcaVeiculoInput) {
			cadastroMarcaVeiculo.alterarStatus(
					ativarDesativarMarcaVeiculoInput.getMarcaVeiculoIds(), 
					ativarDesativarMarcaVeiculoInput.getStatus());
	}

}

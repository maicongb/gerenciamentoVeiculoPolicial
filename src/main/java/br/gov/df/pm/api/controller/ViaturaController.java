package br.gov.df.pm.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.gov.df.pm.api.assembler.ViaturaInputDisassembler;
import br.gov.df.pm.api.assembler.ViaturaModelAssembler;
import br.gov.df.pm.api.assembler.ViaturaResumoInputDisassembler;
import br.gov.df.pm.api.model.ViaturaModel;
import br.gov.df.pm.api.model.input.ViaturaInput;
import br.gov.df.pm.api.model.input.ViaturaResumoInput;
import br.gov.df.pm.domain.exception.ModeloVeiculoNaoEncontradoException;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.StatusViaturaNaoEncontradaException;
import br.gov.df.pm.domain.exception.TipoCombustivelNaoEncontradoException;
import br.gov.df.pm.domain.exception.TipoViaturaNaoEncontradoException;
import br.gov.df.pm.domain.exception.UnidadeFederativaNaoEncontradaException;
import br.gov.df.pm.domain.exception.UnidadePolicialMilitarNaoEncontradaException;
import br.gov.df.pm.domain.exception.ViaturaNaoEncontradaException;
import br.gov.df.pm.domain.model.Viatura;
import br.gov.df.pm.domain.model.ViaturaUpm;
import br.gov.df.pm.domain.repository.ViaturaUpmRepository;
import br.gov.df.pm.domain.service.CadastroViaturaService;

@RestController
@RequestMapping(path = "/viatura", produces = MediaType.APPLICATION_JSON_VALUE)
public class ViaturaController {
	
	@Autowired
	private CadastroViaturaService cadastroViatura;
	
	@Autowired
	private ViaturaModelAssembler viaturaModelAssembler;
	
	@Autowired
	private ViaturaInputDisassembler viaturaInputDisassembler;
	
	@Autowired
	private ViaturaResumoInputDisassembler viaturaResumoInputDisassembler;
	
	@Autowired
	private ViaturaUpmRepository viaturaUpmRepository;
	
	@GetMapping("/{viaturaId}")
	public ViaturaModel buscar(@PathVariable String viaturaId) {
		Viatura viatura = cadastroViatura.buscarOuFalhar(viaturaId);
		return viaturaModelAssembler.toModel(viatura);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ViaturaModel adicionar(@RequestBody @Valid ViaturaInput viaturaInput) {
		
		try {
			Viatura viatura = viaturaInputDisassembler
					.toDomainObject(viaturaInput);
			
			viatura = cadastroViatura.salvar(viatura);
			
			ViaturaModel viaturaModel = viaturaModelAssembler.toModel(viatura);
			
			//ADICIONAR A URI http://localhost:8080/viatura/1 NO HEADER
			ResourceUriHelper.addUriInResponseHeader(viaturaModel.getId());
			
			return viaturaModel;
		} catch (UnidadeFederativaNaoEncontradaException | 
				 ModeloVeiculoNaoEncontradoException | 
				 TipoCombustivelNaoEncontradoException |				 
				 TipoViaturaNaoEncontradoException | 
				 StatusViaturaNaoEncontradaException |
				 UnidadePolicialMilitarNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{viaturaId}")
	public ViaturaModel atualizar(@RequestBody @Valid ViaturaResumoInput viaturaResumoInput,
				@PathVariable String viaturaId) {
		
		try {
			
			Viatura viaturaAtual = cadastroViatura.buscarOuFalhar(viaturaId);
			
			viaturaResumoInputDisassembler.copyToDomainObject(viaturaResumoInput, viaturaAtual);
			
			
			ViaturaUpm viaturaUpm = viaturaUpmRepository.buscarUltimaMovimentacao(viaturaAtual.getCodigo());

			viaturaAtual.getViaturasUpm().add(viaturaUpm);

			
			return viaturaModelAssembler.toModel(cadastroViatura.salvar(viaturaAtual));
			
		} catch (UnidadeFederativaNaoEncontradaException | 
				 ModeloVeiculoNaoEncontradoException | 
				 TipoCombustivelNaoEncontradoException |				 
				 TipoViaturaNaoEncontradoException | 
				 StatusViaturaNaoEncontradaException |
				 UnidadePolicialMilitarNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{viaturaId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable String viaturaId) {
		cadastroViatura.ativar(viaturaId);
	}
	
	@DeleteMapping("/{viaturaId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable String viaturaId) {
		cadastroViatura.inativar(viaturaId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<String> viaturaIds) {
		try {
			cadastroViatura.ativar(viaturaIds);
		} catch (ViaturaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<String> viaturaIds) {
		try {
			cadastroViatura.inativar(viaturaIds);
		} catch (ViaturaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	

	
	
	
	
	
	
}

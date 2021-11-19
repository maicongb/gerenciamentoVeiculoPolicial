package br.gov.df.pm.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.pm.domain.enumeration.StatusViaturaEnum;
import br.gov.df.pm.domain.exception.NegocioException;
import br.gov.df.pm.domain.exception.ViaturaNaoEncontradaException;
import br.gov.df.pm.domain.model.ModeloVeiculo;
import br.gov.df.pm.domain.model.StatusViatura;
import br.gov.df.pm.domain.model.TipoCombustivel;
import br.gov.df.pm.domain.model.TipoViatura;
import br.gov.df.pm.domain.model.UnidadeFederativa;
import br.gov.df.pm.domain.model.UnidadePolicialMilitar;
import br.gov.df.pm.domain.model.Viatura;
import br.gov.df.pm.domain.repository.StatusViaturaRepository;
import br.gov.df.pm.domain.repository.ViaturaRepository;
import br.gov.df.pm.domain.repository.ViaturaUpmRepository;

@Service
public class CadastroViaturaService {
	
	
	@Autowired
	private ViaturaRepository viaturaRepository;
	
	@Autowired
	private CadastroModeloVeiculoService cadastroModeloVeiculo;
	
	@Autowired
	private CadastroUnidadeFederativaService cadastroUnidadeFederativa;
	
	@Autowired
	private CadastroTipoCombustivelService cadastroTipoCombustivel;
	
	@Autowired
	private CadastroTipoViaturaService cadastroTipoViatura;
	
	@Autowired
	private StatusViaturaRepository statusViaturaRepository;
	
	@Autowired
	private CadastroStatusViaturaService cadastroStatusViatura;
	
	@Autowired
	private CadastroUnidadePolicialMilitarService cadastroUnidadePolicialMilitar;
	
	@Autowired
	private ViaturaUpmRepository viaturaUpmRepository;
	
	public Viatura buscarOuFalhar(String viaturaId) {
		return viaturaRepository.findById(viaturaId)
				.orElseThrow(() -> new ViaturaNaoEncontradaException(viaturaId));
	}
	
	@Transactional
	public Viatura salvar(Viatura viatura) {
		
		viaturaRepository.detach(viatura);
		
		validarCamposAtualizarViatura(viatura);
		
		validarItensViatura(viatura);
	
		viatura = viaturaRepository.save(viatura);
		viaturaRepository.flush();
		
		//SALVA VIATURAUPM
		validaViaturaUpm(viatura);
		
		return viatura;
	}

	
	@Transactional
	public void ativar(String viaturaId) {
		Viatura viaturaAtual = buscarOuFalhar(viaturaId);
		viaturaAtual.ativar();
		
	}
	
	@Transactional
	public void inativar(String viaturaId) {
		Viatura viaturaAtual = buscarOuFalhar(viaturaId);
		viaturaAtual.inativar();
	}
	
	@Transactional
	public void ativar(List<String> viaturaIds) {
		viaturaIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<String> viaturaIds) {
		viaturaIds.forEach(this::inativar);
	}
	

	/* 
	 * VALIDAR CAMPOS QUE NÃO PODERÃO SE REPETIR NA ATUALIZAÇÃO
	 * CAMPOS: PREFIXO, PLACA, CHASSI, NUMERO DO MOTOR
	 * RENAVAM E TOMBAMENTO
	 */
	private void validarCamposAtualizarViatura(Viatura viatura) {
		
		Optional<Viatura> prefixoExiste = viaturaRepository
				.findByPrefixoIgnoreCase(viatura.getPrefixo());
		
		if(prefixoExiste.isPresent() && !viatura.equals(prefixoExiste.get())) {
			throw new NegocioException(
				String.format("Já existe um prefixo cadastrado com essa informação %s", 
						viatura.getPrefixo()));
		}
		
		Optional<Viatura> placaExiste = viaturaRepository
				.findByPlacaIgnoreCase(viatura.getPlaca());
		
		if(placaExiste.isPresent() && !viatura.equals(placaExiste.get())) {
			throw new NegocioException(
				String.format("Já existe uma placa cadastrada com essa informação %s", 
						viatura.getPlaca()));
		}
		
		Optional<Viatura> chassiExiste = viaturaRepository
				.findByChassiIgnoreCase(viatura.getChassi());
		
		if(chassiExiste.isPresent() && !viatura.equals(chassiExiste.get())) {
			throw new NegocioException(
				String.format("Já existe um chassi cadastrado com essa informação %s", 
						viatura.getChassi()));
		}
		
		Optional<Viatura> numeroMotorExiste = viaturaRepository
				.findByNumeroMotorIgnoreCase(viatura.getNumeroMotor());
		
		if(numeroMotorExiste.isPresent() && !viatura.equals(numeroMotorExiste.get())) {
			throw new NegocioException(
				String.format("Já existe um número do motor cadastrado essa informação %s", 
						viatura.getNumeroMotor()));
		}
		
		Optional<Viatura> renavamExiste = viaturaRepository
				.findByRenavamIgnoreCase(viatura.getRenavam());
		
		if(renavamExiste.isPresent() && !viatura.equals(renavamExiste.get())) {
			throw new NegocioException(
				String.format("Já existe um renavam cadastrado com essa informação %s", 
						viatura.getRenavam()));
		}
		
		Optional<Viatura> tombamentoExiste = viaturaRepository
				.findByTombamentoIgnoreCase(viatura.getTombamento());
		
		if(tombamentoExiste.isPresent() && !viatura.equals(tombamentoExiste.get())) {
			throw new NegocioException(
				String.format("Já existe um tombamento cadastrado com essa informação %s", 
						viatura.getTombamento()));
		}
		
	}

	private void validarItensViatura(Viatura viatura) {
		
		Long modeloVeiculoId = viatura.getModeloVeiculo().getId();		
		ModeloVeiculo modeloVeiculo = cadastroModeloVeiculo.buscarOuFalhar(modeloVeiculoId);
		
		Long ufPlacaId = viatura.getUfPlaca().getId();
		UnidadeFederativa ufPlaca = cadastroUnidadeFederativa.buscarOuFalhar(ufPlacaId);
		
		Long tipoCombustivelId = viatura.getTipoCombustivel().getId();
		TipoCombustivel tipoCombustivel = cadastroTipoCombustivel.buscarOuFalhar(tipoCombustivelId);
		
		Long tipoViaturaId = viatura.getTipoViatura().getId();
		TipoViatura tipoViatura = cadastroTipoViatura.buscarOuFalhar(tipoViaturaId);
		
		validaStatusViatura(viatura);
		
		viatura.setModeloVeiculo(modeloVeiculo);
		viatura.setUfPlaca(ufPlaca);
		viatura.setTipoCombustivel(tipoCombustivel);
		viatura.setTipoViatura(tipoViatura);
	}

	private void validaStatusViatura(Viatura viatura) {
		StatusViatura statusViatura;
		if(viatura.isNova()) {
			statusViatura = statusViaturaRepository.getOne(
					StatusViaturaEnum.DISPONÍVEL.getId());
		} else {
			Long statusViaturaId = viatura.getStatusViatura().getId();
			statusViatura = cadastroStatusViatura.buscarOuFalhar(statusViaturaId);
		}
		viatura.setStatusViatura(statusViatura);
	}
	
	@Transactional
	private void validaViaturaUpm(Viatura viatura)  {
		viatura.getViaturasUpm().forEach(viaturaUpm -> {
			
				UnidadePolicialMilitar upm = cadastroUnidadePolicialMilitar
						.buscarOuFalhar(viaturaUpm.getUpm().getId());
				
				viaturaUpm.setUpm(upm);
				viaturaUpm.setViatura(viatura);
				
				viaturaUpmRepository.save(viaturaUpm);
				
		});
	}
	

	
}

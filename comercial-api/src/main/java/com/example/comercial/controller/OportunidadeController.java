package com.example.comercial.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.comercial.model.Oportunidade;
import com.example.comercial.repository.OportunidadeRepository;



@CrossOrigin("http://localhost:4200")
@RestController /* fala que e um controlador rest que responde a requisiçoes http */
@RequestMapping("/oportunidades") // mapeando na uri da requisição
public class OportunidadeController {

	@Autowired
	private OportunidadeRepository oportunidadeRepository;

	@GetMapping // responde a requisçoes http do tipo Get
	public List<Oportunidade> listar() {

		return oportunidadeRepository.findAll();

	}

	@GetMapping("/{id}")

	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {

		Optional<Oportunidade> oportunidade = oportunidadeRepository.findById(id);

		if (oportunidade.isPresent()) {
			return ResponseEntity.ok(oportunidade.get());

		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)

	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {

		Optional<Oportunidade>oportunidadeExistente=oportunidadeRepository.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
				if(oportunidadeExistente.isPresent()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Já existe oportunidade para este prospecto com a mesma descrição");
					
				}
				
		return oportunidadeRepository.save(oportunidade);

	}

}

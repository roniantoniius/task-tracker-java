package com.roniantonius.tugas.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.tugas.domain.dto.TugasDaftarDto;
import com.roniantonius.tugas.domain.entities.TugasDaftar;
import com.roniantonius.tugas.mappers.TugasDaftarMapper;
import com.roniantonius.tugas.services.TugasDaftarService;

@RestController
@RequestMapping(path = "/task-lists")
public class TugasDaftarController {
	private final TugasDaftarService tugasDaftarService;
	private final TugasDaftarMapper tugasDaftarMapper;
	public TugasDaftarController(TugasDaftarService tugasDaftarService, TugasDaftarMapper tugasDaftarMapper) {
		this.tugasDaftarService = tugasDaftarService;
		this.tugasDaftarMapper = tugasDaftarMapper;
	}
	
	@GetMapping
	public List<TugasDaftarDto> daftarTugasDaftar(){
		return tugasDaftarService.listTugasDaftars()
				.stream()
				.map(tugasDaftarMapper::toDto)
				.toList();
	}
	
	@PostMapping
	public TugasDaftarDto createTugasDaftar(@RequestBody TugasDaftarDto tugasDaftarDto) {
		TugasDaftar createdTugasDaftar = tugasDaftarService.createTugasDaftar(tugasDaftarMapper.fromDto(tugasDaftarDto));
		return tugasDaftarMapper.toDto(createdTugasDaftar);
	}
	
	@GetMapping(path = "/{tugas_daftar_id}")
	public Optional<TugasDaftarDto> getTugasDaftar(@PathVariable("tugas_daftar_id") UUID tugasDaftarId){
		return tugasDaftarService.getTugasDaftar(tugasDaftarId).map(tugasDaftarMapper::toDto);
	}
	
	@PutMapping(path = "/{tugas_daftar_id")
	public TugasDaftarDto updateTugasDaftar(@PathVariable("tugas_daftar_id") UUID tugasDaftarId, @RequestBody TugasDaftarDto tugasDaftarDto) {
		TugasDaftar updateTugasDaftar = tugasDaftarService.updateTugasDaftar(tugasDaftarId, tugasDaftarMapper.fromDto(tugasDaftarDto));
		return tugasDaftarMapper.toDto(updateTugasDaftar);
	}
	
	@DeleteMapping(path = "/{tugas_daftar_id}")
	public void deleteTugasDaftar(@PathVariable("tugas_daftar_id") UUID tugasDaftarId) {
		tugasDaftarService.deleteTugasDaftar(tugasDaftarId);
	}
}

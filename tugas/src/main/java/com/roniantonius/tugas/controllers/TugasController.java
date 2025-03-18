package com.roniantonius.tugas.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.tugas.domain.dto.TugasDto;
import com.roniantonius.tugas.domain.entities.Tugas;
import com.roniantonius.tugas.mappers.TugasMapper;
import com.roniantonius.tugas.services.TugasService;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TugasController {
	private final TugasService tugasService;
	private final TugasMapper tugasMapper;
	public TugasController(TugasService tugasService, TugasMapper tugasMapper) {
		this.tugasService = tugasService;
		this.tugasMapper = tugasMapper;
	}
	
	@GetMapping
	public List<TugasDto> listTugas(@PathVariable("task_list_id") UUID tugasDaftarId){ // ambil id TugasDaftar
		return tugasService.listTugas(tugasDaftarId).stream()
				.map(tugasMapper::toDto)
				.toList();
	}
	
	@PostMapping
	public TugasDto createTugas(@PathVariable("task_list_id") UUID tugasDaftarId, @RequestBody TugasDto tugasDto) {
		Tugas tugasBaru = tugasService.createTugas(tugasDaftarId, tugasMapper.fromDto(tugasDto));
		return tugasMapper.toDto(tugasBaru);
	}
	
	@GetMapping(path = "/{task_id}")
	public Optional<TugasDto> getTugas(@PathVariable("task_list_id") UUID tugasDaftarId, @PathVariable("task_id") UUID id){
		return tugasService.getTugas(tugasDaftarId, id).map(tugasMapper::toDto);
	}
}

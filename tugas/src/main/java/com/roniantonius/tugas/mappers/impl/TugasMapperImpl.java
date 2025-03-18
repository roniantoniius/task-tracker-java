package com.roniantonius.tugas.mappers.impl;

import org.springframework.stereotype.Component;

import com.roniantonius.tugas.domain.dto.TugasDto;
import com.roniantonius.tugas.domain.entities.Tugas;
import com.roniantonius.tugas.mappers.TugasMapper;

@Component
public class TugasMapperImpl implements TugasMapper{

	@Override
	public Tugas fromDto(TugasDto tugasDto) {
		// TODO Auto-generated method stub
		return new Tugas(
				tugasDto.id(),
				tugasDto.nama(),
				tugasDto.deskripsi(),
				tugasDto.waktuDeadline(),
				tugasDto.status(),
				tugasDto.prioritas(),
				null, // ini adalah foreign key which is TugasDaftar
				null, // kedua ini waktu
				null
				);
	}

	@Override
	public TugasDto toDto(Tugas tugas) {
		// TODO Auto-generated method stub
		return new TugasDto(tugas.getId(), tugas.getNama(), tugas.getDeskripsi(), tugas.getWaktuDeadline(), tugas.getStatus(), tugas.getPrioritas());
	}
	
}
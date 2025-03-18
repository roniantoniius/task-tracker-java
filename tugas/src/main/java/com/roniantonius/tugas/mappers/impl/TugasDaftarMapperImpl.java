package com.roniantonius.tugas.mappers.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.roniantonius.tugas.domain.dto.TugasDaftarDto;
import com.roniantonius.tugas.domain.dto.TugasDto;
import com.roniantonius.tugas.domain.entities.Tugas;
import com.roniantonius.tugas.domain.entities.TugasDaftar;
import com.roniantonius.tugas.domain.entities.TugasStatus;
import com.roniantonius.tugas.mappers.TugasDaftarMapper;
import com.roniantonius.tugas.mappers.TugasMapper;

@Component
public class TugasDaftarMapperImpl implements TugasDaftarMapper{

	private final TugasMapper tugasMapper;
	
	public TugasDaftarMapperImpl(TugasMapper tugasMapper) {
		this.tugasMapper = tugasMapper;
	}
	
	@Override
	public TugasDaftar fromDto(TugasDaftarDto tugasDaftarDto) {
		// TODO Auto-generated method stub
		return new TugasDaftar(
				tugasDaftarDto.id(),
				tugasDaftarDto.nama(),
				tugasDaftarDto.deskripsi(),
				Optional.ofNullable(tugasDaftarDto.tugas())
					.map(misi -> misi.stream()
							.map(tugasMapper::fromDto)
							.toList()
							).orElse(null),
				null,
				null
				);
	}

	@Override
	public TugasDaftarDto toDto(TugasDaftar tugasDaftar) {
		// TODO Auto-generated method stub
		return new TugasDaftarDto(
				tugasDaftar.getId(),
				tugasDaftar.getNama(),
				tugasDaftar.getDeskripsi(),
				Optional.ofNullable(tugasDaftar.getTugas())
					.map(List::size).orElse(0),
				hitungProgresTugasDaftar(tugasDaftar.getTugas()),
				Optional.ofNullable(tugasDaftar.getTugas())
					.map(misi -> misi.stream()
							.map(tugasMapper::toDto)
							.toList()
							).orElse(null));
	}
	
	private Double hitungProgresTugasDaftar(List<Tugas> tugas) {
		if (tugas == null) {
			return null;
		}
		
		Long jumlahTugasTutup = tugas.stream()
				.filter(tugass -> TugasStatus.TUTUP == tugass.getStatus()).count();
		
		return (double) jumlahTugasTutup / tugas.size();
	}
}

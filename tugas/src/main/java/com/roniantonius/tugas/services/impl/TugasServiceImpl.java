package com.roniantonius.tugas.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.tugas.domain.entities.Tugas;
import com.roniantonius.tugas.domain.entities.TugasDaftar;
import com.roniantonius.tugas.domain.entities.TugasPriority;
import com.roniantonius.tugas.domain.entities.TugasStatus;
import com.roniantonius.tugas.repositories.TugasDaftarRepository;
import com.roniantonius.tugas.repositories.TugasRepository;
import com.roniantonius.tugas.services.TugasService;

@Service
public class TugasServiceImpl implements TugasService{

	private final TugasRepository tugasRepository;
	private final TugasDaftarRepository tugasDaftarRepository;
	
	public TugasServiceImpl(TugasRepository tugasRepository, TugasDaftarRepository tugasDaftarRepository) {
		this.tugasRepository = tugasRepository;
		this.tugasDaftarRepository = tugasDaftarRepository;
	}

	@Override
	public List<Tugas> listTugas(UUID tugasDaftarId) {
		// TODO Auto-generated method stub
		return tugasRepository.findByTugasDaftarId(tugasDaftarId);
	}

	@Override
	public Tugas createTugas(UUID tugasDaftarId, Tugas tugas) {
		// TODO Auto-generated method stub
		if (tugas.getId() != null) {
			throw new IllegalArgumentException("Tugas sudah memiliki id");
		}
		if (tugas.getNama() == null || tugas.getNama().isBlank()) {
			throw new IllegalArgumentException("Tugas harus memiliki judul");
		}
		TugasPriority tugasPriority = Optional.ofNullable(tugas.getPrioritas()).orElse(TugasPriority.SEDANG);
		TugasStatus tugasStatus = TugasStatus.BUKA;
		TugasDaftar tugasDaftar = tugasDaftarRepository.findById(tugasDaftarId).orElseThrow(() -> new IllegalArgumentException("Invalid Tugas ID diterima!"));
		
		LocalDateTime waktuSekarang = LocalDateTime.now();
		Tugas simpanTugas = new Tugas(
				null,
				tugas.getNama(),
				tugas.getDeskripsi(),
				tugas.getWaktuDeadline(),
				tugasStatus,
				tugasPriority,
				tugasDaftar,
				waktuSekarang,
				waktuSekarang
				
		);
		return tugasRepository.save(simpanTugas);
	}

	@Override
	public Optional<Tugas> getTugas(UUID tugasDaftarId, UUID id) {
		// TODO Auto-generated method stub
		return tugasRepository.findByTugasDaftarIdAndId(tugasDaftarId, id);
	}	
}
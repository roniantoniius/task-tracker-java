package com.roniantonius.tugas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.roniantonius.tugas.domain.entities.Tugas;

public interface TugasService {
	List<Tugas> listTugas(UUID tugasDaftarId);
	Tugas createTugas(UUID tugasDaftarId, Tugas tugas);
	Optional<Tugas> getTugas(UUID tugasDaftarId, UUID id);
	Tugas updateTugas(UUID tugasDaftarId, UUID id, Tugas tugas);
	void deleteTugas(UUID tugasDaftarId, UUID tugasId);
}
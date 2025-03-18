package com.roniantonius.tugas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.roniantonius.tugas.domain.entities.TugasDaftar;

public interface TugasDaftarService {
	List<TugasDaftar> listTugasDaftars();
	TugasDaftar createTugasDaftar(TugasDaftar tugasDaftar);
	Optional<TugasDaftar> getTugasDaftar(UUID id);
	TugasDaftar updateTugasDaftar(UUID id, TugasDaftar tugasDaftar);
	void deleteTugasDaftar(UUID tugasDaftarId);
}

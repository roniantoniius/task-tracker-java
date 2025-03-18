package com.roniantonius.tugas.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.tugas.domain.entities.Tugas;

@Repository
public interface TugasRepository extends JpaRepository<Tugas, UUID>{
	List<Tugas> findByTugasDaftarId(UUID tugasDaftarId); // ini mencari daftar tugas dari TugasDaftar
	Optional<Tugas> findByTugasDaftarIdAndId(UUID tugasDaftarId, UUID id); // ini mencari tugas spesifik dari TugasDaftar
}

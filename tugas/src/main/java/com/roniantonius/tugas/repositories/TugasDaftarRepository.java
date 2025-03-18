package com.roniantonius.tugas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.tugas.domain.entities.TugasDaftar;

@Repository
public interface TugasDaftarRepository extends JpaRepository<TugasDaftar, UUID>{
	
}

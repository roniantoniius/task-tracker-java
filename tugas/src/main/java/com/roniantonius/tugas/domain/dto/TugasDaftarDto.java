package com.roniantonius.tugas.domain.dto;

import java.util.List;
import java.util.UUID;

public record TugasDaftarDto (
	UUID id,
	String nama,
	String deskripsi,
	Integer hitung,
	Double progress,
	List<TugasDto> tugas
) {
	
}
package com.roniantonius.tugas.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.roniantonius.tugas.domain.entities.TugasPriority;
import com.roniantonius.tugas.domain.entities.TugasStatus;

public record TugasDto (
	UUID id,
	String nama,
	String deskripsi,
	LocalDateTime waktuDeadline,
	TugasStatus status,
	TugasPriority prioritas
){
	
}
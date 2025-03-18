package com.roniantonius.tugas.mappers;

import com.roniantonius.tugas.domain.dto.TugasDto;
import com.roniantonius.tugas.domain.entities.Tugas;

public interface TugasMapper {
	Tugas fromDto(TugasDto tugasDto);
	TugasDto toDto(Tugas tugas);
}
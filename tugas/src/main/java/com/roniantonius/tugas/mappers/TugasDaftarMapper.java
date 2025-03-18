package com.roniantonius.tugas.mappers;

import com.roniantonius.tugas.domain.dto.TugasDaftarDto;
import com.roniantonius.tugas.domain.dto.TugasDto;
import com.roniantonius.tugas.domain.entities.TugasDaftar;

public interface TugasDaftarMapper {
	TugasDaftar fromDto(TugasDaftarDto tugasDto);
	TugasDaftarDto toDto(TugasDaftar tugasDaftar);
}

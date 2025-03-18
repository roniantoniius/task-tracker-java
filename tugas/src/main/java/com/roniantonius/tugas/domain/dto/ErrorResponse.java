package com.roniantonius.tugas.domain.dto;

public record ErrorResponse(
		int status,
		String pesan,
		String detail
	) {

}

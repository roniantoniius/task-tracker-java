package com.roniantonius.tugas.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.stereotype.Service;

import com.roniantonius.tugas.domain.entities.TugasDaftar;
import com.roniantonius.tugas.repositories.TugasDaftarRepository;
import com.roniantonius.tugas.services.TugasDaftarService;
@Service
public class TugasDaftarServiceImpl implements TugasDaftarService{
	private TugasDaftarRepository tugasDaftarRepository;
	
	public TugasDaftarServiceImpl(TugasDaftarRepository tugasDaftarRepository) {
		this.tugasDaftarRepository = tugasDaftarRepository;
	}

	@Override
	public List<TugasDaftar> listTugasDaftars() {
		// TODO Auto-generated method stub
		return tugasDaftarRepository.findAll();
	}

	@Override
	public TugasDaftar createTugasDaftar(TugasDaftar tugasDaftar) {
		// TODO Auto-generated method stub
		if (tugasDaftar.getId() != null) {
			throw new IllegalArgumentException("Daftar tugas tersebut sudah ada!");
		}
		if (tugasDaftar.getNama() == null || tugasDaftar.getNama().isBlank()) {
			throw new IllegalArgumentException("Daftar tugas harus berisi judul!");
		}
		LocalDateTime sekarang = LocalDateTime.now();
		return tugasDaftarRepository.save(new TugasDaftar(
				null,
				tugasDaftar.getNama(),
				tugasDaftar.getDeskripsi(),
				null,
				sekarang,
				sekarang));
	}

	@Override
	public Optional<TugasDaftar> getTugasDaftar(UUID id) {
		// TODO Auto-generated method stub
		return tugasDaftarRepository.findById(id);
	}

	@Override
	public TugasDaftar updateTugasDaftar(UUID id, TugasDaftar tugasDaftar) {
		// TODO Auto-generated method stub
		if (tugasDaftar.getId() == null) {
			throw new IllegalArgumentException("Daftar tugas harus ada ID!");
		}
		if (!Objects.equals(tugasDaftar.getId(), id)) {
			throw new IllegalArgumentException("Kamu mencoba mengubah id, namun entitasnya berbeda");
		}
		TugasDaftar tugasDaftarAda = tugasDaftarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Daftar tugas tidak ditemukan"));
		tugasDaftarAda.setNama(tugasDaftar.getNama());
		tugasDaftarAda.setDeskripsi(tugasDaftar.getDeskripsi());
		tugasDaftarAda.setWaktuDiupdate(LocalDateTime.now());
		return tugasDaftarRepository.save(tugasDaftarAda);
	}

	@Override
	public void deleteTugasDaftar(UUID tugasDaftarId) {
		// TODO Auto-generated method stub
		tugasDaftarRepository.deleteById(tugasDaftarId);
	}

}

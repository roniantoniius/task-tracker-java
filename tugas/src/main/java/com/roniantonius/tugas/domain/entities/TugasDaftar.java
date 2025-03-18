package com.roniantonius.tugas.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tugas_daftar")
public class TugasDaftar {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@Column(name = "deskripsi")
	private String deskripsi;
	
	//relasi, parameter di mappedBy itu merupakan nama suatu variabel foreign key pada tabel yang menerapkan foreign key
	@OneToMany(mappedBy = "tugasDaftar", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Tugas> tugas;
	
	@Column(name = "waktu_dibuat", nullable = false)
	private LocalDateTime waktuDibuat;
	
	@Column(name = "waktu_diupdate", nullable = false)
	private LocalDateTime waktuDiupdate;
	
	public TugasDaftar() {
	}

	public TugasDaftar(UUID id, String nama, String deskripsi, List<Tugas> tugas, LocalDateTime waktuDibuat,
			LocalDateTime waktuDiupdate) {
		this.id = id;
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.tugas = tugas;
		this.waktuDibuat = waktuDibuat;
		this.waktuDiupdate = waktuDiupdate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public List<Tugas> getTugas() {
		return tugas;
	}

	public void setTugas(List<Tugas> tugas) {
		this.tugas = tugas;
	}

	public LocalDateTime getWaktuDibuat() {
		return waktuDibuat;
	}

	public void setWaktuDibuat(LocalDateTime waktuDibuat) {
		this.waktuDibuat = waktuDibuat;
	}

	public LocalDateTime getWaktuDiupdate() {
		return waktuDiupdate;
	}

	public void setWaktuDiupdate(LocalDateTime waktuDiupdate) {
		this.waktuDiupdate = waktuDiupdate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deskripsi, id, nama, tugas, waktuDibuat, waktuDiupdate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TugasDaftar other = (TugasDaftar) obj;
		return Objects.equals(deskripsi, other.deskripsi) && Objects.equals(id, other.id)
				&& Objects.equals(nama, other.nama) && Objects.equals(tugas, other.tugas)
				&& Objects.equals(waktuDibuat, other.waktuDibuat) && Objects.equals(waktuDiupdate, other.waktuDiupdate);
	}

	@Override
	public String toString() {
		return "TugasDaftar [id=" + id + ", nama=" + nama + ", deskripsi=" + deskripsi + ", tugas=" + tugas
				+ ", waktuDibuat=" + waktuDibuat + ", waktuDiupdate=" + waktuDiupdate + "]";
	}
}

package com.roniantonius.tugas.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tugas")
public class Tugas {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@Column(name = "deskripsi")
	private String deskripsi;
	
	@Column(name = "waktu_deadline")
	private LocalDateTime waktuDeadline;
	
	@Column(name = "status", nullable = false)
	private TugasStatus status;
	
	@Column(name = "prioritas", nullable = false)
	private TugasPriority prioritas;
	
	@ManyToOne(fetch = FetchType.LAZY) // ini untuk memberi tahu JPA bahwa data di bawh akan di load ketika dipanggil
	@JoinColumn(name = "tugas_daftar_id") // ini nama variabel foreign keynya
	private TugasDaftar tugasDaftar;
	
	@Column(name = "waktu_dibuat", nullable = false)
	private LocalDateTime waktuDibuat;
	
	@Column(name = "waktu_diupdate", nullable = false)
	private LocalDateTime waktuDiupdate;
	
	public Tugas() {
		
	}

	public Tugas(UUID id, String nama, String deskripsi, LocalDateTime waktuDeadline, TugasStatus status,
			TugasPriority prioritas, TugasDaftar tugasDaftar, LocalDateTime waktuDibuat, LocalDateTime waktuDiupdate) {
		super();
		this.id = id;
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.waktuDeadline = waktuDeadline;
		this.status = status;
		this.prioritas = prioritas;
		this.tugasDaftar = tugasDaftar;
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

	public LocalDateTime getWaktuDeadline() {
		return waktuDeadline;
	}

	public void setWaktuDeadline(LocalDateTime waktuDeadline) {
		this.waktuDeadline = waktuDeadline;
	}

	public TugasStatus getStatus() {
		return status;
	}

	public void setStatus(TugasStatus status) {
		this.status = status;
	}

	public TugasPriority getPrioritas() {
		return prioritas;
	}

	public void setPrioritas(TugasPriority prioritas) {
		this.prioritas = prioritas;
	}

	public TugasDaftar getTugasDaftar() {
		return tugasDaftar;
	}

	public void setTugasDaftar(TugasDaftar tugasDaftar) {
		this.tugasDaftar = tugasDaftar;
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
		return Objects.hash(deskripsi, id, nama, prioritas, status, tugasDaftar, waktuDeadline, waktuDibuat,
				waktuDiupdate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tugas other = (Tugas) obj;
		return Objects.equals(deskripsi, other.deskripsi) && Objects.equals(id, other.id)
				&& Objects.equals(nama, other.nama) && prioritas == other.prioritas && status == other.status
				&& Objects.equals(tugasDaftar, other.tugasDaftar) && Objects.equals(waktuDeadline, other.waktuDeadline)
				&& Objects.equals(waktuDibuat, other.waktuDibuat) && Objects.equals(waktuDiupdate, other.waktuDiupdate);
	}

	@Override
	public String toString() {
		return "Tugas [id=" + id + ", nama=" + nama + ", deskripsi=" + deskripsi + ", waktuDeadline=" + waktuDeadline
				+ ", status=" + status + ", prioritas=" + prioritas + ", tugasDaftar=" + tugasDaftar + ", waktuDibuat="
				+ waktuDibuat + ", waktuDiupdate=" + waktuDiupdate + "]";
	}
}
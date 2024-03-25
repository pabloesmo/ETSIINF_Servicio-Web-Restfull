package com.datos;

public class Usuario {
	private int id;
	private String nombre;
	private String fechaNacimiento;
	private String email;
	
	public Usuario(String nombre, String fechaNacimiento, String email) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getfechaNacimiento() {
		return fechaNacimiento;
	}

	public void setfechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario => {" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", fechaNacimiento='" + fechaNacimiento + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
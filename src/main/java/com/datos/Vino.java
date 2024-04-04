package com.datos;

public class Vino {
	private int id;
	private String nombre;
	private String bodega;
	private int añada;
	private String denominacion;
	private String tipo;
	private double puntuacion;
	
	public Vino(String nombre, String bodega, int añada, String denominacion, String tipo,double puntuacion) {
		this.nombre = nombre;
		this.bodega = bodega;
		this.añada = añada;
		this.denominacion = denominacion;
		this.tipo = tipo;
		this.puntuacion = puntuacion;
	}
	
	public Vino() {
		
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

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public int getAñada() {
		return añada;
	}

	public void setAñada(int añada) {
		this.añada = añada;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPuntuacion() {
		return puntuacion;
	}
	
	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	@Override
	public String toString() {
		return "Vino{" +
				"Id=" + id +
				", Nombre='" + nombre + '\'' +
				", Bodega='" + bodega + '\'' +
				", Agnada=" + añada +
				", Denominacion='" + denominacion + '\'' +
				", Tipo='" + tipo +
				'}';
	}
}
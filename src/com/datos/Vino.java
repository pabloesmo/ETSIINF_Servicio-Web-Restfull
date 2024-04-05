package com.datos;

public class Vino {
	private int id;
	private String nombre;
	private String bodega;
	private int añada;
	private String denominacion;
	private String tipo;
	
	public Vino(String nombre, String bodega, int añada, String denominacion, String tipo) {
		this.nombre = nombre;
		this.bodega = bodega;
		this.añada = añada;
		this.denominacion = denominacion;
		this.tipo = tipo;
	}
	
	public Vino(int id,String nombre, String bodega, int añada, String denominacion, String tipo) {
		this.id = id;
		this.nombre = nombre;
		this.bodega = bodega;
		this.añada = añada;
		this.denominacion = denominacion;
		this.tipo = tipo;
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
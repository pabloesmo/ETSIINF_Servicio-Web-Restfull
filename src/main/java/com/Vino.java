package com.datos;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "vino")
public class Vino {
	private int id;
	private String nombre;
	private String bodega;
	private int añada;
	private String denominacion;
	private String tipo;
	private String[] tiposUva;
	
	public Vino(String nombre) {
		this.nombre = nombre;
	}
	
	@XmlAttribute(required=false)
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

	public String[] getTiposUva() {
		return tiposUva;
	}

	public void setTiposUva(String[] tiposUva) {
		this.tiposUva = tiposUva;
	}
}

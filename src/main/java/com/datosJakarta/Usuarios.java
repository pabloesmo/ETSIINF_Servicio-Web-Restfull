package com.datosJakarta;
import java.util.ArrayList;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuarios")
public class Usuarios {
	private ArrayList<Link> usuarios;

	public Usuarios() {
		this.usuarios = new ArrayList<Link>();
	}

	@XmlElement(name="usuario")
	public ArrayList<Link> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Link> usuarios) {
		this.usuarios = usuarios;
	}
}

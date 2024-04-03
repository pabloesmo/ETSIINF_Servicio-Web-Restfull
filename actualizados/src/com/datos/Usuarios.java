package com.datos;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

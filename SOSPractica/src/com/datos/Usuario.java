package com.datos;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "usuario")
public class Usuario{
	private int id_usuario;
	private String nombre;
	private String fechaNacimiento;
	private String email;
	private ArrayList<Seguidor> seguidores;
	private ArrayList<Vino> vinos;
	
	public Usuario(String nombre, String fechaNacimiento, String email) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.seguidores = new ArrayList<>();
		this.vinos = new ArrayList<>();
	}

	@XmlAttribute(required=false)
	public int getId() {
		return id_usuario;
	}

	public void setId(int id) {
		this.id_usuario = id;
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
				"id=" + id_usuario +
				", nombre='" + nombre + '\'' +
				", fechaNacimiento='" + fechaNacimiento + '\'' +
				", email='" + email + '\'' +
				'}';
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addSeguidor(Seguidor seguidor){
		seguidores.add(seguidor);
	}

	public void addVino(Vino vino){
		vinos.add(vino);
	}
	
	public Vino getVino(String nombre) {
		for (int i=0; i < vinos.size(); i++) {
			if (vinos.get(i).getNombre().equals(nombre)) {
				return vinos.get(i);
			}
		}
		return null;
	}

	public boolean existeVino(String nombre){
		for (int i=0; i < vinos.size(); i++) {
			if (vinos.get(i).getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	public void deleteVino(String nombre){
		for (int i=0; i < vinos.size(); i++) {
			if (vinos.get(i).getNombre().equals(nombre)) {
				vinos.remove(i);
			}
		}
	}

	public Seguidor getSeguidor(String nombre) {
		for (int i=0; i < seguidores.size(); i++) {
			if (seguidores.get(i).getNombre().equals(nombre)) {
				return seguidores.get(i);
			}
		}
		return null;
	}

	public Seguidor getSeguidor(int id) {
		for (int i=0; i < seguidores.size(); i++) {
			if (seguidores.get(i).getId() == id) {
				return seguidores.get(i);
			}
		}
		return null;
	}

	public boolean existeSeguidor(String nombre){
        for (int i=0; i < seguidores.size(); i++) {
            if (seguidores.get(i).getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

	public boolean existeSeguidor(int id){
        for (int i=0; i < seguidores.size(); i++) {
            if (seguidores.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Seguidor[] getSeguidores() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSeguidores'");
    }
    
    @XmlElementWrapper(name="vinos")
    @XmlElement(name="vino") 
    //@XmlTransient
    public ArrayList<Vino> getVinos() {
        return vinos;
    }

    public void setVinos(ArrayList<Vino> vinos) {
        this.vinos = vinos;
    }
}
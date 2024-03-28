package vinos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario extends DBTable {
	
	private int id_usuario;
	private String nombre;
	private String fechaNacimiento;
	private String email;
	
	public Usuario(int id_usuario, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_usuario = id_usuario;
		if(DBSync) {
			insertEntry();
		}
	}
	
	public Usuario (int id_usuario, String nombre, String fechaNacimiento, String email, DBConnection conn, boolean DBSync){
		super(conn, DBSync);
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}
	
	//Devuelve el id_usuario
	public int getId_usuario() {
		if (DBSync) {
			getEntryChanges();
		}
		return id_usuario;
	}
	
	//Devuelve el nombre del usuario
	public String getNombre_usuario() {
		if (DBSync) {
			getEntryChanges();
		}
		return nombre;
	}
	
	//Devuelve la fechaNacimiento
	public String getFechaNacimiento() {
		if (DBSync) {
			getEntryChanges();
		}
		return fechaNacimiento;
	}
	
	//Devuelve la fechaNacimiento
	public String getEmail() {
		if (DBSync) {
			getEntryChanges();
		}
		return email;
	}
	
	public void setNombre(String nombre) {
		if (DBSync) {
			getEntryChanges();
			updateEntry();
		}
		this.nombre = nombre;
	}
	
	public void setFechaNacimiento(String fechaNacimiento) {
		if (DBSync) {
			getEntryChanges();
			updateEntry();
		}
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public void setEmail(String email) {
		if (DBSync) {
			getEntryChanges();
			updateEntry();
		}
		this.email = email;
	}

	
	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", email=" + email + "]";
	}

	@Override
	boolean createTable() {
		boolean creada = false;
		//si ya existe no hago nada
		if(conn.tableExists("usuario")) {
			return false;
		}
		
		String sql = "CREATE TABLE usuario (\n"
				+ "	   id INT NOT NULL AUTO_INCREMENT,\n"
				+ "    nombre VARCHAR(50) NOT NULL,\n"
				+ "    fechaNacimiento DATE NOT NULL,\n"
				+ "    email VARCHAR(100) NOT NULL,\n"
				+ "    PRIMARY KEY(id)\n"
				+ ");";
		int ok = conn.update(sql);
		if(ok == 0) {
			creada = true;
		}
		return creada;	
	}
	
	@Override
	boolean insertEntry() {
	    boolean insertado = false;
	    if (DBSync) {
	        // Si no existe la tabla, la creo antes de insertar nada
	        if (!conn.tableExists("usuario")) {
	            createTable();
	        }
	        
	        String sql = "INSERT INTO usuario (nombre, fechaNacimiento, email) VALUES (?,?,?)";
	        ArrayList<Object> params = new ArrayList<>();
	        params.add(nombre != null ? nombre : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(fechaNacimiento != null ? fechaNacimiento : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(email != null ? email : DBConnection.NULL_SENTINEL_VARCHAR);
	        int filas = conn.update(sql, params);
	        insertado = filas == 1;
	        // No se ha podido insertar el elemento
	        if (filas != 1) {
	            this.id_usuario = 0;
	            DBSync = false;
	            insertado = false;
	        }
	    }
	    return insertado;
	}


	@Override
	boolean updateEntry() {
	    boolean actualizado = false;
	    if (DBSync) {
	        String sql = "UPDATE usuario SET nombre = ?, fechaNacimiento= ?, email= ? WHERE id = ?";
	        ArrayList<Object> params = new ArrayList<>();
	        
	        //si son null los parametros en el constructor los pongo a valor centinela
	        params.add(nombre != null ? nombre : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(fechaNacimiento != null ? fechaNacimiento: DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(email != null ? email : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(id_usuario);
	        int filas = conn.update(sql, params);
	        if (filas > 0) {
	            actualizado = true;
	        }
	    }
	    return actualizado;
	}

//	@Override
//	boolean deleteEntry() {
//		boolean eliminado = false;
//		if (DBSync) {
//			String sql = "DELETE FROM usuario WHERE id = ?";
//			ArrayList<Object> params = new ArrayList<>();
//			params.add(id_usuario);
//			int filas = conn.update(sql, params);
//			if (filas == 1)
//				eliminado = true;
//		}
//		return eliminado;
//	}

	public void destroy() {
	    String sql = "DELETE FROM usuario WHERE nombre = ?";
	    ArrayList<Object> params = new ArrayList<>();
	    params.add(nombre);
	    conn.update(sql, params);
	    
	    // Borra los valores de los atributos
	    id_usuario = DBConnection.NULL_SENTINEL_INT;
	    nombre = DBConnection.NULL_SENTINEL_VARCHAR;
	    fechaNacimiento = DBConnection.NULL_SENTINEL_VARCHAR;
	    email = DBConnection.NULL_SENTINEL_VARCHAR;
	    
	    // Indica que la sincronización ya no es necesaria
	    DBSync = false;
	}


	@Override
	void getEntryChanges() {
	    if (DBSync) {
	        String sql = "SELECT * FROM usuario WHERE id = ?";
	        ArrayList<Object> params = new ArrayList<>();
	        params.add(id_usuario);
	        try {
	            ResultSet rs = conn.query(sql, params);

	            if (rs.next()) {	                
	                id_usuario = rs.getInt("id");
	                nombre = rs.getString("nombre");
	                fechaNacimiento = rs.getString("fechaNacimiento");
	                email = rs.getString("email");
	            }
	        } catch (SQLException e) {
	            e.getMessage();
	        }
	    }
	}	
}
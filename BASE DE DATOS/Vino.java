package vinos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Vino extends DBTable{
	
	private int id_vino;
	private String nombre;
	private String bodega;
	private int agnada;
	private String denominacion;
	private String tipo;
	private String[] tiposUva;
	private int puntuacion;
	
	public Vino(int id_vino, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_vino = id_vino;
		if(DBSync) {
			insertEntry();
		}
	}
	
	public Vino(int id_vino, String nombre, String bodega, int añada, String denominacion, String tipo, String[] tiposUva, int puntuacion, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_vino = id_vino;
		this.nombre = nombre;
		this.bodega = bodega;
		this.agnada = añada;
		this.denominacion = denominacion;
		this.tipo = tipo;
		this.tiposUva = tiposUva;
		this.puntuacion = puntuacion;
	}

	public int getId() {
		return id_vino;
	}
	
	public void setId(int id) {
		this.id_vino = id;
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
		return agnada;
	}

	public void setAñada(int añada) {
		this.agnada = añada;
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
	
	private String imprimeArr(String [] arr) {
		String res = "";
		for(int i=0; i<arr.length; i++) {
			res += arr[i] + ",";
		}
		return res;
	}

	@Override
	public String toString() {
		return "Vino{" +
				"  id=" + id_vino +
				", nombre='" + nombre + '\'' +
				", bodega='" + bodega + '\'' +
				", añada=" + agnada +
				", denominacion='" + denominacion + '\'' +
				", tipo='" + tipo + '\'' +
				", tiposUva=" + imprimeArr(tiposUva) +
				'}';
	}
	
	@Override
	boolean createTable() {
		boolean creada = false;
		//si ya existe no hago nada
		if(conn.tableExists("vino")) {
			return false;
		}
		String sql = "CREATE TABLE vino (\n"
				+ "    id INT NOT NULL AUTO_INCREMENT,\n"
				+ "    nombre VARCHAR(50) NOT NULL,\n"
				+ "    bodega VARCHAR(25) NOT NULL,\n"
				+ "    agnada INT NOT NULL,\n"
				+ "    denominacion VARCHAR(40) NOT NULL,\n"
				+ "    tipo VARCHAR(25) NOT NULL,\n"
				+ "    tiposUva VARCHAR(25) NOT NULL,\n"
				+ "    puntuacion INT NOT NULL,\n"
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
	        if (!conn.tableExists("vino")) {
	            createTable();
	        }
	        
	        String sql = "INSERT INTO vino(nombre,bodega,agnada,denominacion,tipo,tiposUva,puntuacion) VALUES (?,?,?,?,?,?,?)";
	        ArrayList<Object> params = new ArrayList<>();
	        params.add(nombre != null ? nombre : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(bodega != null ? bodega : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(agnada);
	        params.add(denominacion != null ? denominacion : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(tipo != null ? tipo : DBConnection.NULL_SENTINEL_VARCHAR);
	        
	        // Convierte el array de tiposUva a JSON
	        String tiposUvaJSON = new Gson().toJson(tiposUva);
	        params.add(tiposUvaJSON);
	        
	        params.add(puntuacion);
	        
	        int filas = conn.update(sql, params);
	        insertado = filas == 1;
	        
	        // No se ha podido insertar el elemento
	        if (filas != 1) {
	            this.id_vino = 0;
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
	        String sql = "UPDATE vino SET nombre = ?, bodega= ?, agnada= ?, denominacion= ?, tipo= ?, tiposUva= ?, puntuacion= ? WHERE id = ?";
	        ArrayList<Object> params = new ArrayList<>();
	        
	        //si son null los parametros en el constructor los pongo a valor centinela
	        params.add(nombre != null ? nombre : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(bodega != null ? bodega : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(agnada);
	        params.add(denominacion != null ? denominacion : DBConnection.NULL_SENTINEL_VARCHAR);
	        params.add(tipo != null ? tipo : DBConnection.NULL_SENTINEL_VARCHAR);

	        // Convierte el array de tiposUva a JSON
	        String tiposUvaJSON = new Gson().toJson(tiposUva);
	        params.add(tiposUvaJSON);
	        
	        params.add(puntuacion);
	        params.add(id_vino);
	        int filas = conn.update(sql, params);
	        if (filas > 0) {
	            actualizado = true;
	        }
	    }
	    return actualizado;
	}
	
	public void destroy() {
	    String sql = "DELETE FROM vino WHERE nombre = ?";
	    ArrayList<Object> params = new ArrayList<>();
	    params.add(nombre);
	    conn.update(sql, params);
	    
	    // Borra los valores de los atributos
	    id_vino = DBConnection.NULL_SENTINEL_INT;
	    nombre = DBConnection.NULL_SENTINEL_VARCHAR;
	    bodega = DBConnection.NULL_SENTINEL_VARCHAR;
	    agnada = DBConnection.NULL_SENTINEL_INT;
	    denominacion = DBConnection.NULL_SENTINEL_VARCHAR;
	    tipo = DBConnection.NULL_SENTINEL_VARCHAR;
	    tiposUva = null;
	    puntuacion = DBConnection.NULL_SENTINEL_INT;
	    
	    // Indica que la sincronización ya no es necesaria
	    DBSync = false;
	}
	
	@Override
	void getEntryChanges() {
	    if (DBSync) {
	        String sql = "SELECT * FROM vino WHERE id = ?";
	        ArrayList<Object> params = new ArrayList<>();
	        params.add(id_vino);
	        try {
	            ResultSet rs = conn.query(sql, params);

	            if (rs.next()) {
	                // Obtiene los valores de los tipos de uva como una cadena desde la base de datos
	                String tiposUvaString = rs.getString("tiposUva");
	                // Divide la cadena en un arreglo de cadenas usando ',' como delimitador
	                tiposUva = tiposUvaString.split(",");
	                
	                // Obtén otros valores y asígnalos a los atributos de la clase
	                id_vino = rs.getInt("id");
	                nombre = rs.getString("nombre");
	                bodega = rs.getString("bodega");
	                agnada = rs.getInt("agnada");
	                denominacion = rs.getString("denominacion");
	                tipo = rs.getString("tipo");
	                puntuacion = rs.getInt("puntuacion");
	            }
	        } catch (SQLException e) {
	            e.getMessage();
	        }
	    }
	}
	
}
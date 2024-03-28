package vinos;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
	final static String NULL_SENTINEL_VARCHAR = "NULL";
	final static int NULL_SENTINEL_INT = Integer.MIN_VALUE;
	final static java.sql.Date NULL_SENTINEL_DATE = java.sql.Date.valueOf("1900-01-01");
	
	private Connection conn;
	private String user;
	private String pass;
	private String url;
	
	
	public DBConnection(String server, int port, String user, String pass, String database){
		this.user = user;
		this.pass = pass;
		this.url = "jdbc:mysql://" + server + ":" + port + "/" + database;		
	}
	
	public boolean connect() {   
	    try {
	        // Cargar el controlador JDBC
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, user, pass); // Utilizar los parámetros proporcionados
	        System.out.println("-----------Conexión IN-----------");
	        return true;
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error al cargar el controlador");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.out.println("Error en la conexión");
	        e.printStackTrace();
	    }
	    return false;
	}

	
	
	public boolean close(){
		boolean conexion = false;
		try {
	        if (conn != null && !conn.isClosed()) {
	            conn.close();
	            System.out.println("-----------Conexión OUT----------");
	            conexion = true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al cerrar la conexión");
	        e.printStackTrace();
	    }		
		return conexion;
	}

	
	public int update(String sql) {
		try {
	        Statement statement = conn.createStatement();
	        int rowCount = statement.executeUpdate(sql);
	        return rowCount;
	    } catch (SQLException e) {
	        System.out.println("Error al ejecutar el comando SQL");
	        e.printStackTrace();
	        return -1;
	    }
	}
	
	public int update(String sql, ArrayList<Object> a) {
		try {
            PreparedStatement statement = conn.prepareStatement(sql);
            setParameters(statement, a);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
	}
	
	 private void setParameters(PreparedStatement statement, ArrayList<Object> a) throws SQLException {
		 if (a != null) {
			 for (int i = 0; i < a.size(); i++) {
				 Object param = a.get(i);
				 if (param != null){
					 statement.setObject(i + 1, param);
				 }else {
					 statement.setNull(i + 1, java.sql.Types.NULL);
	             }
	         }
	     }
	 }
	
	public ResultSet query(String sql) {
		try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public ResultSet query(String sql, ArrayList<Object> a) {
		try{
			PreparedStatement st = conn.prepareStatement(sql);
            setParameters(st, a);
   
		    ResultSet srs = st.executeQuery(); 
	        return srs;
	        
		}catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
		}
		
	}
	
	public boolean tableExists(String tableName) {
		 try {
			 Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery("SHOW TABLES");
	         while (resultSet.next()) {
	        	 String existingTable = resultSet.getString(1);
	             if (existingTable.equalsIgnoreCase(tableName)) {
	            	 return true;
	             }
	         }	        
	        }catch (SQLException e) {
	        	e.printStackTrace();
	        }
      return false;
	}
}
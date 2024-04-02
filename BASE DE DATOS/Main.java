package vinos;

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
	    String server = "localhost";
	    int port = 3306;
	    String user = "vinosUser";
	    String pass = "vinosPass";
	    String database = "vinos";
	    
	    DBConnection dbc = new DBConnection(server, port, user, pass, database);
	    dbc.connect();
	    
	    ////////////////////////////////////////////////////////////////////////////////
	    Usuario u = new Usuario(4,"Pepe","2010-02-22","pepe.alumno@correo.es",dbc,true);
	    //Usuario u = new Usuario(2,"Luis","2005-09-12","luis.alumno@correo.es",dbc,true);
	    
	    //CREAR UN USUARIO FUNCIONA!!
	    //u.insertEntry();
	    
	    //MODIFICAR UN USUARIO FUNCIONA!!
	    //u.updateEntry();
	    
	    //BORRAR UN USUARIO FUNCIONA!!
	    //u.destroy();
	    
	    //GET USUARIO FUNCIONA RARO EN LA FECHA DE NACIMIENTO
//	    Usuario u1 = new Usuario(3,dbc,true);
//	    u1.getEntryChanges();
//	    System.out.println("AQUI DOY EL ACTUAL: \n");
//	    System.out.println(u1.toString());
	    
	    
	    ////////////////////////////////////////////////////////////////////////////////
	    
	    
	    ////////////////////////////////////////////////////////////////////////////////
	    String[] tiposUva = {"Uva roja","Uva morada"};
	    Vino v = new Vino(1,"Ramon Bilbao","Bodega Los Carmenes",1994,"Origen Manchego","Tinto",tiposUva,89,dbc,true);
	    
	    //CREAR UN VINO FUNCIONA!!
	    //v.insertEntry();
	    
	    //MODIFICAR UN VINO FUNCIONA!!
	    //v.updateEntry();
	    
	    //BORRAR UN VINO FUNCIONA!!
	    //v.destroy();
	    
	    //GET VINO FUNCIONA!!
//	    Vino v1 = new Vino(1,dbc,true);
//		v1.getEntryChanges();
//		System.out.println("AQUI DOY EL ACTUAL : \n");
//		System.out.println(v1.toString());
	    
	    ////////////////////////////////////////////////////////////////////////////////
	    
	    
	    
	    dbc.close();
	}
}
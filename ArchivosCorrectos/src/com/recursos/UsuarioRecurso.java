package com.recursos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.naming.NamingContext;
import com.datos.ListaUsuarios;
import com.datos.Sistema;
import com.datos.Usuario;



@Path("/usuarios")
public class UsuarioRecurso {

    @Context
	private UriInfo uriInfo;

    private DataSource ds;
    private Connection conn;

    public UsuarioRecurso() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			NamingContext envCtx = (NamingContext) ctx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/vinos");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


    //SOLUCIONADAAAA!!
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.TEXT_HTML)
    public String getUsuario(@PathParam("nombre") String nombre) {
    	try {
    		String sql = "SELECT * FROM usuario WHERE nombre = ?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, nombre);
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			String nombreUsuario = rs.getString("nombre");
    			String fechaNacimiento = rs.getString("fechaNacimiento");
    			String email = rs.getString("email");
    			
    			return "<html><body>" +
                "<h2>Detalles del Usuario</h2>" +
                "<p><strong>Nombre:</strong> " + nombreUsuario + "</p>" +
                "<p><strong>Fecha de Nacimiento:</strong> " + fechaNacimiento + "</p>" +
                "<p><strong>Email:</strong> " + email + "</p>" +
                "</body></html>";
    		} else {
    			return "<html><body><h2>Usuario no encontrado</h2></body></html>";
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    		return "<html><body><h2>Error al obtener el usuario</h2></body></html>";
    	}
    }
    
 
    //SOLUCIONADAAA!!
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addUsuario(@FormParam("nombre") String nombre, @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("email") String email) {
        try {
        	String sql = "INSERT INTO usuario (nombre, fechaNacimiento, email) VALUES (?,?,?)";
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ps.setString(1, nombre);
        	ps.setString(2, fechaNacimiento);
        	ps.setString(3, email);
        	
        	int affectedRows = ps.executeUpdate();
        	if(affectedRows > 0) {
        		return Response.status(Response.Status.CREATED).build();
        	} else {
        		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar usuario").build();
        }
    }
    

    //SOLUCIONADAAA!!
    @PUT
    @Path("/{usuario_id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateUsuario(@PathParam("usuario_id") int usuarioId, @FormParam("nombre") String nombre, @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("email") String email) {
        try {
        	String sql = "UPDATE usuario SET nombre = ?, fechaNacimiento = ?, email = ? WHERE id = ?";
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ps.setString(1, nombre);
        	ps.setString(2, fechaNacimiento);
        	ps.setString(3, email);
        	ps.setInt(4, usuarioId);
        	
        	int affectedRows = ps.executeUpdate();
        	if(affectedRows > 0) {
        		return Response.status(Response.Status.OK).build();
        	} else {
        		return Response.status(Response.Status.NOT_FOUND).build();
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar usuario").build();
        }
    }
    

    //SOLUCIONADAAA!!
    @DELETE
    @Path("/{usuario_id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteUsuario(@PathParam("usuario_id") int usuarioId) {
        try {
        	String sql = "DELETE FROM usuario WHERE id = ?";
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ps.setInt(1,usuarioId);
        	
        	int affectedRows = ps.executeUpdate();
        		if(affectedRows > 0) {
        			// Devolvemos una respuesta con código HTTP 200 OK
        			return Response.status(Response.Status.OK).build();
        		} else {
        			//Devolvemos una respuesta con código HTTP 404 Not Found
        			return Response.status(Response.Status.NOT_FOUND).build();
        		}
        	} catch(SQLException e) {
        	e.printStackTrace();
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar usuario").build();
        }
    }

    //SOLUCIONADAAA!!
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getUsuarios() {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head><title>Usuarios</title></head>");
        html.append("<body>");
        html.append("<h1>Lista de Usuarios</h1>");
        
        try {
        	String sql = "SELECT * FROM usuario";
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ResultSet rs = ps.executeQuery();
        	
        	while(rs.next()) {
        		int id = rs.getInt("id");
        		String nombre = rs.getString("nombre");
        		String fechaNacimiento = rs.getString("fechaNacimiento");
        		String email = rs.getString("email");
	            html.append("<p>------------------------------------------------------------------------</p>");
	            html.append("<div>");
	            html.append("<p><strong>Id:</strong> ").append(id).append("</p>");
	            html.append("<p><strong>Nombre:</strong> ").append(nombre).append("</p>");
	            html.append("<p><strong>Fecha de Nacimiento:</strong> ").append(fechaNacimiento).append("</p>");
	            html.append("<p><strong>Email:</strong> ").append(email).append("</p>");
	            html.append("</div>");
	        }
        }catch (SQLException e){
        	e.printStackTrace();
        	html.append("<p>Error al obtener la lista de usuarios</p>");
        }
        
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }
}
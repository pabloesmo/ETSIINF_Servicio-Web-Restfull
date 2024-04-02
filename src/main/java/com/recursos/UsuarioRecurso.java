package com.recursos;
import com.datos.ListaUsuarios;
import com.datos.Sistema;
import com.datos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import org.apache.naming.NamingContext;
import javax.sql.DataSource;
import java.sql.Connection;
import com.basededatos.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import javax.naming.NamingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;


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


    //FUNCIONA!!
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.TEXT_HTML)
    public String getUsuario(@PathParam("nombre") String nombre) {
        Usuario usuario = ListaUsuarios.getUsuario(nombre);
        // System.out.println(ListaUsuarios.toStringUsuarios());
        if(usuario == null) {
            return "<html><body><h2>Usuario no encontrado</h2></body></html>";
        } else {
            return "<html><body>" +
                   "<h2>Detalles del Usuario</h2>" +
                   "<p><strong>Nombre:</strong> " + usuario.getNombre() + "</p>" +
                   "<p><strong>Fecha de Nacimiento:</strong> " + usuario.getfechaNacimiento() + "</p>" +
                   "<p><strong>Email:</strong> " + usuario.getEmail() + "</p>" +
                   "</body></html>";
        }
    }

    //FUNCIONA!!
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addUsuario(@FormParam("nombre") String nombre, @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("email") String correo) {
        if(!ListaUsuarios.existeUsuario(nombre)){
            Usuario usuario = new Usuario(nombre, fechaNacimiento, correo);
            usuario.setId(Sistema.getId_usuario());
            ListaUsuarios.addUsuario(usuario);
                // Devolvemos una respuesta con código HTTP 201 Created
                return Response.status(Response.Status.CREATED).build();
        }else{
            // Devolvemos una respuesta con código HTTP 500 Internal Server Error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //FUNCIONA!!
    @PUT
    @Path("/{nombre}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateUsuario(@FormParam("nombre") String nombre, @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("email") String correo) {
        Usuario usuario = ListaUsuarios.getUsuario(nombre);
        if(usuario != null){
            usuario.setfechaNacimiento(fechaNacimiento);
            usuario.setEmail(correo);
            // Devolvemos una respuesta con código HTTP 200 OK
            return Response.status(Response.Status.OK).build();
        } else {
            // Devolvemos una respuesta con código HTTP 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //FUNCIONA!!
    @DELETE
    @Path("/{nombre}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteUsuario(@FormParam("nombre") String nombre) {
        if(ListaUsuarios.existeUsuario(nombre)){
            ListaUsuarios.deleteUsuario(nombre);
            // Devolvemos una respuesta con código HTTP 200 OK
            return Response.status(Response.Status.OK).build();
        } else {
            // Devolvemos una respuesta con código HTTP 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //FUNCIONA!!
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getUsuarios() {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head><title>Usuarios</title></head>");
        html.append("<body>");
        html.append("<h1>Lista de Usuarios</h1>");
        for (Usuario usuario : ListaUsuarios.getUsuarios()) {
            html.append("<p>------------------------------------------------------------------------</p>");
            html.append("<div>");
            html.append("<p><strong>Id:</strong> ").append(usuario.getId()).append("</p>");
            html.append("<p><strong>Nombre:</strong> ").append(usuario.getNombre()).append("</p>");
            html.append("<p><strong>Fecha de Nacimiento:</strong> ").append(usuario.getfechaNacimiento()).append("</p>");
            html.append("<p><strong>Email:</strong> ").append(usuario.getEmail()).append("</p>");
            html.append("</div>");
        }
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response crearUsuario(Usuario usuario){
        try {
            String sql = "INSERT INTO 'vinos'.'usuario' (`nombre`, `fechaNacimiento`, `email`) " + "VALUES ('"
            + usuario.getNombre() + "', '" + usuario.getfechaNacimiento() + "', '" + usuario.getEmail() + "');";
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			int affectedRows = ps.executeUpdate();
            // Obtener el ID del elemento recién creado. 
			// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un statement.executeUpdate() o al crear un PreparedStatement
			ResultSet generatedID = ps.getGeneratedKeys();
            if (generatedID.next()) {
                usuario.setId(generatedID.getInt(1));
                String location = uriInfo.getAbsolutePath() + "/" + usuario.getId();
                return Response.status(Response.Status.CREATED).entity(usuario).header("Location", location).header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
			
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario\n" + e.getStackTrace()).build();
		}
    }








}
package com.recursos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.naming.NamingContext;
import com.datos.*;


@Path("/usuarios")
public class UsuariosRecurso {

    @Context
	private UriInfo uriInfo;

    private DataSource ds;
    private Connection conn;

    public UsuariosRecurso() {
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
    //SOLO FALTA QUE SE LE PUEDA PASAR AL POSTMAN UN JSON/XML COMO "Consumes"
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response addUsuario(@FormParam("nombre") String nombre, @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("email") String email) throws ParseException {
//        try {
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        	Date fechaNac = sdf.parse(fechaNacimiento);
//        	Date fechaMayorEdad = new Date(System.currentTimeMillis() - (18L * 365 * 24 * 60 * 60 * 1000));
//        	
//        	if(fechaNac.after(fechaMayorEdad)) {
//        		return Response.status(Response.Status.BAD_REQUEST).entity("El usuario debe ser mayor de edad.").build();
//        	}
//        	String sql = "INSERT INTO usuario (nombre, fechaNacimiento, email) VALUES (?,?,?)";
//        	PreparedStatement ps = conn.prepareStatement(sql);
//        	ps.setString(1, nombre);
//        	ps.setString(2, fechaNacimiento);
//        	ps.setString(3, email);
//        	
//        	int affectedRows = ps.executeUpdate();
//        	if(affectedRows > 0) {
//        		return Response.status(Response.Status.CREATED).build();
//        	} else {
//        		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        	}
//        } catch (SQLException e) {
//        	e.printStackTrace();
//        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar usuario").build();
//        }
//    }
    
    
    //VERSION CON XML Y JSON COMO CONSUMES
    @POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addUsuario(Usuario usuario) throws ParseException {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Date fechaNac = sdf.parse(usuario.getfechaNacimiento());
        	Date fechaMayorEdad = new Date(System.currentTimeMillis() - (18L * 365 * 24 * 60 * 60 * 1000));
        	
        	if(fechaNac.after(fechaMayorEdad)) {
        		return Response.status(Response.Status.BAD_REQUEST).entity("El usuario debe ser mayor de edad.").build();
        	}
        	String sql = "INSERT INTO `vinos`.`usuario` (`nombre`, `fechaNacimiento`, `email`) " + "VALUES ('"
					+ usuario.getNombre() + "', '" + usuario.getfechaNacimiento() + "', '" + usuario.getEmail() + "');";
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			int affectedRows = ps.executeUpdate();
        	
			//obtener el ID del usuario creado
			// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un statement.executeUpdate() o al crear un PreparedStatement
        	ResultSet generatedID = ps.getGeneratedKeys();
        	if(generatedID.next()) {
        		usuario.setId(generatedID.getInt(1));
        		String location = uriInfo.getAbsolutePath() + "" + usuario.getId();
        		return Response.status(Response.Status.CREATED).entity(usuario).header("Location", location).header("Content-Location", location).build();
        	}
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
        
        } catch (SQLException e) {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar usuario\n" + e.getStackTrace()).build();
        }
    }
    
 // Lista de garajes JSON/XML generada con listas en JAXB
// 	@GET
// 	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
// 	public Response getUsuarios(@QueryParam("offset") @DefaultValue("1") String offset,
// 			@QueryParam("count") @DefaultValue("10") String count) {
// 		try {
// 			int off = Integer.parseInt(offset);
// 			int c = Integer.parseInt(count);
// 			String sql = "SELECT * FROM usuario order by id LIMIT " + (off - 1) + "," + c + ";";
// 			PreparedStatement ps = conn.prepareStatement(sql);
// 			ResultSet rs = ps.executeQuery();
// 			Usuarios u = new Usuarios();
// 			ArrayList<Link> usuarios = u.getUsuarios();
// 			rs.beforeFirst();
// 			while (rs.next()) {
// 				usuarios.add(new Link(uriInfo.getAbsolutePath() + "/" + rs.getInt("id"),"self"));
// 			}
// 			return Response.status(Response.Status.OK).entity(g).build(); // No se puede devolver el ArrayList (para generar XML)
// 		} catch (NumberFormatException e) {
// 			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números")
// 					.build();
// 		} catch (SQLException e) {
// 			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
// 		}
// 	} 

        
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
    
    
    @GET
    @Path("/{usuario_id}/vinos")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getVinos(@PathParam("usuario_id") int usuarioId) {
    	try {
            String sql = "SELECT * FROM vino WHERE usuario_id = ?";
      		PreparedStatement ps = conn.prepareStatement(sql);
      		ResultSet rs = ps.executeQuery();
      		
      		ArrayList<Vino> vinos = new ArrayList<>();
      		
      		while(rs.next()) {
      			Vino vino = new Vino();
      			vino.setId(rs.getInt("id"));
      			vino.setNombre(rs.getString("nombre"));
      			vino.setBodega(rs.getString("bodega"));
      			vino.setAñada(rs.getInt("agnada"));
      			vino.setDenominacion(rs.getString("denominacion"));
      			vino.setTipo(rs.getString("tipo"));
      			vino.setTiposUva(rs.getString("tiposUva"));
      			vino.setPuntuacion(rs.getInt("puntuacion"));
      		}
      		return Response.status(Response.Status.OK).entity(vinos).build();
    	} catch (SQLException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener vinos\n" + e.getMessage()).build();
    	}
    		
    }
    
    
    
    //FUNCION PARA AÑADIR VINOS A LA LISTA DE CADA USUARIO
    //Pensar si la uri debe ser: <<</usuarios/Pepe/vinos>>> (Por nombre) o <<</usuarios/2/vinos>>> (Por id)
    //TODO REVISAR QUE EN LA CLASE DE USUARIOS Y VINOS ESTE LO DE XML nseq mas pero sirve para que el bicho lea
    //el xml/json y entienda el objeto raro que le paso. (XMLAttribute o nsq era)
    @POST 
    @Path("/{usuario_id}/vinos")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response addVino(@PathParam("usuario_id") int usuarioId, Vino vino){
    	try {
          String sql = "INSERT INTO vino (usuario_id, nombre, bodega, agnada, denominacion, tipo, tiposUva, puntuacion) " + 
        		  "VALUES (?,?,?,?,?,?,?,?)";
  		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
  		ps.setInt(1, usuarioId);
  		ps.setString(2, vino.getNombre());
  		ps.setString(3, vino.getBodega());
  		ps.setInt(4, vino.getAñada());
  		ps.setString(5, vino.getDenominacion());
  		ps.setString(6, vino.getTipo());
  		ps.setString(7, vino.getTiposUva());
  		ps.setInt(8, vino.getPuntuacion());
  		int affectedRows = ps.executeUpdate();
         	
  		//obtener el ID del vino creado
  		// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un statement.executeUpdate() o al crear un PreparedStatement
         ResultSet generatedID = ps.getGeneratedKeys();
          if(generatedID.next()) {
          	vino.setId(generatedID.getInt(1));
          	String location = uriInfo.getAbsolutePath() + "" + vino.getId();
          	return Response.status(Response.Status.CREATED).entity(vino).header("Location", location).header("Content-Location", location).build();
          }
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo agregar el vino").build();
          
         } catch (SQLException e) {
        	 return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar vino\n" + e.getStackTrace()).build();
         }
     }
    
  
    
    
}
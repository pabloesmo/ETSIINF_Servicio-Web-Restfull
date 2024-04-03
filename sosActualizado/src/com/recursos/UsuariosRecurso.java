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

    
    //ESTE ES EL GET TOCHO QUE DEVUELVE LAS URIS DE LOS USUARIOS QUE HAY
 	@GET
 	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
 	public Response getUsuarios(@QueryParam("offset") @DefaultValue("1") String offset,
 			@QueryParam("count") @DefaultValue("10") String count) {
 		try {
 			int off = Integer.parseInt(offset);
 			int c = Integer.parseInt(count);
 			String sql = "SELECT * FROM usuario order by id LIMIT " + (off - 1) + "," + c + ";";
 			PreparedStatement ps = conn.prepareStatement(sql);
 			ResultSet rs = ps.executeQuery();
 			Usuarios u = new Usuarios();
 			ArrayList<Link> usuarios = u.getUsuarios();
 			rs.beforeFirst();
 			while (rs.next()) {
 				usuarios.add(new Link(uriInfo.getAbsolutePath() + "/" + rs.getString("nombre"),"self"));
 			}
 			return Response.status(Response.Status.OK).entity(u).build(); // No se puede devolver el ArrayList (para generar XML)
 		} catch (NumberFormatException e) {
 			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números")
 					.build();
 		} catch (SQLException e) {
 			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
 		}
 	} 
    
    
    //YA FUNCIONA!!
    //TODO HABRIA QUE CAMBIAR EL MODO DE DEVOLUCION PARA DEVOLVER LO QUE ELLOS QUIEREN
    //ESTA FUNCION ES: lista de usuarios con filtro de nombre (si 2 empiezan por Pe- pues aparecen ambos
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(@PathParam("nombre") String nombre) {
    	try {
    		String sql = "SELECT * FROM usuario WHERE nombre LIKE ?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, nombre + "%");
    		ResultSet rs = ps.executeQuery();
    		
    		ArrayList<Usuario> usuarios = new ArrayList<>();
    		
    		while (rs.next()) {
    			Usuario usuario = new Usuario();
    			usuario.setId(rs.getInt("id"));
    			usuario.setNombre(rs.getString("nombre"));
    			usuario.setfechaNacimiento(rs.getString("fechaNacimiento"));
    			usuario.setEmail(rs.getString("email")); 
    			
    			usuarios.add(usuario);
    		}
    		return Response.status(Response.Status.OK).entity(usuarios).build();
    	} catch(SQLException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener usuarios de la base de datos\n" + e.getMessage()).build();
    	}
    }
    
    
	//YA FUNCIONA!!!
  	@POST
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response addUsuario(Usuario usuario) throws ParseException {
		try {
      		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      		Date fechaNac = sdf.parse(usuario.getfechaNacimiento());
      		Date fechaMayorEdad = new Date(System.currentTimeMillis() - (18L * 365 * 24 * 60 * 60 * 1000));
      	
      		if(fechaNac.after(fechaMayorEdad)) {
      			return Response.status(Response.Status.BAD_REQUEST).entity("El usuario debe ser mayor de edad.").build();
      		}
      		String sql = "INSERT INTO vinos.usuario (nombre, fechaNacimiento, email) " + 
      					"VALUES (?,?,?)";
      		PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
      		ps.setString(1, usuario.getNombre());
      		ps.setString(2,usuario.getfechaNacimiento());
      		ps.setString(3, usuario.getEmail());
      	
			int affectedRows = ps.executeUpdate();
			//obtener el ID del usuario creado
			// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un statement.executeUpdate() o al crear un PreparedStatement
			ResultSet generatedID = ps.getGeneratedKeys();
			if(generatedID.next()) {
				usuario.setId(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath().toString() + "" + usuario.getId();
				
				//AQUI ESTABA EL ERROR!!! EN entity(usuario)
				//SOLUCIONADO!!
				return Response.status(Response.Status.CREATED).entity(usuario).header("Location", location).header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar usuario\n" + e.getStackTrace()).build();
		}
  	}
    

  	//YA FUNCIONA!!!
	@PUT
    @Path("/{usuario_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsuario(@PathParam("usuario_id") int usuarioId, Usuario usuarioNuevo) {
        try {
        	String sql = "UPDATE usuario SET nombre = ?, fechaNacimiento = ?, email = ? WHERE id = ?";
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ps.setString(1, usuarioNuevo.getNombre());
        	ps.setString(2, usuarioNuevo.getfechaNacimiento());
        	ps.setString(3, usuarioNuevo.getEmail());
        	ps.setInt(4, usuarioId);
        	
        	int affectedRows = ps.executeUpdate();
        	if(affectedRows > 0) {
        		String location = uriInfo.getBaseUri() + "usuarios/" + usuarioNuevo.getId();
    			return Response.status(Response.Status.OK).entity(usuarioNuevo).header("Content-Location", location).build();
        	} else {
        		return Response.status(Response.Status.NOT_FOUND).build();
        	}
        } catch (SQLException e) {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar usuario\n" + e.getStackTrace()).build();
        }
    }
    
	
    //YA FUNCIONA!!!
    @DELETE
    @Path("{usuario_id}")
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
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar usuario\n" + e.getStackTrace()).build();
        }
    }
    

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	
	//YA FUNCIONA!!!
    @GET
    @Path("/{usuario_id}/vinos")
    public Response getVinos(@PathParam("usuario_id") int usuarioId) {
    	try {
            String sql = "SELECT * FROM vino WHERE id_usuario = ?";
      		PreparedStatement ps = conn.prepareStatement(sql);
      		ps.setInt(1, usuarioId);
      		ResultSet rs = ps.executeQuery();
      		
      		ArrayList<Vino> vinos = new ArrayList<>();
      		
      		while(rs.next()) {
      			Vino vino = new Vino();
      			vino.setId(rs.getInt("id_vino"));
      			vino.setNombre(rs.getString("nombre"));
      			vino.setBodega(rs.getString("bodega"));
      			vino.setAñada(rs.getInt("agnada"));
      			vino.setDenominacion(rs.getString("denominacion"));
      			vino.setTipo(rs.getString("tipo"));
      			vino.setPuntuacion(rs.getDouble("puntuacion"));
      			
      			vinos.add(vino);
      		}
      		return Response.status(Response.Status.OK).entity(vinos).build();
    	} catch (SQLException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener vinos de la base de datos\n" + e.getMessage()).build();
    	}
    		
    }
    
    
    //FUNCION PARA AÑADIR VINOS A LA LISTA DE CADA USUARIO
    //Pensar si la uri debe ser: <<</usuarios/Pepe/vinos>>> (Por nombre) o <<</usuarios/2/vinos>>> (Por id)
    
    //YA FUNCIONA!!!
    @POST
	@Path("/{usuario_id}/vinos")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVino(@PathParam("usuario_id") int usuarioId, Vino vino) {
		try {
			double puntuacion = vino.getPuntuacion();
			if(puntuacion < 0 || puntuacion > 10) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Error: La puntuacion debe estar entre 0 y 10").build();
			}
			String sql = "INSERT INTO vino (id_usuario, nombre, bodega, agnada, denominacion, tipo, puntuacion) "
					+
					"VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, usuarioId);
			ps.setString(2, vino.getNombre());
			ps.setString(3, vino.getBodega());
			ps.setInt(4, vino.getAñada());
			ps.setString(5, vino.getDenominacion());
			ps.setString(6, vino.getTipo());
			ps.setDouble(7, puntuacion);

			int affectedRows = ps.executeUpdate();

			// obtener el ID del vino creado
			// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un
			// statement.executeUpdate() o al crear un PreparedStatement
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) {
				vino.setId(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath().toString() + "/" + vino.getId();
				return Response.status(Response.Status.CREATED).entity(vino).header("Location", location)
						.header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo agregar el vino").build();

		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al agregar vino en base de datos\n" + e.getStackTrace()).build();
		}
	}
    

    //YA FUNCIONA!!!
    @PUT
	@Path("/{usuario_id}/vinos/{vino_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateVino(@PathParam("usuario_id") int usuarioId, @PathParam("vino_id") int vinoId, Vino vino) {
		try {
			// ver si el vino pertenece a la lista o no
			if (!vinoPerteneceUsuario(usuarioId, vinoId)) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("El vino no se encuentra en la lista del usuario").build();
			}
			
			double puntuacion = vino.getPuntuacion();
			if(puntuacion < 0 || puntuacion > 10) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Error: La puntuacion debe estar entre 0 y 10").build();
			}

			String sql = "UPDATE vino SET puntuacion = ? WHERE id_vino = ? AND id_usuario = ?";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, puntuacion);
			ps.setInt(2, vinoId);
			ps.setInt(3, usuarioId);
			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				return Response.status(Response.Status.OK).entity("Puntuacion del vino actualizada correctamente").build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("No se encontro el vino").build();
			}
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al actualizar el vino\n" + e.getMessage()).build();
		}
	}
    
    
    //YA FUNCIONA!!!
    @DELETE
	@Path("/{usuario_id}/vinos/{vino_id}")
	public Response deleteVino(@PathParam("usuario_id") int usuarioId, @PathParam("vino_id") int vinoId) {
		try {
			// ver si el vino pertenece a la lista o no
			if (!vinoPerteneceUsuario(usuarioId, vinoId)) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity("El vino no se encuentra en la lista del usuario").build();
			}

			String sql = "DELETE FROM vino WHERE id_vino = ? AND id_usuario = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, vinoId);
			ps.setInt(2, usuarioId);
			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				// Devolvemos una respuesta con código HTTP 200 OK
				return Response.status(Response.Status.OK).entity("Vino eliminado existosamente").build();
			} else {
				// Devolvemos una respuesta con código HTTP 404 Not Found
				return Response.status(Response.Status.NOT_FOUND).entity("No se encontro el vino").build();
			}
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al eliminar el vino\n" + e.getMessage()).build();
		}
	}
    
    
    //FUNCION AUXILIAR PARA BORRADO
    private boolean vinoPerteneceUsuario(int usuarioId, int vinoId) throws SQLException {
		String sql = "SELECT COUNT(*) AS count FROM vino WHERE id_vino = ? AND id_usuario = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, vinoId);
		ps.setInt(2, usuarioId);
		ResultSet rs = ps.executeQuery();
    	
		if(rs.next()) {
			int count = rs.getInt("count");
			return count > 0;
		} else {
			return false;
		}
    }
    

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//YA FUNCIONA!!!
	@GET
	@Path("/{usuario_id}/seguidores")
	public Response getSeguidores(@PathParam("usuario_id") int usuarioId, @QueryParam("nombre") String nombre, @QueryParam("limit") int limit) {
		try {
			String sql = "SELECT * FROM usuario WHERE id IN (SELECT seguidor_id FROM seguimiento WHERE usuario_seguido_id = ?)";
			if (nombre != null) {
				sql += " AND nombre LIKE ?";
			}
			if (limit > 0) {
				sql += " LIMIT ?";
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, usuarioId);
			int parameterIndex = 2;
			if (nombre != null) {
				ps.setString(parameterIndex, "%" + nombre + "%");
				parameterIndex++;
			}
			if (limit > 0) {
				ps.setInt(parameterIndex, limit);
			}
			ResultSet rs = ps.executeQuery();
			ArrayList<Usuario> seguidores = new ArrayList<>();
			
			while(rs.next()) {
				Usuario seguidor = new Usuario();
				seguidor.setId(rs.getInt("id"));
				seguidor.setNombre(rs.getString("nombre"));
				seguidor.setfechaNacimiento(rs.getString("fechaNacimiento"));
				seguidor.setEmail(rs.getString("email"));
				seguidores.add(seguidor);
			}
			return Response.status(Response.Status.OK).entity(seguidores).build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener seguidores\n" + e.getMessage()).build();
		}
	}
	
	//YA FUNCIONA!!!
    @POST
	@Path("/{seguidor_id}/seguimiento/{usuario_seguido_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response seguirUsuario(@PathParam("seguidor_id") int seguidorId,
			@PathParam("usuario_seguido_id") int usuarioSeguidoId) {
		try {
			String sql = "INSERT INTO seguimiento(seguidor_id, usuario_seguido_id) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, seguidorId);
			ps.setInt(2, usuarioSeguidoId);
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				return Response.status(Response.Status.CREATED).entity("Se ha añadido el seguidor correctamente.")
						.build();
			} else {
				return Response.status(Response.Status.CREATED).entity("No se ha podido añadir el seguidor.").build();
			}
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al añadir el seguidor\n" + e.getMessage()).build();
		}
	}

	//YA FUNCIONA!!!
	@DELETE
	@Path("/{seguidor_id}/seguimiento/{usuario_seguido_id}")
	public Response deleteSeguidor(@PathParam("seguidor_id") int seguidorId, @PathParam("usuario_seguido_id") int usuarioSeguidoId){
		try {
			String sql = "DELETE FROM seguimiento WHERE seguidor_id = ? AND usuario_seguido_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, seguidorId);
			ps.setInt(2, usuarioSeguidoId);
			int affectedRows = ps.executeUpdate();
			
			if(affectedRows > 0) {
				return Response.status(Response.Status.OK).entity("Seguidor eliminado correctamente").build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("No se encontro el seguidor").build();
			}
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el seguidor\n" + e.getMessage()).build();
		}
	}



}
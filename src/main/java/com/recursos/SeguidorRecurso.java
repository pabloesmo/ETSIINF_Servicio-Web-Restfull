package com.recursos;

import com.datos.ListaUsuarios;
import com.datos.Seguidor;
import com.datos.Usuario;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/seguidores")
public class SeguidorRecurso {
    
    @GET
    @Path("/{id_usuario}/seguidor/id_seguidor")
    @Produces(MediaType.TEXT_HTML)
    public String getSeguidor(@PathParam("id_usuario") int idUsuario, @PathParam("id_seguidor") int idSeguidor) {
        Usuario usuario = ListaUsuarios.getUsuario(idUsuario);
        if(usuario == null) {
            return "<html><body><h2>Usuario no encontrado</h2></body></html>";
        } else {
            Seguidor seguidor = usuario.getSeguidor(idSeguidor);
            if(seguidor == null) {
                return "<html><body><h2>Seguidor no encontrado</h2></body></html>";
            } else {
                return "<html><body>" +
                       "<h2>Detalles del Seguidor</h2>" +
                       "<p><strong>Nombre:</strong> " + seguidor.getNombre() + "</p>" +
                       "<p><strong>Fecha de Nacimiento:</strong> " + seguidor.getfechaNacimiento() + "</p>" +
                       "<p><strong>Email:</strong> " + seguidor.getEmail() + "</p>" +
                       "</body></html>";
            }
        }
    }
        
    @POST
    @Path("/{id_usuario}/seguidor/{id_seguidor}")
    public Response addSeguidor(@PathParam("id_usuario") int idUsuario, @PathParam("id_seguidor") int idSeguidor) {
       if(ListaUsuarios.getUsuario(idUsuario) == null){/*Compruebo si existe el usuario */
           return Response.status(Response.Status.NOT_FOUND).build();
       }
       if(ListaUsuarios.existeUsuario(idSeguidor)){/*Compruebo si ya existe le seguidor */
            if(ListaUsuarios.getUsuario(idUsuario).existeSeguidor(idSeguidor)){/*Compruebo si ya existe le seguidor */
                return Response.status(Response.Status.CONFLICT).build();
            }
           Seguidor seguidor = new Seguidor(ListaUsuarios.getUsuario(idSeguidor).getNombre(), ListaUsuarios.getUsuario(idSeguidor).getfechaNacimiento(), ListaUsuarios.getUsuario(idSeguidor).getEmail());/*si no esxite lo creo  */
           ListaUsuarios.getUsuario(idUsuario).addSeguidor(seguidor);/*añado el seguidor a la lista de seguidores de ese usuario */
           // Devolvemos una respuesta con código HTTP 201 Created
           return Response.status(Response.Status.CREATED).build();
        }else{
            // En este caso, podrías devolver un código de estado HTTP 409 Conflict para indicar que la operación no pudo completarse debido a un conflicto con el estado actual del recurso.
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    

}

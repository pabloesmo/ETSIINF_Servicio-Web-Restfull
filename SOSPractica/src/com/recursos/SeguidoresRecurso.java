package com.recursos;

import com.datos.ListaUsuarios;
import com.datos.Seguidor;
import com.datos.Sistema;
import com.datos.Usuario;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/seguidores")
public class SeguidoresRecurso {
    /*Funcio para ver un seguidor */
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
    /*Funcion para crear un seguidor */
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
           seguidor.setId(Sistema.getId_seguidor());
           ListaUsuarios.getUsuario(idUsuario).addSeguidor(seguidor);/*añado el seguidor a la lista de seguidores de ese usuario */
           // Devolvemos una respuesta con código HTTP 201 Created
           return Response.status(Response.Status.CREATED).build();
        }else{
            // En este caso, podrías devolver un código de estado HTTP 409 Conflict para indicar que la operación no pudo completarse debido a un conflicto con el estado actual del recurso.
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    /*funcion para cojer la lista de seguidores
     */
    @GET
    @Path("/{id_usuario}/seguidores")
    @Produces(MediaType.TEXT_HTML)
    public String getSeguidores(@PathParam("id_usuario") int idUsuario) {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head><title>Usuarios</title></head>");
        html.append("<body>");
        html.append("<h1>Lista de Usuarios</h1>");
        for (Seguidor seguidor: ListaUsuarios.getUsuario(idUsuario).getSeguidores()) {
            html.append("<p>------------------------------------------------------------------------</p>");
            html.append("<div>");
            html.append("<p><strong>Nombre:</strong> ").append(seguidor.getNombre()).append("</p>");
            html.append("<p><strong>Fecha de Nacimiento:</strong> ").append(seguidor.getfechaNacimiento()).append("</p>");
            html.append("<p><strong>Email:</strong> ").append(seguidor.getEmail()).append("</p>");
            html.append("</div>");
        }
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
    

}

package com.recursos;
import java.util.Arrays;
import java.util.Date;
import com.datos.ListaUsuarios;
import com.datos.ListaVinos;
import com.datos.Sistema;
import com.datos.Usuario;
import com.datos.Vino;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;


@Path("/vinos")
public class VinoRecurso {
    
    @GET
    @Path("/{nombre_vino}")
    @Produces(MediaType.TEXT_HTML)
    public String getVino(@PathParam("nombre") String nombre) {
        Vino vino = ListaVinos.getVino(nombre);
        if(vino == null) {
            return "<html><body><h2>Vino no encontrado</h2></body></html>";
        } else {
            return "<html><body>" +
                   "<h2>Detalles del Vino</h2>" +
                   "<p><strong>Nombre:</strong> " + vino.getNombre() + "</p>" +
                   "<p><strong>Bodega:</strong> " + vino.getBodega() + "</p>" +
                   "<p><strong>Agnada:</strong> " + vino.getAñada() + "</p>" +
                   "<p><strong>Denominacion:</strong> " + vino.getDenominacion() + "</p>" +
                   "<p><strong>Tipo:</strong> " + vino.getTipo() + "</p>" +
                   "<p><strong>Tipos de Uva:</strong> " + Arrays.toString(vino.getTiposUva()) + "</p>" +
                   "</body></html>";
        }
    }

//    /*Funcion para añadir vino */
//    @POST
//    @Path("/{id_usuario}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//        public Response addVino(@FormParam("id_usuario") int idUsuario,@FormParam("nombre") String nombre, @FormParam("bodega") String bodega, @FormParam("añada") int añada, @FormParam("denominacion") String denominacion, @FormParam("tipo") String tipo, @FormParam("tiposUva") String[] tiposUva, @FormParam("puntuacion") int puntuacion){
//        if(ListaUsuarios.getUsuario(idUsuario) == null){/*Compruebo si existe el usuario */
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        if(!ListaUsuarios.getUsuario(idUsuario).existeVino(nombre)){/*Compruebo si ya existe le vino */
//            Vino vino = new Vino(nombre, bodega, añada, denominacion, tipo, tiposUva,puntuacion);/*si no esxite lo creo  */
//            vino.setId(Sistema.getId_vino());
//            ListaUsuarios.getUsuario(idUsuario).addVino(vino);/*añado el vino a la lista de vinos de ese usuario */
//            // Devolvemos una respuesta con código HTTP 201 Created
//            return Response.status(Response.Status.CREATED).build();
//        }else{
//            // En este caso, podrías devolver un código de estado HTTP 409 Conflict para indicar que la operación no pudo completarse debido a un conflicto con el estado actual del recurso.
//            return Response.status(Response.Status.CONFLICT).build();
//        }
//    }

//    @PUT/*Funcion para modificar el vino de un usuario. */
//    @Path("/{id_usuario}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response updateVino(@FormParam("id") int idUsuario,@FormParam("nombre") String nombre, @FormParam("bodega") String bodega, @FormParam("añada") int añada, @FormParam("denominacion") String denominacion, @FormParam("tipo") String tipo, @FormParam("tiposUva") String[] tiposUva) {
//        if(ListaUsuarios.getUsuario(idUsuario) == null){/*Compruebo si existe el usuario */
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        if(ListaUsuarios.getUsuario(idUsuario).existeVino(nombre)){
//            ListaUsuarios.getUsuario(idUsuario).getVino(nombre).setBodega(bodega);
//            ListaUsuarios.getUsuario(idUsuario).getVino(nombre).setAñada(añada);
//            ListaUsuarios.getUsuario(idUsuario).getVino(nombre).setDenominacion(denominacion);
//            ListaUsuarios.getUsuario(idUsuario).getVino(nombre).setTipo(tipo);
//            ListaUsuarios.getUsuario(idUsuario).getVino(nombre).setTiposUva(tiposUva);
//
//            // Devolvemos una respuesta con código HTTP 200 OK
//            return Response.status(Response.Status.OK).build();
//        } else {
//            // Devolvemos una respuesta con código HTTP 404 Not Found
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

//    @DELETE
//    @Path("/{id_usuario}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response deleteVino(@FormParam("id_usuario") int idUsuario,@FormParam("nombre") String nombre) {
//        if(ListaUsuarios.getUsuario(idUsuario) == null){/*Compruebo si existe el usuario */
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        if(ListaUsuarios.getUsuario(idUsuario).existeVino(nombre)){
//            ListaUsuarios.getUsuario(idUsuario).deleteVino(nombre);
//            // Devolvemos una respuesta con código HTTP 200 OK
//            return Response.status(Response.Status.OK).build();
//        } else {
//            // Devolvemos una respuesta con código HTTP 404 Not Found
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getVinos() {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head><title>Vinos</title></head>");
        html.append("<body>");
        html.append("<h1>Lista de Vinos</h1>");
        for (Vino vino : ListaVinos.getVinos()) {
            html.append("<p>------------------------------------------------------------------------</p>");
            html.append("<div>");
            html.append("<p><strong>Nombre:</strong> ").append(vino.getNombre()).append("</p>");
            html.append("<p><strong>Bodega:</strong> ").append(vino.getBodega()).append("</p>");
            html.append("<p><strong>Agnada:</strong> ").append(vino.getAñada()).append("</p>");
            html.append("<p><strong>Denominacion:</strong> ").append(vino.getDenominacion()).append("</p>");
            html.append("<p><strong>Tipo:</strong> ").append(vino.getTipo()).append("</p>");
            html.append("<p><strong>Tipos de Uva:</strong> ").append(Arrays.toString(vino.getTiposUva())).append("</p>");
            html.append("</div>");
        }
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    
}

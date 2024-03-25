package com.recursos;
import java.util.Arrays;
import java.util.Date;
import com.datos.ListaUsuarios;
import com.datos.ListaVinos;
import com.datos.Usuario;
import com.datos.Vino;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;


@Path("/vinos")
public class VinoRecurso {
    
    @GET
    @Path("/{nombre}")
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


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public Response addVino(@FormParam("nombre") String nombre, @FormParam("bodega") String bodega, @FormParam("añada") int añada, @FormParam("denominacion") String denominacion, @FormParam("tipo") String tipo, @FormParam("tiposUva") String[] tiposUva) {
        if(!ListaVinos.existeVino(nombre)){
            Vino vino = new Vino(nombre, bodega, añada, denominacion, tipo, tiposUva);
            ListaVinos.addVino(vino);
            // Devolvemos una respuesta con código HTTP 201 Created
            return Response.status(Response.Status.CREATED).build();
        }else{
            System.out.println("El usuario ya existe");
            // En este caso, podrías devolver un código de estado HTTP 409 Conflict para indicar que la operación no pudo completarse debido a un conflicto con el estado actual del recurso.
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateVino(@FormParam("nombre") String nombre, @FormParam("bodega") String bodega, @FormParam("añada") int añada, @FormParam("denominacion") String denominacion, @FormParam("tipo") String tipo, @FormParam("tiposUva") String[] tiposUva) {
        Vino vino = ListaVinos.getVino(nombre);
        if(vino != null){
            vino.setBodega(vino.getBodega());
            vino.setAñada(vino.getAñada());
            vino.setDenominacion(vino.getDenominacion());
            vino.setTipo(vino.getTipo());
            vino.setTiposUva(vino.getTiposUva());
            // Devolvemos una respuesta con código HTTP 200 OK
            return Response.status(Response.Status.OK).build();
        } else {
            // Devolvemos una respuesta con código HTTP 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteVino(@FormParam("nombre") String nombre) {
        if(ListaVinos.existeVino(nombre)){
            ListaVinos.deleteVino(nombre);
            // Devolvemos una respuesta con código HTTP 200 OK
            return Response.status(Response.Status.OK).build();
        } else {
            // Devolvemos una respuesta con código HTTP 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

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

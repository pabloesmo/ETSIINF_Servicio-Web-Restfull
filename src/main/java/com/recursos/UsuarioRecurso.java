package com.recursos;
import com.datos.ListaUsuarios;
import com.datos.Usuario;
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

@Path("/usuarios")
public class UsuarioRecurso {

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
            ListaUsuarios.addUsuario(usuario);
            // Devolvemos una respuesta con código HTTP 201 Created
            return Response.status(Response.Status.CREATED).build();
        }else{
            System.out.println("El usuario ya existe");
            // En este caso, podrías devolver un código de estado HTTP 409 Conflict para indicar que la operación no pudo completarse debido a un conflicto con el estado actual del recurso.
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    //FUNCIONA!!
    @PUT
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
            html.append("<p><strong>Nombre:</strong> ").append(usuario.getNombre()).append("</p>");
            html.append("<p><strong>Fecha de Nacimiento:</strong> ").append(usuario.getfechaNacimiento()).append("</p>");
            html.append("<p><strong>Email:</strong> ").append(usuario.getEmail()).append("</p>");
            html.append("</div>");
        }
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}
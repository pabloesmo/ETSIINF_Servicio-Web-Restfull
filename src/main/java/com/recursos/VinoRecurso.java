package com.recursos;
import java.util.Date;
import com.datos.ListaUsuarios;
import com.datos.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;


@Path("/vinos")
public class VinoRecurso {
    
    // @GET
    // @Path("/{nombre}")
    // @Produces(MediaType.TEXT_PLAIN)
    // public Vino getVino(@PathParam("nombre") String nombre) {
    //     Vino vino = ListaVinos.getVino(nombre);
    //     if(vino == null){
    //         return null;
    //     } else {
    //         return vino;
    //     }
    // }
}

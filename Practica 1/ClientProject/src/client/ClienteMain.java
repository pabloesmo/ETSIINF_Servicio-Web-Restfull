package client;
import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class ClienteMain {

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/SOSPractica/").build();
	}
	
	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
	
		//PRUEBA DE GET USUARIOS
		Response rGetUsuarios = target.path("api").path("usuarios").
				request().accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		System.out.println("Estado: " + rGetUsuarios.getStatus());
		String valorUs = rGetUsuarios.readEntity(String.class);
		System.out.println("Entidad: " + valorUs);
		
		
		//PRUEBA DE GET USUARIO ESPECIFICO
		Response rGetUsuarioEspec = target.path("api").path("usuarios").path("Pedro").
				request().accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		System.out.println("Estado: " + rGetUsuarioEspec.getStatus());
		String valor = rGetUsuarioEspec.readEntity(String.class);
		System.out.println("Entidad: " + valor);		
		
		
		//PRUEBA DE POST USUARIO
		Usuario u = new Usuario("Pepito Grillo","1990-04-31","pepito.grillo@example.com");
		Response rPost = target.path("api").path("usuarios").
				request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(u));
		rPost.close();
		
		
		//PRUEBA DE PUT USUARIO
		Usuario u1 = new Usuario("Pepito Abeja","1990-04-31","pepito.grillo@example.com");
		Response rPut = target.path("api").path("usuarios").path("8").
				request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.json(u1));
		rPut.close();
		
		
		//PRUEBA DE DELETE USUARIO
		String idUsuario = "8";
		Response rDelete = target.path("api").path("usuarios").path(idUsuario)
				.request()
				.delete();
		rDelete.close();
	
		
		//PRUEBA DE GET VINOS
		String idUsuario1 = "2";
		Response rGetVinos = target.path("api").path("usuarios").path(idUsuario1).path("vinos")
				.request().accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		System.out.println("Estado: " + rGetVinos.getStatus());
		String valorVinos = rGetVinos.readEntity(String.class);
		System.out.println("Entidad: " + valorVinos);
		
		
		//PRUEBA DE POST VINOS
		String cuerpo = "{\"puntuacion\": 8.5}";
		Response rPostVinos = target.path("api").path("usuarios/1").path("vinos/2").
				request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(cuerpo));
		rPostVinos.close();
		
		
		//PRUEBA DE PUT VINOS
		String cuerpo2 = "{\"puntuacion\": 10}";
		Response rPutVinos = target.path("api").path("usuarios/1").path("vinos/2").
				request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.json(cuerpo2));
		rPutVinos.close();
		
		
		//PRUEBA DE DELETE VINOS
		String idUsuarioD = "1";
		String idVino = "2";
		Response rDeleteVinos = target.path("api").path("usuarios").path(idUsuarioD).path("vinos").path(idVino)
				.request()
				.delete();
		rDeleteVinos.close();
		
		
		//PRUEBA DE GET SEGUIDORES
		String idUsuario2 = "1";
		Response rGetSeguidores = target.path("api").path("usuarios").path(idUsuario2).path("seguir")
				.request().accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		System.out.println("Estado: " + rGetSeguidores.getStatus());
		String valorSeguidores1 = rGetSeguidores.readEntity(String.class);
		System.out.println("Entidad: " + valorSeguidores1);
		
		
		//PRUEBA DE POST SEGUIDORES
		String idSeguido = "1";
		String idSeguidor = "2";
		Response rPostSeguidor = target.path("api").path("usuarios").path(idSeguido)
				.path("seguir").path(idSeguidor).
				request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(""));
		rPostSeguidor.close();
		
		
		//PRUEBAS DE DELETE SEGUIDORES
		//@Path("/{seguido_id}/no_seguir/{seguidor_id}")
		String idSeguido2 = "1";
		String idSeguidor2 = "2";
		Response rDeleteSeguidor = target.path("api").path("usuarios").path(idSeguido2).path("no_seguir").path(idSeguidor2)
				.request()
				.delete();
		rDeleteSeguidor.close();
		
		
		//PRUEBAS DE GET VINOS SEGUIDORES
		String idUsuario3 = "1";
		String idSeguidor3 = "2";
		Response rGetVinosSeguidor = target.path("api").path("usuarios").path(idUsuario3)
				.path("seguidores").path(idSeguidor3).path("vinos")
				.request().accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		System.out.println("Estado: " + rGetVinosSeguidor.getStatus());
		String valorSeguidores = rGetVinosSeguidor.readEntity(String.class);
		System.out.println("Entidad: " + valorSeguidores);
		
	}
}
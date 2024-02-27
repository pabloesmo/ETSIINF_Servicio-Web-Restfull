import java.net.Socket;
import java.io.IOException;
public class conexionServidor {
    private Socket socket;

    public void conectar(String direccion, int puerto) {
        try {
            socket = new Socket(direccion, puerto);
            System.out.println("Conexión exitosa con el servidor");
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            socket.close();
            System.out.println("Desconexión exitosa del servidor");
        } catch (IOException e) {
            System.out.println("Error al desconectar del servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        conexionServidor conexion = new conexionServidor();
        conexion.conectar("localhost", 8080);
        conexion.desconectar();
    }
}
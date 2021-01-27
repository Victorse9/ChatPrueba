package interfaz;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat implements Runnable{
	
	public static void main (String [] args) {
		ServidorChat server = new ServidorChat();
		Thread hilo = new Thread(server);
		
		hilo.start();
	}

	@Override
	public void run() {
		ServerSocket socketEscucha = null;
		Socket conexion=null;
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			socketEscucha = new ServerSocket(5000);
			System.out.println("Arrancado el servidor");
			while (true) {
				try {
					conexion = socketEscucha.accept();
					System.out.println("Conexion recibida!");
					System.out.println("Dirección IP: " + conexion.getInetAddress());
					in = new DataInputStream(conexion.getInputStream());
					out = new DataOutputStream(conexion.getOutputStream());
					String mensaje = in.readUTF();
					System.out.println(mensaje);
					out.writeUTF("EL SERVIDOR RECIBIO EL MENSAJE");
					System.out.println("Cliente desconectado");
				} catch (IOException e) {
					System.out.println("Error al aceptar conexion "+e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					if (null != out) {
						out.close();
					}
					if (null != in) {
						in.close();
					}
					if (null != conexion) {
						conexion.close();
					}
				}
			}
		} catch (IOException e) {
			System.out.println("No se pudo poner un socket a escuchar "+e.getMessage());
			e.printStackTrace();

		} finally {
			if (null != socketEscucha) {
				try {
					socketEscucha.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}

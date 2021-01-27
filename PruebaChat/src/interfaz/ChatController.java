package interfaz;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChatController extends Application implements Runnable{
	@FXML
	TextField txtMensaje;
	@FXML
	TextArea txtArea;
	

	//Constructor
	public void ClienteController() {
		Thread hilo = new Thread();
		hilo.start();
	}
	
	public void enviaMensaje(ActionEvent ev) throws IOException {
		String mensaje=txtMensaje.getText();
		txtArea.setText(txtArea.getText()+"\n"+mensaje);
		txtMensaje.setText("");
		System.out.println("prueba");

		final String ip = "192.168.43.30";
		Socket socket = null;
		DataOutputStream out=null;
		DataInputStream in=null;
		ChatController chat = new ChatController();
		try {
			socket = new Socket(ip, 5000);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			out.writeUTF(mensaje);
			mensaje = in.readUTF();
			System.out.println(mensaje);
		}catch(IOException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
			if (null != socket) {
				socket.close();
			}
		}
	}	
		
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Chat.fxml"));
			Scene scene = new Scene(root,650,690);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("img/logoxuchat.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void run() {
		
	}
}

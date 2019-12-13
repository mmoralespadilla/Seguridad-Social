package pruebarecibo;

import java.awt.Dimension;
import java.awt.Panel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Main {
	private static JEditorPane editor = new JEditorPane();
	private static JFrame ventana = new JFrame();

	public static void main(String[] args) throws MessagingException, IOException {

		Panel panel = new Panel();
		String user = "iamsegsoctrustme@gmail.com";
		String pass = "segsoc123";
		ConexionCorreo conmail = new ConexionCorreo(user, pass);
		Folder folder = conmail.conectar();
		ListadoMensajes listmessages = new ListadoMensajes(folder);
		listmessages.listarMensajes();
		JEditorPane panelMensaje = new JEditorPane();
		JTextPane panelTexto = new JTextPane();
		MuestraMensaje verMensaje = new MuestraMensaje(folder, 8/*Mete por parámetro la posición del mensaje que quieres abrir (empieza en el 1)*/);
		verMensaje.mostrarMensaje(panelMensaje, panelTexto);
		//panelMensaje.setText(verMensaje.mostrarMensaje().toString());
		
		JScrollPane scrollPane = new JScrollPane(panelTexto);
		ventana.add(scrollPane);
		ventana.pack();

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
	}
}

//Preguntar a Enrique:
/*
 * Las recuperaciones de PSP y ACDA son los 2 el miércoles de llegar de vavaciones, ¿no?
 * En caso afirmativo, ¿daría tiempo a ambos?
 * Con las recuperaciones se recuperaba tanto los trabajos como la prueba de aptitud, ¿no?
 * En la recu de ADCA no entran las BD de objetos , ¿no?
 */

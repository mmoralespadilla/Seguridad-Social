package recibocorreo;

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
		panelMensaje.setContentType("text/html");
		MuestraMensaje verMensaje = new MuestraMensaje(folder, 58/*Mete por parámetro la posición del mensaje que quieres abrir (empieza en el 1)*/);
		panelMensaje.setText(verMensaje.mostrarMensaje());
		panelMensaje.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(panelMensaje);
		ventana.add(scrollPane);
		ventana.pack();

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
	}
}

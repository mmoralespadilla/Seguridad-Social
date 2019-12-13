package pruebarecibo;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;

public class MuestraMensaje {

	private static int posMensaje;
	private static Folder folder;

	public MuestraMensaje(Folder folder, int posMensaje) {
		this.folder = folder;
		this.posMensaje = posMensaje;
	}

	public static void mostrarMensaje(JEditorPane panelMensaje, JTextPane panelTexto) {
		panelMensaje.setContentType("text/html");
		panelMensaje.setEditable(false);
		StringBuilder txtMensaje = new StringBuilder();
		try {
			Message mensaje = folder.getMessage(posMensaje);
			if (mensaje.isMimeType("multipart/*")) {
				// Obtenemos el contenido, que es de tipo MultiPart.
				Multipart multi = (Multipart) mensaje.getContent();

				// Extraemos cada una de las partes.
				for (int j = 1; j < multi.getCount(); j++) {
					Part unaParte = multi.getBodyPart(j);
					if (unaParte.isMimeType("image/*")) {
						ImageIcon icono = new ImageIcon(ImageIO.read(unaParte.getInputStream()));
						JLabel label = new JLabel(icono);
						panelTexto.insertComponent(label);
					} else {
						txtMensaje.append(unaParte.getContent().toString());
					}
				}
				panelMensaje.setText(txtMensaje.toString());
			}
			panelTexto.insertComponent(panelMensaje);
		} catch (MessagingException me) {
			System.err.println(me.getMessage());
		} catch (IOException ie) {
			txtMensaje.append("<html>No se ha podido cargar el contenido :(</html>");
			panelMensaje.setText(txtMensaje.toString());
		}
	}
}

package pruebarecibo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JEditorPane;

public class MuestraMensaje {

	private int posMensaje;
	private Folder folder;

	public MuestraMensaje(Folder folder, int posMensaje) {
		this.folder = folder;
		this.posMensaje = posMensaje;
	}

	public StringBuilder mostrarMensaje() {
		StringBuilder txtMensaje = new StringBuilder();
		try {
			Message mensaje = folder.getMessage(posMensaje);
			if (mensaje.isMimeType("multipart/*")) {

				// Obtenemos el contenido, que es de tipo MultiPart.
				Multipart multi = (Multipart) mensaje.getContent();

				// Extraemos cada una de las partes.
				for (int j = 1; j < multi.getCount(); j++) {
					Part unaParte = multi.getBodyPart(j);
					//if (unaParte.isMimeType("text/*")) {
						txtMensaje.append(unaParte.getContent().toString());
					//} 
					//else{
						MimeBodyPart filePart = (MimeBodyPart) multi.getBodyPart(j);
						filePart.saveFile(
								new File(System.getProperty("user.home") + "\\Downloads\\" + filePart.getFileName()));
						txtMensaje.append("Se ha guardado fichero en: " + System.getProperty("user.home")
								+ "\\Downloads\\" + unaParte.getFileName() + "\n");
						System.out.println(txtMensaje);
					}
				//}
				return txtMensaje;
			}
		} catch (MessagingException me) {
			System.err.println(me.getMessage());
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			txtMensaje.append("<html>No se ha podido cargar el contenido :(</html>");
			return txtMensaje;
		}
		return null;
	}
}

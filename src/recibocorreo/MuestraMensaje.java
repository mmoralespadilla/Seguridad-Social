package recibocorreo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.JEditorPane;

public class MuestraMensaje {

	private int posMensaje;
	private Folder folder;

	public MuestraMensaje(Folder folder, int posMensaje) {
		this.folder = folder;
		this.posMensaje = posMensaje;
	}

	public String mostrarMensaje(){
		StringBuilder txtMensaje = new StringBuilder();
		try {
			Message mensaje = folder.getMessage(posMensaje);
			if (mensaje.isMimeType("multipart/*")) {

				// Obtenemos el contenido, que es de tipo MultiPart.
				Multipart multi = (Multipart) mensaje.getContent();

				// Extraemos cada una de las partes.
				for (int j = 0; j < multi.getCount(); j++) {
					BodyPart bodyPart = multi.getBodyPart(j);
					if (bodyPart.isMimeType("text/html")) {
						txtMensaje.append(bodyPart.getContent().toString());
						System.out.println(bodyPart.toString());
					} else if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equals(BodyPart.ATTACHMENT)){
						MimeBodyPart filePart = (MimeBodyPart) multi.getBodyPart(j);
						//if(!filePart.getFileName().equals("unnamed.jpg")){
						filePart.saveFile(
								new File(System.getProperty("user.home") + "\\Downloads\\" + filePart.getFileName()));
						txtMensaje.append("<p><em>Se ha guardado fichero en: " + System.getProperty("user.home")
								+ "\\Downloads\\" + bodyPart.getFileName() + "</em></p>");
						/*} else {
							txtMensaje.append("<img src=\"https://www.vectorlogo.es/wp-content/uploads/2018/01/logo-vector-instituto-nacional-de-la-seguridad-social.jpg\"/>");
							System.out.println(System.getProperty("user.home") + "/Downloads/SegSoc.png");
						}*/
					}
				}
				return txtMensaje.toString();
			}
		} catch (MessagingException me) {
			System.err.println(me.getMessage());
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			txtMensaje.append("<html>No se ha podido cargar el contenido :(</html>");
			return txtMensaje.toString();
		}
		return null;
	}
}

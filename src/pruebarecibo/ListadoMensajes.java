package pruebarecibo;

import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public class ListadoMensajes {
	private Folder folder;
	private ArrayList<String> remitentes = new ArrayList<>();
	private ArrayList<String> asuntos = new ArrayList<>();

	public ListadoMensajes(Folder folder) {
		this.folder = folder;
	}

	public void listarMensajes() {
		try {
			Message[] mensajes = folder.getMessages();
			for (int i = 0; i < mensajes.length; i++) {
				remitentes.add(mensajes[i].getFrom()[0].toString());
				asuntos.add(mensajes[i].getSubject());
			}
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
		}
		for(String remitente : remitentes)
			System.out.println(remitente);
		int contador = 1;
		for(String asunto : asuntos) {
			System.out.println(contador++ + ": " + asunto);
		}
	}
}

package pruebarecibo;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class ConexionCorreo {

	private String user;
	private String pass;
	
	public ConexionCorreo(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	public Folder conectar() {
		try {
			Properties prop = new Properties();

			// Hay que usar SSL
			prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			prop.setProperty("mail.pop3.socketFactory.fallback", "false");

			// Puerto 995 para conectarse.
			prop.setProperty("mail.pop3.port", "995");
			prop.setProperty("mail.pop3.socketFactory.port", "995");

			Session sesion = Session.getInstance(prop);

			Store store = sesion.getStore("pop3");

			store.connect("pop.gmail.com", user, pass);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			return folder;
		} catch (NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
}

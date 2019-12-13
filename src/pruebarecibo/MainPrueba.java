package pruebarecibo;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class MainPrueba {

	public static void main(String[] args) throws MessagingException, IOException {
		JFrame ventana = new JFrame();
		JEditorPane editor = new JEditorPane();
		// Marcamos el editor para que use HTML
		editor.setContentType("text/html");
		
		Properties prop = new Properties();

		// Deshabilitamos TLS
		prop.setProperty("mail.pop3.starttls.enable", "false");

		// Hay que usar SSL
		prop.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
		prop.setProperty("mail.pop3.socketFactory.fallback", "false");

		// Puerto 995 para conectarse.
		prop.setProperty("mail.pop3.port","995");
		prop.setProperty("mail.pop3.socketFactory.port", "995");

		Session sesion = Session.getInstance(prop);
		
		Store store = sesion.getStore("pop3");
		store.connect("pop.gmail.com","iamsegsoctrustme@gmail.com","segsoc123");
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		
		Message [] mensajes = folder.getMessages();
		
		for (int i=0;i<mensajes.length;i++)
		{
		   System.out.println("From:"+mensajes[i].getFrom()[0].toString());
		   System.out.println("Subject:"+mensajes[i].getSubject());
		
		
		if (mensajes[i].isMimeType("multipart/*"))
		{
		   // Obtenemos el contenido, que es de tipo MultiPart.
		   Multipart multi = (Multipart)mensajes[i].getContent();

		   // Extraemos cada una de las partes.
		   for (int j=0;j<multi.getCount();j++)
		   {
		      Part unaParte = multi.getBodyPart(j);
		      editor.setText(unaParte.getContent().toString());
		   }
		}
	
		}
		ventana.pack();
		ventana.add(editor);
		ventana.pack();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
	}

}

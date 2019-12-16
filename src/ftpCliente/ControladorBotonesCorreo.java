package ftpCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Folder;
import javax.mail.Message;

import recibocorreo.MenuCorreo;
import recibocorreo.ListadoMensajes;
import recibocorreo.MuestraMensaje;

public class ControladorBotonesCorreo implements ActionListener{

	ModeloTextoInterfaz textos;
	
	public ControladorBotonesCorreo() {
		super();
		textos = new ModeloTextoInterfaz();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String boton = e.getActionCommand().toString();
		if(boton.equals(textos.getTituloAccionesRefrescar())) {
			String user = "iamsegsoctrustme@gmail.com";
			String pass = "segsoc123";
			MenuCorreo menuMail = new MenuCorreo(user, pass);
			Folder folder = menuMail.conectar();
			Message[] mensajes = menuMail.listarMensajes();
		}
	}

}

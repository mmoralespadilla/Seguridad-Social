package ftpCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			
		}
	}

}

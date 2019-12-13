package ftpCliente;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ControladorBotonesFtp implements ActionListener {
	private PrimerFtp ftp;

	public ControladorBotonesFtp(PrimerFtp ftp) {
		super();
		this.ftp = ftp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nomFichero = "";
		String ruta = "";
		String archivo;
		Object boton = e.getActionCommand();
		System.out.println(boton);
		switch (boton.toString()) {
		case "Subir fichero":
			System.out.println("hey");
			JFileChooser cargar = new JFileChooser();
			cargar.showOpenDialog(null); // panel indicando que archivo cargaremos
			cargar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			try {
				if (ftp.subir(cargar.getSelectedFile().getAbsolutePath(), cargar.getSelectedFile().getName())) {
					JOptionPane.showMessageDialog(null, "Archivo subido con �xito");
				} else {
					JOptionPane.showMessageDialog(null, "Error al subir el archivo");
				}
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;

		case "Descargar fichero":
			JFileChooser elegir = new JFileChooser();
			try {
				ruta = ftp.getCliente().printWorkingDirectory();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			archivo = (String)InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
			ftp.descargar(ruta, archivo, elegir);
			break;

		case "Crear fichero":
			nomFichero = JOptionPane.showInputDialog("Nombre del fichero");
			ftp.crearFichero(nomFichero);
			break;
		case "Eliminar fichero":
			ruta = "";
			try {
				ruta = ftp.getCliente().printWorkingDirectory();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			archivo = (String)InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
			ftp.borrarCarpeta(ruta+archivo);
			break;
		
		case "Crear carpeta":
			nomFichero = JOptionPane.showInputDialog("Nombre de la carpeta");
			if(nomFichero.length() != 0) {
				ftp.crearCarpeta(nomFichero);
			}else {
				
			}
			break;
		case "Eliminar carpeta":
			ruta = "";
			try {
				ruta = ftp.getCliente().printWorkingDirectory();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			archivo = (String)InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
			ftp.borrarCarpeta(ruta+archivo);
			break;
		}
		InterfazFtp.recargarTabla();
	}

}

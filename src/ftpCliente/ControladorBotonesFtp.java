package ftpCliente;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ControladorBotonesFtp implements ActionListener {
	private PrimerFtp ftp;
	private ModeloTextoInterfaz textos;

	public ControladorBotonesFtp(PrimerFtp ftp) {
		super();
		this.ftp = ftp;
		textos = new ModeloTextoInterfaz();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Comentario para subir
		String nomFichero = "";
		String ruta = "";
		String archivo;
		String boton = e.getActionCommand();
		if(boton.equals(textos.getTituloSubirFichero())){
			try {
				System.out.println("hey");
				JFileChooser cargar = new JFileChooser();
				cargar.showOpenDialog(null); // panel indicando que archivo cargaremos
				cargar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (ftp.subir(cargar.getSelectedFile().getAbsolutePath(), cargar.getSelectedFile().getName())) {
					JOptionPane.showMessageDialog(null, "Archivo subido con ï¿½xito");
				} else {
					JOptionPane.showMessageDialog(null, "Error al subir el archivo");
				}
			} catch (NullPointerException e1) {
				System.out.println("No has seleccionado un fichero");
			}
		}
		else if(boton.equals(textos.getTituloDescargarFichero())) {
			try {
				JFileChooser elegir = new JFileChooser();
				try {
					ruta = ftp.getCliente().printWorkingDirectory();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
				ftp.descargar(ruta, archivo, elegir);
			} catch (NullPointerException e1) {
				System.out.println("No has seleccionado un directorio");
			} catch (ArrayIndexOutOfBoundsException a1) {
				System.out.println("No has seleccionado un fichero de la tabla");
			} 
		}
		else if (boton.equals(textos.getTituloCrearFichero())) {
				try {
					try {
						nomFichero = JOptionPane.showInputDialog("Nombre del fichero");
						ftp.crearFichero(nomFichero);
					} catch (NullPointerException e2) {
						System.out.println("No has seleccionado un fichero");
					}
				} catch (NullPointerException e1) {
					System.out.println("Acción cancelada");
				} 
			}
		else if (boton.equals(textos.getTituloEliminar())) {
				try {
					archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
					ftp.borrarCarpeta(archivo);
				} catch (ArrayIndexOutOfBoundsException e1) {
					System.out.println("Selecciona un elemento de la tabla");
				} 
			}
		else if (boton.equals(textos.getTituloCrearCarpeta())) {
				try {
					nomFichero = JOptionPane.showInputDialog("Nombre de la carpeta");
					if (nomFichero.length() != 0) {
						ftp.crearCarpeta(nomFichero);
					} else {

					}
				} catch (NullPointerException e1) {
					System.out.println("Acción cancelada");
				} 
			}
		else if(boton.equals(textos.getTituloCambiarNombre())) {
			archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
			nomFichero = JOptionPane.showInputDialog("Nombre de la carpeta");
			ftp.renombrar(archivo, nomFichero);
		}else if(boton.equals(textos.getTituloCambiarUsuario())) {
			
			
		}
		InterfazFtp.recargarTabla();
	}
}
		
	

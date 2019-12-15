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
		switch (boton.toString()) {
		case "Subir fichero":
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
			break;

		case "Descargar fichero":
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
			break;

		case "Crear fichero":
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
			break;
		case "Eliminar fichero":
			try {
				archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
				ftp.borrarCarpeta(archivo);
			} catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Selecciona un fichero de la tabla");
			}
			break;

		case "Crear carpeta":
			try {
				nomFichero = JOptionPane.showInputDialog("Nombre de la carpeta");
				if (nomFichero.length() != 0) {
					ftp.crearCarpeta(nomFichero);
				} else {

				}
			} catch (NullPointerException e1) {
				System.out.println("Acción cancelada");
			}
			break;
		case "Eliminar carpeta":
			try {
				ruta = "";
				archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
				ftp.borrarCarpeta(archivo);
				break;
			} catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Selecciona una carpeta de la tabla");
			}
		}
		InterfazFtp.recargarTabla();
	}

}

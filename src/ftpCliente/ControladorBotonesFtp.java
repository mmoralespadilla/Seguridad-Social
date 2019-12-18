package ftpCliente;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ControladorBotonesFtp implements ActionListener {
	private PrimerFtp ftp;
	private ModeloTextoInterfaz textos;
	private JLabel lblRuta;

	
	
	public ControladorBotonesFtp(PrimerFtp ftp) {
		super();
		this.ftp = ftp;
		textos = new ModeloTextoInterfaz();
	}

	public ControladorBotonesFtp(PrimerFtp ftp, JLabel lblRuta) {
		super();
		this.ftp = ftp;
		this.lblRuta = lblRuta;
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
				int numeroFicheros = 0;
				JFileChooser cargar = new JFileChooser();
				cargar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				cargar.setMultiSelectionEnabled(true);
				cargar.showOpenDialog(null); // panel indicando que archivo cargaremos
				
				File[] ficheros = cargar.getSelectedFiles();
				for (int i = 0; i < ficheros.length; i++) {
					if (ftp.subir(ficheros[i].getAbsolutePath(), ficheros[i].getName())) {
						numeroFicheros++;
					}
				}
				if(numeroFicheros == 1) {
					JOptionPane.showMessageDialog(null, "Archivo subido con ï¿½xito");
				}else if(numeroFicheros >1) {
					JOptionPane.showMessageDialog(null, "Archivos subidos con ï¿½xito");
				}else {
					JOptionPane.showMessageDialog(null, "Error al subir archivo");
				}
			} catch (NullPointerException e1) {
				System.out.println("No has seleccionado un fichero");
			}
		}
		else if(boton.equals(textos.getTituloDescargarFichero())) {
			try {
				JFileChooser elegir = new JFileChooser();
				archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
				ftp.descargar(archivo, elegir);
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
						System.out.println("No has introducido nombre del fichero");
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
			nomFichero = JOptionPane.showInputDialog("Nombre nuevo");
			ftp.renombrar(archivo, nomFichero);
		}else if(boton.equals(textos.getTituloCorreoAbrir())) {
			
			InterfazEmail email = new InterfazEmail(ftp.getUser(),ftp.getPass());
			email.setModal(true);
			email.setEnabled(true);
		}else if(boton.equals(textos.getTituloBotonAtras())) {
			ruta = "";
			System.out.println(ftp.getPosicion());
			if (ftp.getPosicion() >= 1) {
				ftp.decrementarPosicion();
				try {
					ftp.getCliente().changeWorkingDirectory(ftp.getRutas().get(ftp.getPosicion()));
					ruta = ftp.getCliente().printWorkingDirectory();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblRuta.setText("Ruta: " + ruta);
				InterfazFtp.recargarTabla();
			}
		}
		InterfazFtp.recargarTabla();
	}
}
		
	

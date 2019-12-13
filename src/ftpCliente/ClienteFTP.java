package ftpCliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ClienteFTP {
	private static FTPClient cliente = new FTPClient();
	private static String user = "user", pass = "";
	private static String host;
	private static String email;
	private static FTPFile[] ficheros;

	public ClienteFTP(String host, String usuario, String pass, String email) {
		this.host = host;
		this.user = usuario;
		this.pass = pass;
		this.email = email;
	}

	public static void init() throws SocketException, IOException {
		cliente.connect(host, 21);
		cliente.login(user, pass);
		ficheros = cliente.listFiles();
		cliente.enterLocalPassiveMode();

		if (subir("C:\\Users\\Miguel\\Desktop\\PDFS\\frutasyverduras.sql", "ejemplo.sql")) {
			System.out.println("Archivo subido.");
		} else {
			System.out.println("ERROR AL SUBIR El archivo.");
		}
		reescribir("ejemplo.sql", "C:\\Users\\Miguel\\Downloads\\mascotas.sql");
		crearCarpeta("carpetaPrueba");
		borrarCarpeta("ejemplo.sql");
		borrarCarpeta("carpetaPrueba");
		crearFichero("StinkyPoops.txt");
	}

	private static boolean subir(String archivo, String nombre) {
		BufferedInputStream in;
		boolean subido = false;
		try {
			in = new BufferedInputStream(new FileInputStream(archivo));
			if (cliente.storeFile(nombre, in)) {
				subido = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return subido;
	}

	private static void reescribir(String nombreFichero, String nuevoArchivo) {
		try {
			for (FTPFile file : ficheros) {
				if (file.getName().equals(nombreFichero) && file.isFile()) {
					cliente.removeDirectory(cliente.printWorkingDirectory() + file.getName());
					if (subir(nuevoArchivo, nombreFichero)) {
						System.out.println("Fichero: '" + file.getName() + "' sobreescrito.");
					}
				} else {
					System.out.println("Es un directorio.");
				}
			}
		} catch (NullPointerException e) {
			System.out.println("El fichero no existe.");
		} catch (IOException e) {
			System.out.println("ERROR E/S");
		}
	}

	private static void crearCarpeta(String nombreCarpeta) {
		try {
			if (cliente.makeDirectory(nombreCarpeta)) {
				System.out.println("Carpeta creada");
			} else {
				System.out.println("ERROR AL CREAR CARPETA.");
			}
		} catch (IOException e) {
			System.out.println("ERROR E/S");
		}
	}

	private static void borrarCarpeta(String nombreCarpeta) {
		try {
			FTPFile f = cliente.mlistFile(nombreCarpeta);
			if (f.isDirectory()) {
				if (cliente.removeDirectory(nombreCarpeta)) {
					System.out.println("Carpeta borrada.");
				} else {
					System.out.println("Carpeta inexistente.");
				}
			} else if (f.isFile()) {
				if (cliente.deleteFile(nombreCarpeta)) {
					System.out.println("fichero borrada.");
				} else {
					System.out.println("fichero inexistente.");
				}
			}
		} catch (IOException e) {
			System.out.println("ERROR E/S");
			e.printStackTrace();
		}
	}

	private static void renombrar(String nombreAntiguo, String nombreNuevo) {
		try {
			cliente.rename(nombreAntiguo, nombreNuevo);
		} catch (IOException e) {
			System.out.println("ERROR E/S");
			e.printStackTrace();
		}

	}

	public static void crearFichero(String fichero) {
		File fi = new File(fichero);
		System.out.println(fi.getAbsolutePath());
		try {
			fi.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (FileInputStream FicheroNuevo = new FileInputStream(fi);) {
			cliente.storeFile(fichero, FicheroNuevo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.delete();
	}

	public void descargar(String rutaCompleta, String nombre) {
		File fileDescargar;
		String archivoDirDestino = "";
		String dirDest = "";
		JFileChooser elegir = new JFileChooser();
		elegir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		elegir.setDialogTitle("aaaaaaa");
		int returnF = elegir.showDialog(null, "Descargar..");
		if (returnF == JFileChooser.APPROVE_OPTION) {
			fileDescargar = elegir.getSelectedFile();
			dirDest = fileDescargar.getAbsolutePath();
			archivoDirDestino = dirDest + File.separator + nombre;

			try {
				BufferedOutputStream salida = new BufferedOutputStream(new FileOutputStream(archivoDirDestino));
				if (cliente.retrieveFile(rutaCompleta, salida))
					JOptionPane.showMessageDialog(null, nombre + "=> Se ha descargado correctamente...");
				else
					JOptionPane.showMessageDialog(null, nombre + "=> No se ha podido descargar...");
				salida.close();

			} catch (Exception e) {
				System.out.println("ERROR");
			}

		}

	}

	public static FTPClient getCliente() {
		return cliente;
	}

	public static String getUser() {
		return user;
	}

	public static String getPass() {
		return pass;
	}

	public static String getHost() {
		return host;
	}

	public static String getEmail() {
		return email;
	}

	public static FTPFile[] getFicheros() {
		return ficheros;
	}
}

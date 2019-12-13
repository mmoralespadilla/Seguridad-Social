package PrimerFtp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class MyFtp {
	private static FTPClient cliente = new FTPClient();
	private static String user = "usuario@usuario.com", pass = "123";
	private static String path;

	public static void main(String[] args) throws SocketException, IOException {
		user = "admin@admin.com";
		String nombreFichero = "texto.txt";
		cliente.connect("localhost", 21);
		boolean conectado = cliente.login(user, pass);
		System.out.println(conectado);
		path = "D:\\"+nombreFichero;
		System.out.println(cliente.printWorkingDirectory());
		if (!user.contains("admin")) {

			subir(path, "prueba.txt");
		}
		System.out.println(path);
		listadoArchivos(user);
	}

	private static boolean subir(String fichero, String nombre) {
		boolean t = false;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(fichero));
			if (cliente.storeFile(nombre, in)) {
				System.out.println("Archivo subido");
				in.close();
				t = true;
			} else
				System.out.println("No se ha podido subir");
		} catch (FileNotFoundException e) {
			System.out.println("Ruta no encontrada");
		} catch (IOException e) {
			System.out.println("Error de fichero");
		}finally {
		
		}
		return t;
	}

	private static void listadoArchivos(String nombre) throws IOException {

		FTPFile[] path = cliente.listFiles();
		System.out.println(path.length);
		for (int i = 0; i < path.length; i++) {
			System.out.println(path[i].getName());
		}

	}
}

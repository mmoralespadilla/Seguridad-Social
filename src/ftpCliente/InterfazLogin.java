package ftpCliente;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class InterfazLogin extends JDialog {

	private BufferedImage image;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JTextField textFieldContraseña;
	private JPanel panelLogin;
	private JPanel panelImagen;
	private String rutaImagen;
	private static ModeloTextoInterfaz modeloTexto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazLogin frame = new InterfazLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public InterfazLogin(){
		modeloTexto = new ModeloTextoInterfaz();
		setTitle(modeloTexto.getTituloLogin());
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 524, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new java.awt.Color(218, 230, 228));

		panelLogin = new JPanel();
		panelLogin.setBorder(new LineBorder(new Color(30, 105, 90), 3));

		panelLogin.setBounds(40, 86, 427, 168);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		panelLogin.setBackground(new java.awt.Color(218, 230, 228));

		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);

		JLabel lblUsuario = new JLabel(modeloTexto.getTituloUsuario());
		lblUsuario.setBounds(31, 28, 75, 16);
		lblUsuario.setFont(fuenteTitulo);
		panelLogin.add(lblUsuario);

		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(134, 25, 231, 25);

		panelLogin.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);

		JLabel lblContrasea = new JLabel(modeloTexto.getTituloContrasena());
		lblContrasea.setBounds(31, 80, 80, 16);
		lblContrasea.setFont(fuenteTitulo);
		panelLogin.add(lblContrasea);

		textFieldContraseña = new JTextField();
		textFieldContraseña.setColumns(10);
		textFieldContraseña.setBounds(134, 75, 231, 25);
		panelLogin.add(textFieldContraseña);

		JButton btnLogin = new JButton(modeloTexto.getTituloLogin());
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = textFieldContraseña.getText().toString();
				String usuario = textFieldUsuario.getText().toString();
				int comprobarLogin = ConexionMysql.comprobarLogin(usuario, password);
				if (comprobarLogin >= 0) {
					String email = ConexionMysql.email;
					ControladorFtp ftp = new ControladorFtp("localhost",usuario, password, email);
					try {
						if(ftp.init()) {
							try {
								InterfazFtp frame = new InterfazFtp(ftp);
								dispose();
								frame.setVisible(true);
							} catch (Exception c) {
								c.printStackTrace();
							}
						}
					} catch (SocketException a) {
						// TODO Auto-generated catch block
						a.printStackTrace();
					} catch (IOException b) {
						// TODO Auto-generated catch block
						b.printStackTrace();
					}
				} else if (comprobarLogin == -2){
					JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
				} else if (comprobarLogin == -1 ) {
					JOptionPane.showMessageDialog(null, "Usuario no existe");
				}
			}
		});
		btnLogin.setBounds(165, 120, 97, 30);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new java.awt.Color(30, 105, 90));
		btnLogin.setFont(fuenteTitulo);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		btnLogin.setBorder(compound);
		panelLogin.add(btnLogin);
	}
}

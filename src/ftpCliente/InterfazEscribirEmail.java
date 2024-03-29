package ftpCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;

public class InterfazEscribirEmail extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel dtm;
	private static JTextArea areaTexto;
	private static JTextField textFieldPara;
	private static JTextField textFieldAsunto;
	private static ModeloTextoInterfaz modeloTexto;
	private static CreadorInterfaz creacion;
	private static JComboBox listaAdjuntos;
	private static JComboBox listaContactos;
	private static ArrayList<String> emails = new ArrayList<>();
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazEscribirEmail frame = new InterfazEscribirEmail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazEscribirEmail() {
		modeloTexto = new ModeloTextoInterfaz();
		creacion = new CreadorInterfaz();

		ArrayList<String> titulosMenuItemAcciones;
		ArrayList<String> titulosMenuItemAyuda;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 547);
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new java.awt.Color(30, 105, 90));
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		menuBar.setBorder(compound);
		setJMenuBar(menuBar);

		// Transfer menu
		JMenu mnTransferencia = creacion.crearMenu(modeloTexto.getTituloAcciones(), menuBar);
		titulosMenuItemAcciones = llenarListaTituloAcciones();
		creacion.crearItems(titulosMenuItemAcciones, mnTransferencia);
		menuBar.add(mnTransferencia);

		// Help menu
		JMenu mnAyuda = creacion.crearMenu(modeloTexto.getTituloAyuda(), menuBar);
		titulosMenuItemAyuda = llenarListaTituloAyuda();
		creacion.crearItems(titulosMenuItemAyuda, mnAyuda);
		menuBar.add(mnAyuda);

		// Global Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//From area
		listaContactos = new JComboBox();
		listaContactos.setBounds(615, 20, 160, 30);
		Connection con;
		ResultSet resultado;
		try {
		if(ConexionMysql.iniciarConexion()){
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/" + "segsoc", "root", "");
				Statement consulta = con.createStatement();
				resultado = consulta.executeQuery("select usuario, email from usuarios");
				while(resultado.next()){
					listaContactos.addItem(resultado.getString(1));
					emails.add(resultado.getString(2));
				}
				listaContactos.addActionListener(new ControladorBotonesCorreo());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPane.add(listaContactos);
		JLabel lblDestinatario = new JLabel(modeloTexto.getCabeceraPara());
		lblDestinatario.setBounds(50, 20, 150, 30);
		lblDestinatario.setFont(fuenteTitulo);
		contentPane.add(lblDestinatario);
		textFieldPara = new JTextField();
		textFieldPara.setBounds(140, 20, 470, 30);
		contentPane.add(textFieldPara);
		
				
		//About area
		creacion.crearBotones(modeloTexto.getTituloBotonEnviar(),615, 55, contentPane);
		JLabel lblAsunto = new JLabel(modeloTexto.getCabeceraAsunto());
		lblAsunto.setBounds(50, 55, 150, 30);
		lblAsunto.setFont(fuenteTitulo);
		contentPane.add(lblAsunto);		
		textFieldAsunto = new JTextField();
		textFieldAsunto.setBounds(140, 55, 470, 30);
		contentPane.add(textFieldAsunto);
		
				
		
		//Files area
		creacion.crearBotones(modeloTexto.getTituloBotonAdjuntar(), 50, 410, contentPane);
		listaAdjuntos = new JComboBox();
		listaAdjuntos.setBounds(250, 410, 330, 30);
		contentPane.add(listaAdjuntos);
		creacion.crearBotones(modeloTexto.getTituloBotonEliminar(), 615, 410, contentPane);
		
		
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 90, 725, 300);
		contentPane.add(scrollPane);

		//FALTA METER ASUNTO , SE PUEDE EN CELDA?????
		dtm = new DefaultTableModel();
		areaTexto = new JTextArea();
		scrollPane.setViewportView(areaTexto);
		scrollPane.getViewport().setBackground(Color.WHITE);
	}

	private ArrayList<String> llenarListaTituloAyuda() {
		ArrayList<String> titulosMenuItemAyuda = new ArrayList();
		titulosMenuItemAyuda.add(modeloTexto.getTituloAyudaSobre());
		return titulosMenuItemAyuda;
	}

	private ArrayList<String> llenarListaTituloAcciones() {
		ArrayList<String> titulosMenuItemAcciones = new ArrayList();
		titulosMenuItemAcciones.add(modeloTexto.getTituloBotonAdjuntar());
		titulosMenuItemAcciones.add(modeloTexto.getTituloBotonEnviar());

		return titulosMenuItemAcciones;
	}

	public static JTextArea getAreaTexto() {
		return areaTexto;
	}

	public void setAreaTexto(JTextArea areaTexto) {
		this.areaTexto = areaTexto;
	}

	public static JTextField getTextFieldPara() {
		return textFieldPara;
	}

	public static void setTextFieldPara(String texto) {
		InterfazEscribirEmail.textFieldPara.setText(texto);
	}
	
	public static JTextField getTextFieldAsunto() {
		return textFieldAsunto;
	}

	public void setTextFieldAsunto(JTextField textFieldAsunto) {
		this.textFieldAsunto = textFieldAsunto;
	}

	public static JComboBox getListaAdjuntos() {
		return listaAdjuntos;
	}

	public void setListaAdjuntos(JComboBox listaAdjuntos) {
		this.listaAdjuntos = listaAdjuntos;
	}

	public static JComboBox getListaContactos() {
		return listaContactos;
	}

	public static void setListaContactos(JComboBox listaContactos) {
		InterfazEscribirEmail.listaContactos = listaContactos;
	}

	public static ArrayList<String> getEmails() {
		return emails;
	}

	public static void setEmails(ArrayList<String> emails) {
		InterfazEscribirEmail.emails = emails;
	}
	
}

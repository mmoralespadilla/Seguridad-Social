package ftpCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import recibocorreo.MenuCorreo;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;

public class InterfazEmail extends JDialog {

	private static String user = "iamsegsoctrustme@gmail.com";
	private static String pass = "segsoc123";
	private JPanel contentPane;
	private static DefaultTableModel dtm;
	private static JTable table;
	private static ModeloTextoInterfaz modeloTexto;
	private static CreadorInterfaz creacion;
	private HiloRecargaEmail hiloRecarga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazEmail frame = new InterfazEmail(user,pass);
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
	public InterfazEmail(String user, String pass) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				hiloRecarga.stop();
			}
		});
		hiloRecarga = new HiloRecargaEmail();
		hiloRecarga.start();
		this.user = user;
		this.pass = pass;
		modeloTexto = new ModeloTextoInterfaz();
		creacion = new CreadorInterfaz();
		
		ArrayList <String> titulosMenuItemAcciones;
		ArrayList <String> titulosMenuItemAyuda;
		
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 855, 547);
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);
		
		//Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new java.awt.Color(30, 105, 90));
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);	
		menuBar.setBorder(compound);	
		setJMenuBar(menuBar);
					
		//Transfer menu
		JMenu mnTransferencia = creacion.crearMenu(modeloTexto.getTituloAcciones(), menuBar);
		titulosMenuItemAcciones = llenarListaTituloAcciones();
		creacion.crearItems (titulosMenuItemAcciones, mnTransferencia);		
		menuBar.add(mnTransferencia);
		
		//Help menu
		JMenu mnAyuda = creacion.crearMenu(modeloTexto.getTituloAyuda(), menuBar);
		titulosMenuItemAyuda = llenarListaTituloAyuda();
		creacion.crearItems(titulosMenuItemAyuda, mnAyuda);		
		menuBar.add(mnAyuda);
		
		
		//Global Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		creacion.crearBotones (titulosMenuItemAcciones, 50, contentPane,2);
		
		
		//ESTO VA A METODO ENVIANDOLE SI ES ENVIADOS O RECIBIDOS Y EL TEXTO DE REMITENTE O DESTINATARIO?????????
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 50, 515, 365);
		contentPane.add(scrollPane);
		
		dtm = new DefaultTableModel();
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// Cabeceras de la tabla
		dtm.addColumn(modeloTexto.getCabeceraDe());
		dtm.addColumn(modeloTexto.getCabeceraAsunto());
		table.setSelectionMode(0);
		// Editor de celdas para centrar los datos
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.getColumnModel().getColumn(1).setCellRenderer(tcr);
		scrollPane.setViewportView(table);
		scrollPane.getViewport().setBackground(Color.WHITE);	
		recargarTabla();
	}	
	
	private ArrayList <String> llenarListaTituloAyuda(){
		ArrayList <String> titulosMenuItemAyuda = new ArrayList();
		titulosMenuItemAyuda.add(modeloTexto.getTituloAyudaSobre());
		return titulosMenuItemAyuda;
	}		
		
	private ArrayList <String> llenarListaTituloAcciones(){
		ArrayList <String> titulosMenuItemAcciones = new ArrayList();
		titulosMenuItemAcciones.add(modeloTexto.getTituloAccionesRefrescar());
		titulosMenuItemAcciones.add(modeloTexto.getTituloAccionesLeer());
		titulosMenuItemAcciones.add(modeloTexto.getTituloAccionesEliminar());
		titulosMenuItemAcciones.add(modeloTexto.getTituloAccionesCrear());
		titulosMenuItemAcciones.add(modeloTexto.getTituloEmailRecibido());
		titulosMenuItemAcciones.add(modeloTexto.getTituloEmailEnviado());
		
		return titulosMenuItemAcciones;
	}
	/**
	 * Metodo para vaciar la tabla
	 */
	public static void vaciarTabla() {
		int a = table.getRowCount() - 1;
		for (int i = a; i >= 0; i--) {
			dtm.removeRow(dtm.getRowCount() - 1);
		}
	}

	/**
	 * Metodo que vacia la tabla, y la vuelve a rellenar con los datos de neodatis
	 */
	public static void recargarTabla() {
		vaciarTabla();
		try {
			recibocorreo.MenuCorreo menuCorreo = new MenuCorreo(user, pass);
			menuCorreo.conectar();
			Message[] mensajes = menuCorreo.listarMensajes();
			for(int i = mensajes.length - 1; i >= 0; i--) {
				Object[] row = {mensajes[i].getFrom()[0].toString(), mensajes[i].getSubject()};
				dtm.addRow(row);
			}
		} catch (MessagingException me) {
			System.err.println(me.getMessage());
		}
	}

	public static JTable getTable() {
		return table;
	}

	public static void setTable(JTable table) {
		InterfazEmail.table = table;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		InterfazEmail.user = user;
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		InterfazEmail.pass = pass;
	}
	
	
	
}

package ftpCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JEditorPane;

public class InterfazEscribirEmail extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;
	private JTextField textFieldPara;
	private static modeloTextoInterfaz modeloTexto;
	private static InterfazFtp creacion;
	private static ControladorBotonesCorreo controlBotonesCorreo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		controlBotonesCorreo = new ControladorBotonesCorreo();
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
		modeloTexto = new modeloTextoInterfaz();
		creacion = new InterfazFtp();

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

		crearBotones(modeloTexto.getTituloBotonEnviar(),615, 30, contentPane);

		JLabel lblDestinatario = new JLabel(modeloTexto.getCabeceraPara());
		lblDestinatario.setBounds(50, 30, 150, 30);
		lblDestinatario.setFont(fuenteTitulo);
		contentPane.add(lblDestinatario);
		
		textFieldPara = new JTextField();
		textFieldPara.setBounds(140, 30, 470, 30);
		contentPane.add(textFieldPara);
		
		
		crearBotones(modeloTexto.getTituloBotonAdjuntar(), 50, 410, contentPane);
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 70, 725, 330);
		contentPane.add(scrollPane);

		//FALTA METER ASUNTO , SE PUEDE EN CELDA?????
		dtm = new DefaultTableModel();
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// Cabeceras de la tabla
		dtm.addColumn(modeloTexto.getCabeceraContenido());
		table.setSelectionMode(0);
		// Editor de celdas para centrar los datos
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		scrollPane.setViewportView(table);
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

	private void crearBotones(String titulo, int x , int y, JPanel panel) {
		JButton boton = new JButton(titulo);
		boton.addActionListener(controlBotonesCorreo);
		boton.setBounds(x, y, 159, 30);	
		ponerPropiedadesBoton(boton);
		panel.add(boton);
	}

	private void ponerPropiedadesBoton(JButton boton) {

		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);
		boton.setForeground(Color.WHITE);
		boton.setBackground(new java.awt.Color(30, 105, 90));
		boton.setFont(fuenteTitulo);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		boton.setBorder(compound);
	}

}

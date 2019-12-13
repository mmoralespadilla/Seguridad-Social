package ftpCliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JEditorPane;

public class InterfazEmail extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazEmail frame = new InterfazEmail();
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
	public InterfazEmail() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 547);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTransferencia = new JMenu("Acciones");
		menuBar.add(mnTransferencia);
		
		JMenuItem mntmSubirFichero = new JMenuItem("New menu item");
		mnTransferencia.add(mntmSubirFichero);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre..");
		mnAyuda.add(mntmSobre);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Leer email");
		btnNewButton.setBounds(617, 81, 152, 30);
		contentPane.add(btnNewButton);
		
		JButton btnEliminarEmail = new JButton("Eliminar email");
		btnEliminarEmail.setBounds(617, 139, 152, 30);
		contentPane.add(btnEliminarEmail);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(617, 193, 152, 30);
		contentPane.add(btnCrear);
		
		JButton btnEmailrecibidos = new JButton("Email Recibidos");
		btnEmailrecibidos.setBounds(617, 246, 152, 30);
		contentPane.add(btnEmailrecibidos);
		
		JButton btnEmailEnviados = new JButton("Email enviados");
		btnEmailEnviados.setBounds(617, 302, 152, 30);
		contentPane.add(btnEmailEnviados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 52, 515, 369);
		contentPane.add(scrollPane);
		
		dtm = new DefaultTableModel();
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// Cabeceras de la tabla
		dtm.addColumn("De:");
		dtm.addColumn("Asunto");
		table.setSelectionMode(0);
		// Editor de celdas para centrar los datos
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.getColumnModel().getColumn(1).setCellRenderer(tcr);
		scrollPane.setViewportView(table);
	}
}

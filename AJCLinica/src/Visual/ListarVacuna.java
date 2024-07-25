package Visual;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Clinica;
import logico.Paciente;
import logico.Persona;
import logico.Vacuna;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListarVacuna extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static DefaultTableModel modelo;
	private static Object[] row;
	private Vacuna selected = null;
	private JButton btnUpdate;
	private JButton btnEliminar;
	private static boolean esAdmin=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarVacuna dialog = new ListarVacuna(esAdmin);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarVacuna(boolean valido) {
		esAdmin=valido;
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage("listado.png"));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(esAdmin==true)
						{
							int index = table.getSelectedRow();
							if (index >= 0) {
								btnEliminar.setEnabled(true);
								btnUpdate.setEnabled(true);
								selected = Clinica.getInstance().buscarVacunaByCod(table.getValueAt(index,0).toString());
							}
							
						}
					}
				});
				modelo= new DefaultTableModel();
				String[] headers = {"Codigo", "Nombre","Descripcion","Cantidad"};
				modelo.setColumnIdentifiers(headers);
				table.setModel(modelo);
				scrollPane.setViewportView(table);
				}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnUpdate = new JButton("Actualizar");
				btnUpdate.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						RegVacuna update= new RegVacuna(selected);
						update.setModal(true);
						update.setVisible(true);
						//nuevo
						btnEliminar.setEnabled(false);
						btnUpdate.setEnabled(false);

					}
				});
				btnUpdate.setActionCommand("OK");
				buttonPane.add(btnUpdate);
				getRootPane().setDefaultButton(btnUpdate);
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (selected!=null) {
							int option = JOptionPane.showConfirmDialog(null, "Está seguro(a) que desea eliminar el Paciente con código: "+ selected.getCodigo(), "Confirmación", JOptionPane.OK_CANCEL_OPTION);
							if (option == JOptionPane.OK_OPTION  ) {
									Clinica.getInstance().eliminarVacuna(selected);
									btnEliminar.setEnabled(false);
									btnUpdate.setEnabled(false);
									loadVacunas();
							}
						}
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				btnUpdate.setEnabled(false);
				btnEliminar.setEnabled(false);
				loadVacunas();
			}
		}
	}

	protected void loadVacunas() {
		modelo.setRowCount(0);
		row = new Object[table.getColumnCount()];
		
		for (Vacuna aux: Clinica.getInstance().getMisVacunas()) {	
			row[0] = aux.getCodigo();
			row[1] = aux.getNombre();
			row[2] = aux.getDescripcion();
			row[3] = aux.getCant();
			modelo.addRow(row);	
		}	
		
	}

}

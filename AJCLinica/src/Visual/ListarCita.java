package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.CitaMedica;
import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Empleado;
import logico.Persona;
import logico.User;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ListarCita extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	private static DefaultTableModel modelo;
	private static Object[] row;
	private JButton btnUpdate;
	private JButton btnEliminar;
	private CitaMedica selected=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarCita dialog = new ListarCita();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarCita() {
		setResizable(false);
		setTitle("Listado Citas");
		setIconImage(Toolkit.getDefaultToolkit().getImage("listado.png"));
		setBounds(100, 100, 685, 458);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int index=table.getSelectedRow();
						if (index>=0) {
							btnEliminar.setEnabled(true);
							btnUpdate.setEnabled(true);
							selected = Clinica.getInstance().buscarCitaByCode(table.getValueAt(index,0).toString());
							
						}
					}
				});
				modelo= new DefaultTableModel();
				String[] headers = {"Codigo", "Nombre","Teléfono","Doctor","Fecha","Hora"};
				modelo.setColumnIdentifiers(headers);
				table.setModel(modelo);
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnUpdate = new JButton("Actualizar");
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							RegCita3 update= new RegCita3(selected);
							update.setModal(true);
							update.setVisible(true);
							//nuevo
							btnEliminar.setEnabled(false);
							btnUpdate.setEnabled(false);


					}
				});
				btnUpdate.setEnabled(false);
				btnUpdate.setActionCommand("OK");
				buttonPane.add(btnUpdate);
				getRootPane().setDefaultButton(btnUpdate);
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (selected!=null) {
							int option = JOptionPane.showConfirmDialog(null, "Está seguro(a) que desea eliminar la Cita con código: "+ selected.getCodigo(), "Confirmación", JOptionPane.OK_CANCEL_OPTION);
							if (option== JOptionPane.OK_OPTION  ) {

									Clinica.getInstance().eliminarCitas(selected);
									btnEliminar.setEnabled(false);
									btnUpdate.setEnabled(false);
									loadCitas();

							}
						}
					}
				});
				btnEliminar.setEnabled(false);
				buttonPane.add(btnEliminar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		loadCitas();
	}

	public static void loadCitas() {
	    modelo.setRowCount(0);
	    row = new Object[table.getColumnCount()];

	    for (CitaMedica persona : Clinica.getInstance().getMisCitas()) {
	        row[0] = persona.getCodigo();
	        row[1] = persona.getNomPaciente();
	        row[2] = persona.getCedPaciente();
	        
	        // Verificar que la cita tenga un doctor asociado antes de acceder a su nombre
	        if (persona.getDoctor() != null) {
	            row[3] = persona.getDoctor().getNombre();
	        } else {
	            row[3] = "Sin doctor asignado";
	        }
	        
	        row[4] = persona.getFecha();
	        row[5] = persona.getHora();
	        modelo.addRow(row);
	    }
	}
}
	
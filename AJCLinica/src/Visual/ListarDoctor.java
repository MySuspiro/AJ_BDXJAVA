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
import logico.Consulta;
import logico.Doctor;
import logico.Empleado;
import logico.Persona;
import logico.User;
import net.code.java.sql.JavaConnect2SQL;

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

public class ListarDoctor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	private static DefaultTableModel modelo;
	private static Object[] row;
	private JButton btnUpdate;
	private JButton btnEliminar;
	private Doctor selected=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarDoctor dialog = new ListarDoctor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarDoctor() {
		setResizable(false);
		setTitle("Listado Doctores");
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
							selected = JavaConnect2SQL.getInstace().buscarDoctorByCodigo(table.getValueAt(index,0).toString());
							
						}
					}
				});
				modelo= new DefaultTableModel();
				String[] headers = {"Codigo", "Nombre","Teléfono","Especialidad"};
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
							RegDoctor update= new RegDoctor(selected);
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
						if (selected!=null && verificarDoc(selected)==true) {
							int option = JOptionPane.showConfirmDialog(null, "Está seguro(a) que desea eliminar el Cliente con código: "+ selected.getCodigo(), "Confirmación", JOptionPane.OK_CANCEL_OPTION);
							if (option== JOptionPane.OK_OPTION  ) {

									JavaConnect2SQL.getInstace().deleteWithString("Paciente", "Codigo", selected.getCodigo());
									//NUEVO
									for (User user : JavaConnect2SQL.getInstace().getMisUsers("")) {
									    System.out.println("Current User: " + user);

									    if (user != null) {
									        System.out.println("User's Persona: " + user.getPersona());

									        if (user.getPersona() != null && selected != null) {
									            if (user.getPersona().getCodigo().equalsIgnoreCase(selected.getCodigo())) {
									            	JavaConnect2SQL.getInstace().deleteWithString("User", "UserName", user.getUserName());
									            }
									        } else {
									            System.out.println("Either user.getPersona() or miDoctor is null.");
									        }
									    } else {
									        System.out.println("User is null.");
									    }
									}

									//
									
									btnEliminar.setEnabled(false);
									btnUpdate.setEnabled(false);
									loadDoctores();

							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Error: El doctor tiene Citas y Consultas registradas.", "Agenda", JOptionPane.ERROR_MESSAGE);
							
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
		
		loadDoctores();
	}

	public static void loadDoctores() {
		modelo.setRowCount(0);
		row= new Object[table.getColumnCount()];
		
		for (Doctor persona: JavaConnect2SQL.getInstace().getMisDoctor("")) {
				row[0]=persona.getCodigo();
				row[1]=persona.getNombre();
				row[2]=persona.getTelefono();
				row[3]=persona.getEspecialidad();
				modelo.addRow(row);	
				
			
		}	
		
	}
	
	
	//verificar que no existan consultas con el doctor o citas
	
	public boolean verificarDoc(Doctor doc) {
		
		
		for (Consulta consul: JavaConnect2SQL.getInstace().getMisConsultas("")) {
			if(consul.getDoctor().getCodigo().equalsIgnoreCase(doc.getCodigo()))
			{
				return false;
				
			}
		}
		
		for (CitaMedica cita: JavaConnect2SQL.getInstace().getMisCitas("")) {
			if(cita.getDoctor().getCodigo().equalsIgnoreCase(doc.getCodigo()))
			{
				return false;
				
			}
		}
	
		return true;
		
	}
	
	


}

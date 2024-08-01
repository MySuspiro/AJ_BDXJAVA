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

import com.sun.javafx.scene.control.SelectedCellsMap;

import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Paciente;
import logico.Persona;
import net.code.java.sql.JavaConnect2SQL;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListarPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	private static DefaultTableModel modelo;
	private static Object[] row;
	private JButton btnUpdate;
	private JButton btnEliminar;
	private Paciente selected=null;
	private static boolean esAdmin=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarPaciente dialog = new ListarPaciente(esAdmin);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarPaciente(boolean valido) {
		esAdmin=valido;

		setResizable(false);
		setTitle("Listado Pacientes");
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
							if (esAdmin==true)
							{
								btnEliminar.setEnabled(true);
							}
							btnUpdate.setEnabled(true);
							selected = JavaConnect2SQL.getInstace().buscarPacienteByCodigo(table.getValueAt(index,0).toString());
							
						}
					}
				});
				modelo= new DefaultTableModel();
				String[] headers = {"Codigo", "Nombre","Teléfono","Cédula"};
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
				
				if (esAdmin==true)
				{
					btnUpdate.setText("Actualizar");
				}
				else 
				{
					btnUpdate.setText("Vizualizar");	
				}
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(esAdmin==true)
						{
							RegPaciente update= new RegPaciente(selected,true);
							update.setModal(true);
							update.setVisible(true);	
						}
						else
						{
							RegPaciente update= new RegPaciente(selected,false);
							update.setModal(true);
							update.setVisible(true);
							
						}

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
							int option = JOptionPane.showConfirmDialog(null, "Está seguro(a) que desea eliminar el Paciente con código: "+ selected.getCodigo(), "Confirmación", JOptionPane.OK_CANCEL_OPTION);
							if (option== JOptionPane.OK_OPTION && verificarPaciente()==true  ) {

									JavaConnect2SQL.getInstace().deleteWithString("Paciente", "Codigo", selected.getCodigo());
									btnEliminar.setEnabled(false);
									btnUpdate.setEnabled(false);
									loadPacientes();

							}else
							{
								 JOptionPane.showMessageDialog(null, "No se puede eliminar, el paciente tiene consultas registradas.", "Error", JOptionPane.ERROR_MESSAGE);
								
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
		loadPacientes();
	}

	public static void loadPacientes() {
		modelo.setRowCount(0);
		row= new Object[table.getColumnCount()];
		
		for (Paciente persona: JavaConnect2SQL.getInstace().getMisPacientes("")) {
			row[0]=persona.getCodigo();
			row[1]=persona.getNombre();
			row[2]=persona.getTelefono();
			row[3]=persona.getCedula();
			modelo.addRow(row);	
			
		}	
		
	}
	
	public boolean verificarPaciente()
	{
		for (Consulta consulta: JavaConnect2SQL.getInstace().getMisConsultas("")) {
			if (selected.equals(consulta.getPaciente()))
			{
				return false;
			}
		}
		
		return true;
	}


}


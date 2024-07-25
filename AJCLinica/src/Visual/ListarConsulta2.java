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
import javax.swing.table.TableColumnModel;

import logico.Clinica;
import logico.Consulta;
import logico.Doctor;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListarConsulta2 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	private static DefaultTableModel modelo;
	private static Object[] row;
	private static Doctor miDoctor=null;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		try {
			ListarConsulta2 dialog = new ListarConsulta2(miDoctor);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarConsulta2(Doctor doc) {
		miDoctor=doc;
		setResizable(false);
		setTitle("Listado Consultas 2");
		setIconImage(Toolkit.getDefaultToolkit().getImage("listado.png"));
		setBounds(100, 100, 1131, 501);
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
				modelo= new DefaultTableModel();
				String[] headers = {"Codigo","Fecha","Paciente","Enfermedad","Estado","Vacuna","Diagnostico"};
				modelo.setColumnIdentifiers(headers);
				table.setModel(modelo);
				scrollPane.setViewportView(table);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getTableHeader().setReorderingAllowed(false);
				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(90);
				columnModel.getColumn(1).setPreferredWidth(120);
				columnModel.getColumn(2).setPreferredWidth(185);
				columnModel.getColumn(3).setPreferredWidth(180);
				columnModel.getColumn(4).setPreferredWidth(90);
				columnModel.getColumn(5).setPreferredWidth(180);
				columnModel.getColumn(6).setPreferredWidth(265);
				
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
		loadConsultas();
	}

	public static void loadConsultas() {
		modelo.setRowCount(0);
		row= new Object[table.getColumnCount()];
		
		for (Consulta cliente: Clinica.getInstance().getMisConsultas()) {
			if (cliente.getDoctor().equals(miDoctor))
			{
				row[0]=cliente.getCodigoConsulta();
				row[1]=cliente.getFechaConsulta();
				row[2]=cliente.getPaciente().getNombre();	
				if(cliente.getEnfermedad()!=null)
				{
					row[3]=cliente.getEnfermedad().getNombre();
					row[4]=cliente.getStatus();
					
				}
				else
				{
					row[3]="-";
					row[4]="-";
					
				}
				if(cliente.getVac()!=null)
				{
					row[5]=cliente.getVac().getNombre();
					
				}
				else
				{
					row[5]="-";
					
				}

				row[6]=cliente.getDiagnostico();	
				modelo.addRow(row);
				
				
			}
	
		}	
		
	}

}
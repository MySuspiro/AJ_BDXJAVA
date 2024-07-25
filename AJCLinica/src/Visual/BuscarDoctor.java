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
import logico.Doctor;
import logico.Persona;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuscarDoctor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Object[] row;
	private DefaultTableModel modelo;
	private JTextField txtBuscar;
	private Doctor selected=null;
	private JButton btnSel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BuscarDoctor dialog = new BuscarDoctor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BuscarDoctor() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("editar.png"));
		setTitle("Seleccionar Doctor");
		setBounds(100, 100, 542, 340);
		//setLocationRelativeTo(null); 
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel panel = new JPanel();
			panel.setBounds(12, 59, 504, 188);
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							//HOLA AQUI ME QUEDE
							int indexCli=table.getSelectedRow();
							if (indexCli>=0) {
								selected = (Doctor) Clinica.getInstance().buscarPersonaByCodigo(table.getValueAt(indexCli,0).toString());
								if (selected!=null)
								{
									btnSel.setEnabled(true);
									
								System.out.print(selected.getCodigo());
								}
							}
						}
					});
					modelo= new DefaultTableModel();
					String[] headers = {"Codigo", "Nombre", "Especialidad"};
					modelo.setColumnIdentifiers(headers);
					table.setModel(modelo);
					scrollPane.setViewportView(table);
				}
			}
		}
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(12, 24, 395, 22);
		contentPanel.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreBuscado = txtBuscar.getText();
				loadDoctores(nombreBuscado);
			}
		});
		btnNewButton.setBounds(419, 23, 97, 25);
		contentPanel.add(btnNewButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				{
					btnSel = new JButton("Seleccionar");
					btnSel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					btnSel.setEnabled(false);
					buttonPane.add(btnSel);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//loadFacturas();
	}
	
	
	public void loadDoctores(String nombreBuscado) {
	    modelo.setRowCount(0);
	    row = new Object[table.getColumnCount()];

	    nombreBuscado = nombreBuscado.toLowerCase();
	    boolean coincidencia=false;

	    for (Persona doc : Clinica.getInstance().getMisPersonas()) {
	    	if(doc instanceof Doctor) {
		        if (doc.getNombre().toLowerCase().contains(nombreBuscado)|| doc.getCedula().equals(nombreBuscado) || ((Doctor) doc).getEspecialidad().equalsIgnoreCase(nombreBuscado) ) {
		            row[0] = doc.getCodigo();
		            row[1] = doc.getNombre();
		            row[2] = ((Doctor) doc).getEspecialidad();
		            modelo.addRow(row);
		            coincidencia=true;
		        }
	    		
	    	}

	    }
	    
	    if(!coincidencia)
	    {
	    	JOptionPane.showMessageDialog(null, "Doctor no encontrado", "Doctores", JOptionPane.INFORMATION_MESSAGE);
	    		
	    }
	}

	public Doctor getSelected() {
		return selected;
	}
}


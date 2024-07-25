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

import logico.Clinica;
import logico.Consulta;
import logico.Enfermedad;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListarEnf extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	private static DefaultTableModel modelo;
	private static Object[] row;
	private JButton btnUpdate;
	private JButton btnEliminar;
	private Enfermedad selected=null;
	private static boolean esAdmin=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarEnf dialog = new ListarEnf(esAdmin);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarEnf(boolean valido) {
		esAdmin=valido;
		setResizable(false);
		setTitle("Listado Enfermedades");
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
						if (esAdmin==true)
						{
							int index=table.getSelectedRow();
							if (index>=0) {
								btnEliminar.setEnabled(true);
								btnUpdate.setEnabled(true);
								selected = Clinica.getInstance().buscarEnfermedadByCode(table.getValueAt(index,0).toString());
							
						}
							
						}
					}
				});
				modelo= new DefaultTableModel();
				String[] headers = {"Codigo", "Nombre","Estátus","Descripción"};
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
							RegEnf update= new RegEnf(selected);
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
							int option = JOptionPane.showConfirmDialog(null, "Está seguro(a) que desea eliminar la Enfermedad con código: "+ selected.getCodigo(), "Confirmación", JOptionPane.OK_CANCEL_OPTION);
							if (option== JOptionPane.OK_OPTION  ) {
								if(verificarEnfermedad(selected)==false)//me quede aqui, hay que verificar que no este asignada en ningun lado
								{
									Clinica.getInstance().eliminarEnfermedad(selected);
									btnEliminar.setEnabled(false);
									btnUpdate.setEnabled(false);
									loadEnfermedades();
									
								}else
								{
									JOptionPane.showMessageDialog(null, "No se puede eliminar una Enfermedad asignada a un Paciente", "Error", JOptionPane.ERROR_MESSAGE);
								}

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
		loadEnfermedades();
	}

	public static void loadEnfermedades() {
		modelo.setRowCount(0);
		row= new Object[table.getColumnCount()];
		
		for (Enfermedad enf: Clinica.getInstance().getMisEnfermedades()) {
			row[0]=enf.getCodigo();
			row[1]=enf.getNombre();
			row[2]=enf.getStatus();
			row[3]=enf.getDescripcion();
			modelo.addRow(row);	
		}	
		
	}
	
	public boolean verificarEnfermedad(Enfermedad enf) {
	    boolean tieneEnf = false;

	    ArrayList<Consulta> misConsul = Clinica.getInstance().getMisConsultas();

	    for (Consulta consul : misConsul) {
	        if (consul.getEnfermedad().getCodigo().equalsIgnoreCase(enf.getCodigo())) {
	            tieneEnf= true;
	            break; 
	        }
	    }

		return tieneEnf;
	}


}

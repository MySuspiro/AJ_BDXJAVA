package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import logico.Clinica;
import logico.Consulta;
import logico.Historial;
import logico.Paciente;
import logico.Vacuna;
import sun.util.logging.resources.logging;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;

public class PacienteHistorial extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Object[] row;
	private DefaultTableModel modelo;
	private Object[] row2;
	private DefaultTableModel modelo2;
	private Historial historial = null;
	private JTextField txtCodigo;
	private JTextField txtPaciente;
	private JTable tablevac;
	private static Paciente miPac=null;
	private JTextField txtTipoSangre;
	private JTextField txtObesi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PacienteHistorial dialog = new PacienteHistorial(miPac);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PacienteHistorial(Paciente paciente) {
		miPac=paciente;
		setTitle("Historial de un Paciente");
		setIconImage(Toolkit.getDefaultToolkit().getImage("historial.png"));
		setResizable(false);
		setBounds(100, 100, 686, 646);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panelTabla = new JPanel();
				panelTabla.setBounds(12, 71, 650, 310);
				panel.add(panelTabla);
				panelTabla.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					panelTabla.add(scrollPane, BorderLayout.CENTER);
					{
						table = new JTable();
						modelo= new DefaultTableModel();
						String[] headers = {"Fecha", "Diagnostico", "Enfermedad","Estado"};
						modelo.setColumnIdentifiers(headers);
						table.setModel(modelo);
						scrollPane.setViewportView(table);
					}
				}
			}
			{
				JLabel label = new JLabel("C\u00F3digo Historial:");
				label.setBounds(12, 27, 97, 16);
				panel.add(label);
			}
			{
				txtCodigo = new JTextField();
				txtCodigo.setEditable(false);
				txtCodigo.setColumns(10);
				txtCodigo.setBounds(128, 24, 77, 22);
				panel.add(txtCodigo);
			}
			{
				JLabel label = new JLabel("Nombre Paciente:");
				label.setBounds(224, 27, 122, 16);
				panel.add(label);
			}
			{
				txtPaciente = new JTextField();
				txtPaciente.setEditable(false);
				txtPaciente.setColumns(10);
				txtPaciente.setBounds(365, 24, 281, 22);
				panel.add(txtPaciente);
			}
			{
				JLabel lblVacunas = new JLabel("Vacunas:");
				lblVacunas.setBounds(12, 394, 97, 16);
				panel.add(lblVacunas);
			}

			JPanel panel_1 = new JPanel();
			panel_1.setBounds(12, 423, 405, 129);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_1.add(scrollPane, BorderLayout.CENTER);

			tablevac = new JTable();
			modelo2= new DefaultTableModel();
			String[] headers2 = {"Nombre", "Descripción", "Enfermedad"};
			modelo2.setColumnIdentifiers(headers2);
			tablevac.setModel(modelo2);
			scrollPane.setViewportView(tablevac);

			txtTipoSangre = new JTextField();
			txtTipoSangre.setEditable(false);
			txtTipoSangre.setBounds(460, 456, 186, 22);
			panel.add(txtTipoSangre);
			txtTipoSangre.setColumns(10);

			JLabel lblNewLabel_1 = new JLabel("IMC:");
			lblNewLabel_1.setBounds(460, 497, 161, 16);
			panel.add(lblNewLabel_1);

			JLabel lblNewLabel_2 = new JLabel("Tipo de Sangre:");
			lblNewLabel_2.setBounds(460, 423, 161, 16);
			panel.add(lblNewLabel_2);

			txtObesi = new JTextField();
			txtObesi.setEditable(false);
			txtObesi.setBounds(460, 530, 186, 22);
			panel.add(txtObesi);
			txtObesi.setColumns(10);
		}
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		loadConsultas(miPac);
	}

	private void loadConsultas(Paciente clienteBuscado) {

		modelo.setRowCount(0);
		row= new Object[table.getColumnCount()];
		modelo2.setRowCount(0);
		row2= new Object[table.getColumnCount()];
		historial = clienteBuscado.getHist();
		txtTipoSangre.setText(clienteBuscado.getTipoSangre());
		float obesi = 0;
		txtObesi.setText("");
		//peso (kg) / [estatura (m)]2
		obesi = ((float)clienteBuscado.getPeso()/2.205f)/(float)(Math.pow((float)clienteBuscado.getAltura()/100f, 2));
		if(obesi<18.50) {
			txtObesi.setText("Bajo peso"); 
		} else if(obesi<24.9) {
			txtObesi.setText("Normal"); 
		}else if(obesi<29.9) {
			txtObesi.setText("Sobrepeso"); 
		}else {
			txtObesi.setText("Obesidad"); 
		}
		//historial = Clinica.getInstance().buscarHistorialByCedula(txtCedula.getText());
		//System.out.println("Valor de historial: " + historial.getCodigo());
		//JOptionPane.showMessageDialog(null, "Llegue aqui", "Error", JOptionPane.ERROR_MESSAGE);

		if (historial!=null) 
		{
			//JOptionPane.showMessageDialog(null, "Encontre Historial", "Error", JOptionPane.ERROR_MESSAGE);
			boolean coincidencia=false;
			ArrayList<Consulta> misConsultas = historial.getMisConsultas();
			ArrayList<Vacuna> misVacunas = historial.getMisVacunas();
			//paciente=(Paciente) Clinica.getInstance().buscarPersonaByCedula(txtCedula.getText());
			txtPaciente.setText(clienteBuscado.getNombre());
			txtCodigo.setText(clienteBuscado.getCodigo());

			for (Consulta consulta : misConsultas) {
				if (consulta.getPaciente().getCedula().equals(clienteBuscado.getCedula())) {
					row[0]=consulta.getFechaConsulta();
					row[1]=consulta.getDiagnostico();
					if (consulta.getEnfermedad()!=null)
					{
						row[2]=consulta.getEnfermedad().getNombre();

					}else
					{
						row[2]="-";
					}
					if (consulta.getStatus()!=null)
					{
						row[3]=consulta.getStatus();

					}else
					{
						row[3]="-";
					}
					modelo.addRow(row);
					coincidencia=true;
				}
			}

			for (Vacuna vacuna : misVacunas) {
				if (vacuna!=null)
				{
					row2[0]=vacuna.getNombre();
					row2[1]=vacuna.getDescripcion();
					row2[2]=vacuna.getEnf().getNombre();
					modelo2.addRow(row2);

				}
				else
				{
					row2[0]="-";
					row2[1]="-";
					row2[2]="-";
					modelo2.addRow(row2);

				}

				coincidencia=true;

			}

			if(!coincidencia)
			{
				JOptionPane.showMessageDialog(null, "No existe Historial", "Error", JOptionPane.ERROR_MESSAGE);
			}


		}
		else {
			JOptionPane.showMessageDialog(null, "No existe Historial", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}

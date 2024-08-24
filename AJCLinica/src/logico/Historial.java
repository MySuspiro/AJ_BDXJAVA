package logico;

import java.io.Serializable;
import java.util.ArrayList;

import net.code.java.sql.JavaConnect2SQL;


public class Historial implements Serializable {

	private String codigo;
	private String cedPaciente;
	private Paciente pac;
	private ArrayList<Vacuna> misVacunas;
	private ArrayList<Consulta> misConsultas;
	private ArrayList<Enfermedad> misEnfermedades;
	
	public Historial(String cedPaciente) {
		super();
		this.cedPaciente = cedPaciente;
		misVacunas = new ArrayList<>();
		misConsultas = new ArrayList<>();
		misEnfermedades = new ArrayList<>();
	}
	

	public String getCodigo() {
		return codigo;
	}
	
	public Paciente getPaciente() {
		pac = JavaConnect2SQL.getInstace().buscarPacienteByCedula(cedPaciente);
		return pac;
	}

	public ArrayList<Vacuna> getMisVacunas() {
		ArrayList<String> misCodes = JavaConnect2SQL.getInstace().getMisPaciente_Vacuna(getPaciente());
		Vacuna vac = null;
		misVacunas.clear();
		for (String aux : misCodes) {
			vac = JavaConnect2SQL.getInstace().buscarVacunaByCod(aux);
			if(vac != null) {
				misVacunas.add(vac);
			}
		}
		return misVacunas;
	}

	

	public ArrayList<Consulta> getMisConsultas() {
		ArrayList<String> misCodes = JavaConnect2SQL.getInstace().getMisPaciente_Consulta(getPaciente());
		Consulta consulta = null;
		misConsultas.clear();
		for (String aux : misCodes) {
			consulta = JavaConnect2SQL.getInstace().buscarConsultaXCod(aux);
			if(consulta!=null) {
				misConsultas.add(consulta);
			}
		}

		return misConsultas;
	}

	

	public String getCedPaciente() {
		return cedPaciente;
	}

	public ArrayList<Enfermedad> getMisEnfermedades() {
		ArrayList<String> misCodes = JavaConnect2SQL.getInstace().getMisEnfermedad_Condicion_Paciente(getPaciente());
		Enfermedad enf = null;
		misEnfermedades.clear();
		for (String aux : misCodes) {
			enf = JavaConnect2SQL.getInstace().buscarEnfermedadByCode(aux);
			if(enf != null) {
				misEnfermedades.add(enf);
			}
		}
		return misEnfermedades;
	}
	
}

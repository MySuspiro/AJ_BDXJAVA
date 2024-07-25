package logico;

import java.io.Serializable;
import java.util.ArrayList;


public class Historial implements Serializable {

	private String codigo;
	private String cedPaciente;
	private ArrayList<Vacuna> misVacunas;
	private ArrayList<Consulta> misConsultas;
	private ArrayList<Enfermedad> misEnfermedades;
	
	public Historial(String cedPaciente) {
		super();
		this.codigo = "H-" + Clinica.getInstance().getcodHist();
		this.cedPaciente = cedPaciente;
		misVacunas = new ArrayList<Vacuna>();
		misConsultas = new ArrayList<Consulta>();
		misEnfermedades = new ArrayList<Enfermedad>();
	}

	public String getCodigo() {
		return codigo;
	}

	public ArrayList<Vacuna> getMisVacunas() {
		return misVacunas;
	}

	public void addMisVacunas(Vacuna vac) {
		this.misVacunas.add(vac);
	}
	
	public void removeMisVacunas(Vacuna vac) {
		this.misVacunas.remove(vac);
	}
	

	public ArrayList<Consulta> getMisConsultas() {
		return misConsultas;
	}

	public void addMisConsultas(Consulta con) {
		this.misConsultas.add(con);
	}
	

	public String getCedPaciente() {
		return cedPaciente;
	}

	public ArrayList<Enfermedad> getMisEnfermedades() {
		return misEnfermedades;
	}
	
	public void addMisEnfermedades(Enfermedad aux) {
		this.misEnfermedades.add(aux);
	}
	
	public void eliminarMisEnfermedades(Enfermedad aux) {
		this.misEnfermedades.remove(aux);
	}
	
	
	
	
	
	
	
}

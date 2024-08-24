package logico;

import java.io.Serializable;
import java.util.Date;

public class CitaMedica implements Serializable {

	private String codigo;
	private Date fecha;
	private String cedPaciente;
	private String nomPaciente;
	private Doctor doctor;
	private String hora;
	
	
	public CitaMedica(String codigo, String cedPaciente, String nomPaciente, Doctor doctor, String hora, Date fecha) {
		super();
		this.codigo = codigo;
		this.cedPaciente = cedPaciente;
		this.nomPaciente = nomPaciente;
		this.doctor = doctor;
		this.hora = hora;
		this.fecha =  fecha;
	}
	public String getCodigo() {
		return codigo;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}

	public String getHora() {
		return hora;
	}
	public String getCedPaciente() {
		return cedPaciente;
	}
	public String getNomPaciente() {
		return nomPaciente;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setCedPaciente(String cedPaciente) {
		this.cedPaciente = cedPaciente;
	}
	public void setNomPaciente(String nomPaciente) {
		this.nomPaciente = nomPaciente;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}

}

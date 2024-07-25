package logico;

import java.io.Serializable;

public class Enfermedad implements Serializable {

	private String Codigo;
	private String Nombre;
	private String Status;
	private String descripcion;
	public Enfermedad(String codigo, String nombre, String status, String descripcion) {
		super();
		Codigo = "E-"+Clinica.getInstance().getcodEnf();
		Nombre = nombre;
		this.Status = status;
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	

}

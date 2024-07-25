package logico;

import java.io.Serializable;

public class Vacuna implements Serializable{
	
	private String codigo;
	private String nombre;
	private String descripcion;
	private int cant;
	private Enfermedad enf;
	public Vacuna(String nombre, String descripcion, int cant, Enfermedad enf) {
		super();
		this.codigo = "V-"+Clinica.getInstance().getcodVac();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cant = cant;
		this.enf = enf;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCant() {
		return cant;
	}
	public void SetCant(int cant) {
		this.cant = cant;
	}
	
	public void removeOne() {
		this.cant--;
	}
	
	public Enfermedad getEnf() {
		return enf;
	}
	public void setEnf(Enfermedad enf) {
		this.enf = enf;
	}

}

package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Paciente extends Persona implements Serializable {
	
	private String Seguro;
	private Historial hist;
	private int peso;
	private int altura;
	private String tipoSangre;

	public Paciente(String cedula, String nombre, String dir, String codigo, String telefono, char sexo, String correoElectronico,
			String seguro, int peso, int altura, String tipoSangre) {
		super(cedula, nombre, dir, codigo, telefono, sexo, correoElectronico);
		Seguro = seguro;
		hist = new Historial(cedula);
		codigo = "P-"+Clinica.getInstance().getcodPers();
		Clinica.getInstance().agregarHistorial(hist);
		this.peso = peso;
		this.altura = altura;
		this.tipoSangre = tipoSangre;
	}

	public String getSeguro() {
		return Seguro;
	}

	public void setSeguro(String seguro) {
		Seguro = seguro;
	}

	public Historial getHist() {
		return hist;
	}

	public void setHist(Historial hist) {
		this.hist = hist;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}
	
	

}

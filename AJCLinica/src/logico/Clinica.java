package logico;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import net.code.java.sql.JavaConnect2SQL;

public class Clinica implements Serializable{

	private ArrayList<Enfermedad> misEnfermedades;
	private ArrayList<Vacuna> misVacunas;
	private ArrayList<Consulta> misConsultas;
	private ArrayList<Persona> misPersonas;
	private ArrayList<Historial> misHistoriales;
	private ArrayList<CitaMedica> misCitas;
    private ArrayList<Sintoma> misSintomas;
	//en prueba pa lo fichero
	private ArrayList<User> misUsers;
	private static User loginUser;
	//
	private static Clinica clinica=null;


	
	
	private Clinica() {
		super();
		misEnfermedades = new ArrayList<Enfermedad>();
		misVacunas = new ArrayList<Vacuna>();
		misConsultas = new ArrayList<Consulta>();
		misPersonas = new ArrayList<Persona>();
		misHistoriales = new ArrayList<Historial>();
		misCitas = new ArrayList<CitaMedica>();
		misUsers = new ArrayList<User>();
		misSintomas = new ArrayList<Sintoma>();
	}
	
	public ArrayList<User> getMisUsers() {
		return misUsers;
	}

	public void setMisUsers(ArrayList<User> misUsers) {
		this.misUsers = misUsers;
	}

	public static Clinica getInstance() {
		
		if (clinica==null)
			clinica = new Clinica();
		return clinica;
		
	}

	public ArrayList<Enfermedad> getMisEnfermedades() {
		return misEnfermedades;
	}

	public void setMisEnfermedades(ArrayList<Enfermedad> misEnfermedades) {
		this.misEnfermedades = misEnfermedades;
	}

	public ArrayList<Vacuna> getMisVacunas() {
		return misVacunas;
	}

	public void setMisVacunas(ArrayList<Vacuna> misVacunas) {
		this.misVacunas = misVacunas;
	}

	public ArrayList<Consulta> getMisConsultas() {
		return misConsultas;
	}

	public void setMisConsultas(ArrayList<Consulta> misConsultas) {
		this.misConsultas = misConsultas;
	}

	public ArrayList<Persona> getMisPersonas() {
		return misPersonas;
	}

	public void setMisPersonas(ArrayList<Persona> misPersonas) {
		this.misPersonas = misPersonas;
	}

	public ArrayList<Historial> getMisHistoriales() {
		return misHistoriales;
	}

	public void setMisHistoriales(ArrayList<Historial> misHistoriales) {
		this.misHistoriales = misHistoriales;
	}

	public ArrayList<CitaMedica> getMisCitas() {
		return misCitas;
	}

	public void setMisCitas(ArrayList<CitaMedica> misCitas) {
		this.misCitas = misCitas;
	}
	
	
	public void agregarPersona(Persona persona) {
        misPersonas.add(persona);
    }
	
	public void agregarVacuna(Vacuna vacuna) {
        misVacunas.add(vacuna);
    }
	
	public void agregarEnfermedad(Enfermedad enfermedad) {
        misEnfermedades.add(enfermedad);
    }
	public void agregarCita (CitaMedica cita) {
        misCitas.add(cita);
    }
	public void agregarConsulta(Consulta consulta) {
        misConsultas.add(consulta);
    }
	
	public void agregarHistorial(Historial historial) {
        misHistoriales.add(historial);
    }
	
	//Probando fichero
	
	public static Clinica getClinica() {
		return clinica;
	}

	public static void setClinica(Clinica clinica) {
		Clinica.clinica = clinica;
	}
	
	public static void setLoginUser(User loginUser) {
		Clinica.loginUser = loginUser;
	}

	public static User getLoginUser() {
		return loginUser;
	}

	public void regUser(User user) {
		misUsers.add(user);
		
	}
	
	public boolean confirmLogin(String text, String text2) {
		boolean login = false;
		for (User user : JavaConnect2SQL.getInstace().getMisUsers("")) {
			if(user.getUserName().equals(text) && user.getPass().equals(text2)){
				loginUser = user;
				login = true;
			}
		}
		return login;
	}
	
	//
	
	public Persona buscarPersonaByCodigo(String codigoPersona) {
		Persona aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misPersonas.size()) {
			if(misPersonas.get(i).getCodigo().equalsIgnoreCase(codigoPersona)){
				aux = misPersonas.get(i);
				encontrado = true;
			}
			i++;
		}
		
		return aux;
	}
	
	public Doctor buscarDoctorByUser(String codigo) {
	    Doctor aux = null;
	    boolean encontrado = false;
	    int i = 0;
	    
	    while (!encontrado && i < misPersonas.size()) {
	        if (misPersonas.get(i) instanceof Doctor) {
	            Doctor doctor = (Doctor) misPersonas.get(i);
	            if (doctor.getCodigo().equals(codigo)) {
	                aux = doctor;
	                encontrado = true;
	            }
	        }
	        i++;
	    }
	    
	    return aux;
	}
	
	public Historial buscarHistorialByCedula(String cedula) {
		Historial aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misHistoriales.size()) {
			if(misHistoriales.get(i).getCedPaciente().equalsIgnoreCase(cedula)){
				aux = misHistoriales.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public Persona buscarPersonaByCedula(String cedula) {
		Persona aux = null;
		boolean encontrado = false;
		int i=0;
		if(!misPersonas.isEmpty()) {
			while (!encontrado && i<misPersonas.size()) {
				if(misPersonas.get(i).getCedula().equalsIgnoreCase(cedula)){
					aux = misPersonas.get(i);
					encontrado = true;
				}
				i++;
			}
		}		
		return aux;
	}
	
	public void eliminarUser(logico.User selected) {
		misUsers.remove(selected);
	}
	
	public void modificarUser(User miUser) {
		int index = buscarIndexUserByPersona(miUser);
		if(misUsers != null) {
			misUsers.set(index,miUser);	
		}
	}
	
	private int buscarIndexUserByPersona(User miUser) {
		int aux = -1;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misUsers.size()) {
			if(misUsers.get(i).equals(miUser)){
				aux = i;
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public void eliminarPersona(logico.Persona selected) {
		misPersonas.remove(selected);
	}
	
	public void modificarPersona(Persona miPersona) {
		int index = buscarIndexPersonaByCode(miPersona);
		if(misPersonas != null) {
			misPersonas.set(index,miPersona);	
		}
	}
	
	private int buscarIndexPersonaByCode(Persona miPersona) {
		int aux = -1;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misPersonas.size()) {
			if(misPersonas.get(i).getCodigo().equalsIgnoreCase(miPersona.getCodigo())){
				aux = i;
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public Enfermedad buscarEnfermedadByCode(String Code) {
		Enfermedad aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misEnfermedades.size()) {
			if(misEnfermedades.get(i).getCodigo().equalsIgnoreCase(Code)){
				aux = misEnfermedades.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public Enfermedad buscarEnfermedadByNom(String nom) {
		Enfermedad aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misEnfermedades.size()) {
			if(misEnfermedades.get(i).getNombre().equalsIgnoreCase(nom)){
				aux = misEnfermedades.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public CitaMedica buscarCitaByCode(String Code) {
		CitaMedica aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misCitas.size()) {
			if(misCitas.get(i).getCodigo().equalsIgnoreCase(Code)){
				aux = misCitas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public Persona buscarPersonaByNom(String nom) {
		Persona aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misPersonas.size()) {
			if(misPersonas.get(i).getNombre().equalsIgnoreCase(nom)){
				aux = misPersonas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public Vacuna buscarVacunaByNom(String nom) {
		Vacuna aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misVacunas.size()) {
			if(misVacunas.get(i).getNombre().equalsIgnoreCase(nom)){
				aux = misVacunas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public Vacuna buscarVacunaByCod(String cod) {
		Vacuna aux = null;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misVacunas.size()) {
			if(misVacunas.get(i).getCodigo().equalsIgnoreCase(cod)){
				aux = misVacunas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	
	public void eliminarEnfermedad(logico.Enfermedad selected) {
		misEnfermedades.remove(selected);
	}
	
	public void eliminarCitas(CitaMedica cita) {
		misCitas.remove(cita);
	}
	
	public void eliminarVacuna(Vacuna aux) {
		misVacunas.remove(aux);
	}
	
	public void modificarEnfermedad(Enfermedad miEnf) {
		int index= buscarIndexEnfermedadByCode(miEnf);
		misEnfermedades.set(index, miEnf);	
		
	}
	
	public void modificarCita(CitaMedica cit) {
		int index = buscarIndexCitaByCode(cit);
		misCitas.set(index, cit);
	}
	
	public void modificarVacuna(Vacuna vac) {
		int index = buscarIndexVacunaByCode(vac);
		misVacunas.set(index, vac);
	}

	private int buscarIndexVacunaByCode(Vacuna vac) {
		int aux = -1;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misVacunas.size()) {
			if(misVacunas.get(i).getCodigo().equalsIgnoreCase(vac.getCodigo())){
				aux = i;
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	private int buscarIndexCitaByCode(CitaMedica cit) {
		int aux = -1;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misCitas.size()) {
			if(misCitas.get(i).getCodigo().equalsIgnoreCase(cit.getCodigo())){
				aux = i;
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	
	private int buscarIndexEnfermedadByCode(Enfermedad miEnf) {
		int aux = -1;
		boolean encontrado = false;
		int i=0;
		while (!encontrado && i<misEnfermedades.size()) {
			if(misEnfermedades.get(i).getCodigo().equalsIgnoreCase(miEnf.getCodigo())){
				aux = i;
				encontrado = true;
			}
			i++;
		}
		return aux;
	}
	
	public int cantMujeres() {
		int count=0;
		
		for (Persona persona: JavaConnect2SQL.getInstace().getMisPacientes(""))
		{
			if (persona.getSexo()=='F'&& persona instanceof Paciente)
			{
				count++;
			}
		}
		return count;
	}
	
	public int cantHombres() {
		int count=0;
		
		for (Persona persona: JavaConnect2SQL.getInstace().getMisPacientes(""))
		{
			if (persona.getSexo()=='M' && persona instanceof Paciente)
			{
				count++;
			}
		}
		return count;
	}
	
	public int vacunaCantPacientes(Vacuna vacuna) {
		int count = 0;
		count = JavaConnect2SQL.getInstace().getCantPacxVac(vacuna);
		return count;
	}
	
	public int EnfermoCantPacientes(Enfermedad enf) {
		int count = 0;
		for (Paciente persona: JavaConnect2SQL.getInstace().getMisPacientes(""))
		{
			if (persona instanceof Paciente )
			{
				try {
					for (String enfermedad: JavaConnect2SQL.getInstace().getMisEnfermedad_Condicion_Paciente(persona))
					{
						count++;
					}
				} catch (Exception e) {
				
				}	
			}
		}
		return count;
	}
	
	public int CantPacientes() {
		int count = JavaConnect2SQL.getInstace().getMisPacientes("").size();
		return count;
	}
	
	public int CantPacientesEnfermos() {
		int count = 0;
		for (Paciente persona: JavaConnect2SQL.getInstace().getMisPacientes(""))
		{
			if (persona instanceof Paciente )
			{
				try {
					if(JavaConnect2SQL.getInstace().getMisEnfermedad_Condicion_Paciente(persona).isEmpty()) {
						count++;
					}
				} catch (Exception e) {
				
				}	
			}
		}
		return count;
	}
	
	public int CantPacientesEnfermosVig() {
		int count = 0;
		for (Enfermedad enf: JavaConnect2SQL.getInstace().getMisEnfermedades("where Estado = 1"))
		{
			count += JavaConnect2SQL.getInstace().getCantPacxEnfVig(enf);
		}
		return count;
	}
	
	public int CantPacientesEnfermosNOVig() {
		int count = 0;
		for (Enfermedad enf: JavaConnect2SQL.getInstace().getMisEnfermedades("where Estado = 0"))
		{
			count += JavaConnect2SQL.getInstace().getCantPacxEnfVig(enf);
		}
		return count;
	}
	
	public int CantCitasXDoc(Doctor doc) {
		int count = 0;
		try {
			for (CitaMedica aux : JavaConnect2SQL.getInstace().getMisCitas("")) {
				if(aux.getDoctor().getNombre().equalsIgnoreCase(doc.getNombre())) {
					count++;
				}
			}
		} catch (Exception e) {
		
		}
		
		return count;
	}
	

	public String getcodHist() {
		String mayor = "1000";
		String codigo = null;
		for (Historial aux : misHistoriales) {
			codigo = extractNumber(aux.getCodigo());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;
	}


	public String getcodCita() {
	    String mayor = "1000";
	    String codigo = null;
		ArrayList<CitaMedica> misCitas = JavaConnect2SQL.getInstace().getMisCitas("");
	    for (CitaMedica aux : misCitas) {
	        codigo = extractNumber2(aux.getCodigo());
	        if (codigo != null && codigo.compareTo(mayor) > 0) {
	            mayor = codigo;
	        }
	    }
	    String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
	    return resultado;
	}

	private String extractNumber2(String input) {
	    StringBuilder sb = new StringBuilder();
	    for (char c : input.toCharArray()) {
	        if (Character.isDigit(c)) {
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}



	public String getcodCons() {
		String mayor = "1000";
		String codigo = null;
		ArrayList<Consulta> misConsultas = JavaConnect2SQL.getInstace().getMisConsultas("");
		for (Consulta aux : misConsultas) {
			codigo = extractNumber(aux.getCodigoConsulta());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;
	}


	public String getcodEnf() {
		String mayor = "1000";
		String codigo = null;
		ArrayList<Enfermedad> misEnfermedades = JavaConnect2SQL.getInstace().getMisEnfermedades("");
		for (Enfermedad aux : misEnfermedades) {
			codigo = extractNumber(aux.getCodigo());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;	
	}
	

	public String getcodVac() {
		String mayor = "1000";
		String codigo = null;
		ArrayList<Vacuna> misVacunas = JavaConnect2SQL.getInstace().getMisVacunas("");
		for (Vacuna aux : misVacunas) {
			codigo = extractNumber(aux.getCodigo());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;	
	}

	public String getcodPac() {
		String mayor = "1000";
		String codigo = null;
		ArrayList<Paciente> misPersonas = JavaConnect2SQL.getInstace().getMisPacientes("");
		for (Paciente aux : misPersonas) {
			codigo = extractNumber(aux.getCodigo());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;
	}
	
	public String getcodEmp() {
		String mayor = "1000";
		String codigo = null;
		ArrayList<Empleado> misPersonas = JavaConnect2SQL.getInstace().getMisEmpleado("");
		for (Empleado aux : misPersonas) {
			codigo = extractNumber(aux.getCodigo());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;
	}
	
	public String getcodDoc() {
		String mayor = "1000";
		String codigo = null;
		ArrayList<Doctor> misPersonas = JavaConnect2SQL.getInstace().getMisDoctor("");
		for (Doctor aux : misPersonas) {
			codigo = extractNumber(aux.getCodigo());
			if(codigo.compareTo(mayor) > 0) {
				mayor = codigo;
			}
		}
		String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
		return resultado;
	}
	
	
	
	public static String extractNumber(final String str) {                
		if(str == null || str.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c : str.toCharArray()){
		    if(Character.isDigit(c)){
		        sb.append(c);
		        found = true;
		    } else if(found){
		        // If we already found a digit before and this char is not a digit, stop looping
		        break;                
		    }
		}
		return sb.toString();
	}
	
	public ArrayList<Sintoma> getMisSintomas() {
        return misSintomas;
    }
    public void setMisSintomas(ArrayList<Sintoma> misSintomas) {
        this.misSintomas = misSintomas;
    }


    public String getcodSin() {
        String mayor = "1000";
        String codigo = null;
        for (Sintoma aux : misSintomas) {
            codigo = extractNumber(aux.getCodigo());
            if(codigo.compareTo(mayor) > 0) {
                mayor = codigo;
            }
        }
        String resultado = Integer.toString(Integer.parseInt(mayor) + 1);
        return resultado;
    }
	
	
}
	



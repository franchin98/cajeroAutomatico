package externas;

public class Persona {
	private String nombre;
	private String apellido;
	private String localidad;
	private int edad;
	private int dni;
	private long telefono;
	
	public Persona(String nombre, String apellido, String localidad, int edad, int dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.localidad = localidad;
		this.edad = edad;
		this.dni = dni;
	}

	public String getNombre() {
		return nombre + " " + apellido;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	
	
	
}

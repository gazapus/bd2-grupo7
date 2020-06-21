package datos;

public class Cliente {
	private int dni;
	private String nombre;
	private String apellido;
	private int nroAfiliado;
	private Domicilio domicilio;
	private ObraSocial obraSocial;
	
	public Cliente( int dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + "]";
	}
	
	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}

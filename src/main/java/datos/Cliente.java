package datos;

public class Cliente {
	
	private int dni;
	private String nombre;
	private String apellido;
	private int nroAfiliado;
	private Domicilio domicilio;
	private ObraSocial obraSocial;
	
	public Cliente(int dni, String nombre, String apellido, int nroAfiliado, Domicilio domicilio,
			ObraSocial obraSocial) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nroAfiliado = nroAfiliado;
		this.domicilio = domicilio;
		this.obraSocial = obraSocial;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getNroAfiliado() {
		return nroAfiliado;
	}
	public void setNroAfiliado(int nroAfiliado) {
		this.nroAfiliado = nroAfiliado;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public ObraSocial getObraSocial() {
		return obraSocial;
	}
	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}
	
	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", nroAfiliado=" + nroAfiliado
				+ ", domicilio=" + domicilio + ", obraSocial=" + obraSocial + "]";
	}
}

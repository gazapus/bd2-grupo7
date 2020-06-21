package datos;

public class ObraSocial {
	
	private int cuit;
	private String nombre;
	
	public ObraSocial(int cuit, String nombre) {
		this.cuit = cuit;
		this.nombre = nombre;
	}
	
	public int getCuit() {
		return cuit;
	}
	public void setCuit(int cuit) {
		this.cuit = cuit;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "ObraSocial [cuit=" + cuit + ", nombre=" + nombre + "]";
	}
}

package pojos;


public class ObraSocial {
	
	private String cuit;
	private String nombre;
	
	public ObraSocial(String cuit, String nombre) {
		this.cuit = cuit;
		this.nombre = nombre;
	}
	
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
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

package datos;

public class FormaDePago {
	
	private int codigo;
	private String nombre;
	
	public FormaDePago(int codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "FormaDePago [codigo=" + codigo + ", nombre=" + nombre + "]";
	}
}

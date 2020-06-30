package pojos;


public class Producto {
	private int codigo;
	private String descripcion;
	private float precio;
	private Laboratorio laboratorio;
	private TipoDeProducto tipo;
	
	public Producto(int codigo, String descripcion, float precio, Laboratorio laboratorio, TipoDeProducto tipo) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.laboratorio = laboratorio;
		this.tipo = tipo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public TipoDeProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeProducto tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", descripcion=" + descripcion + ", precio=" + precio + ", laboratorio="
				+ laboratorio + ", tipo=" + tipo + "]";
	}
}

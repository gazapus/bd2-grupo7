package pojos;


import java.util.ArrayList;
import java.util.List;

public class Sucursal {
	
	private int codigo;
	private String nombre;
	private Domicilio domicilio;
	private List<Empleado> empleados;
	
	public Sucursal(int codigo, String nombre, Domicilio domicilio, List<Empleado> empleados) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.empleados = empleados;
	}

	public Sucursal() {
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

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	@Override
	public String toString() {
		return "{" +
			" codigo='" + getCodigo() + "'" +
			", nombre='" + getNombre() + "'" +
			", domicilio='" + getDomicilio() + "'" +
			", empleados='" + getEmpleados() + "'" +
			"}";
	}
	
}

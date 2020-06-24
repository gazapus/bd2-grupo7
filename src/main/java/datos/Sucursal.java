package datos;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
	
	private int codigo;
	private String nombre;
	private Domicilio domicilio;
	private List<Empleado> empleados;
	private List<Venta> ventas;
	
	public Sucursal(int codigo, String nombre, Domicilio domicilio, List<Empleado> empleados, List<Venta> ventas) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.empleados = empleados;
		this.ventas = ventas;
	}
	
	public Sucursal(int codigo, String nombre, Domicilio domicilio) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.empleados = new ArrayList <Empleado> ();
		this.ventas = new ArrayList <Venta> ();
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

	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	@Override
	public String toString() {
		return "Sucursal [codigo=" + codigo + ", nombre=" + nombre + ", domicilio=" + domicilio + ", empleados="
				+ empleados + ", ventas=" + ventas + "]";
	}
}

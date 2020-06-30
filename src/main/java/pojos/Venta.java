package pojos;

import java.time.LocalDate;
import java.util.List;

public class Venta {
	
	private String nroTicket;
	private LocalDate fecha;
	private float total;
	private FormaDePago formaDePago;
	private Empleado empleadoAtencion;
	private Empleado empleadoCobro;
	private Cliente cliente;
	private Sucursal sucursal;
	private List<Item> items;
	
	public Venta(String nroTicket, LocalDate fecha, FormaDePago formaDePago, Empleado empleadoAtencion,
			Empleado empleadoCobro, Sucursal sucursal, Cliente cliente, List<Item> items) {
		this.nroTicket = nroTicket;
		this.fecha = fecha;
		this.formaDePago = formaDePago;
		this.empleadoAtencion = empleadoAtencion;
		this.empleadoCobro = empleadoCobro;
		this.sucursal = sucursal;
		this.cliente = cliente;
		this.sucursal = sucursal;
		this.items = items;
		this.total = calcularTotal();
	}

	public String getNroTicket() {
		return this.nroTicket;
	}

	public void setNroTicket(String nroTicket) {
		this.nroTicket = nroTicket;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public float getTotal() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public FormaDePago getFormaDePago() {
		return this.formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}

	public Empleado getEmpleadoAtencion() {
		return this.empleadoAtencion;
	}

	public void setEmpleadoAtencion(Empleado empleadoAtencion) {
		this.empleadoAtencion = empleadoAtencion;
	}

	public Empleado getEmpleadoCobro() {
		return this.empleadoCobro;
	}

	public void setEmpleadoCobro(Empleado empleadoCobro) {
		this.empleadoCobro = empleadoCobro;
	}

	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "{" +
			" nroTicket='" + getNroTicket() + "'" +
			", fecha='" + getFecha() + "'" +
			", total='" + getTotal() + "'" +
			", formaDePago='" + getFormaDePago() + "'" +
			", empleadoAtencion='" + getEmpleadoAtencion() + "'" +
			", empleadoCobro='" + getEmpleadoCobro() + "'" +
			", sucursal='" + getSucursal() + "'" +
			", cliente='" + getCliente() + "'" +
			", items='" + getItems() + "'" +
			"}";
	}	
	
	private float calcularTotal() {
		float total = 0;
		for(Item item: items) {
			total += item.getCantidad() * item.getProducto().getPrecio();
		}
		return total;
	}
}

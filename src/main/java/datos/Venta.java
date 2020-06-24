package datos;

import java.time.LocalDate;
import java.util.List;

public class Venta {
	
	private int nroTicket;
	private LocalDate fecha;
	private float total;
	private FormaDePago formaDePago;
	private Empleado empleadoAtencion;
	private Empleado empleadoCobro;
	private Cliente cliente;
	private List<Item> items;
	
	public Venta(int nroTicket, LocalDate fecha, float total, FormaDePago formaDePago, Empleado empleadoAtencion,
			Empleado empleadoCobro, Cliente cliente, List<Item>items) {
		this.nroTicket = nroTicket;
		this.fecha = fecha;
		this.total = total;
		this.formaDePago = formaDePago;
		this.empleadoAtencion = empleadoAtencion;
		this.empleadoCobro = empleadoCobro;
		this.cliente = cliente;
		this.items = items;
	}

	public int getNroTicket() {
		return nroTicket;
	}

	public void setNroTicket(int nroTicket) {
		this.nroTicket = nroTicket;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public FormaDePago getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(FormaDePago formaDePago) {
		this.formaDePago = formaDePago;
	}

	public Empleado getEmpleadoAtencion() {
		return empleadoAtencion;
	}

	public void setEmpleadoAtencion(Empleado empleadoAtencion) {
		this.empleadoAtencion = empleadoAtencion;
	}

	public Empleado getEmpleadoCobro() {
		return empleadoCobro;
	}

	public void setEmpleadoCobro(Empleado empleadoCobro) {
		this.empleadoCobro = empleadoCobro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Venta [nroTicket=" + nroTicket + ", fecha=" + fecha + ", total=" + total + ", formaDePago="
				+ formaDePago + ", empleadoAtencion=" + empleadoAtencion + ", empleadoCobro=" + empleadoCobro
				+ ", cliente=" + cliente + ", items=" + items + "]";
	}
}

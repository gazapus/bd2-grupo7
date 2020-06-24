package datos;

public class Empleado extends Persona {

	private String cuil;
	private boolean encargado;
	
	public Empleado(int dni, String nombre, String apellido, int nroAfiliado, Domicilio domicilio,
			ObraSocial obraSocial, String cuil, boolean encargado) {
		super(dni, nombre, apellido, nroAfiliado, domicilio, obraSocial);
		this.cuil = cuil;
		this.encargado = encargado;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public boolean isEncargado() {
		return encargado;
	}

	public void setEncargado(boolean encargado) {
		this.encargado = encargado;
	}

	@Override
	public String toString() {
		return "Empleado [cuil=" + cuil + ", encargado=" + encargado + ", getDni()=" + getDni() + ", getNombre()="
				+ getNombre() + ", getApellido()=" + getApellido() + ", getNroAfiliado()=" + getNroAfiliado()
				+ ", getDomicilio()=" + getDomicilio() + ", getObraSocial()=" + getObraSocial() + "]";
	}
}

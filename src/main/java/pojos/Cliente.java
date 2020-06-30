package pojos;


public class Cliente extends Persona {

	public Cliente(int dni, String nombre, String apellido, int nroAfiliado, Domicilio domicilio,
			ObraSocial obraSocial) {
		super(dni, nombre, apellido, nroAfiliado, domicilio, obraSocial);
	}

	@Override
	public String toString() {
		return "Cliente [getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getApellido()=" + getApellido()
				+ ", getNroAfiliado()=" + getNroAfiliado() + ", getDomicilio()=" + getDomicilio() + ", getObraSocial()="
				+ getObraSocial() + "]";
	}
}

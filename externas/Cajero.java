package externas;

public class Cajero {
	private Cuenta cuentasQueIngresan[];
	private String nombreCajero;
	
	public Cajero(String nombreBanco) {
		nombreCajero = nombreBanco;
		cuentasQueIngresan = new Cuenta[2];
	}
	
	public Cuenta[] getCuentasQueIngresan() {
		return cuentasQueIngresan;
	}
	
	public void ingresarNuevaCuenta(int posicionLibre, Cuenta cuentaARegistrar) {
		cuentasQueIngresan[posicionLibre] = cuentaARegistrar;
	}
	
	public void setCuentasQueIngresan(Cuenta[] cuentasQueIngresan) {
		this.cuentasQueIngresan = cuentasQueIngresan;
	}

	public String getNombreCajero() {
		return nombreCajero;
	}

	public void setNombreCajero(String nombreCajero) {
		this.nombreCajero = nombreCajero;
	}
	
	
}

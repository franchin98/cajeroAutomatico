package externas;

public class Cuenta {
	private Persona titular;
	private String numCuenta;
	private double saldo;
	private long cbu;
	private String contrasenia; 

	public Cuenta(Persona personaTitular) {
		titular = personaTitular;
		saldo = 0.0D;
		numCuenta = String.valueOf((int) (Math.random() * 10000)) + "-" + String.valueOf((int) (Math.random() * 100000))
				+ "/" + String.valueOf((int) (Math.random() * 10));
		cbu = (long) (Math.random() * 1000000000) + (long)(Math.random() * 1000000000);
	}

	public String getNombreTitular() {
		return titular.getNombre();
	}
	
	public int getDniTiular() {
		return titular.getDni();
	}
	

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public long getCbu() {
		return cbu;
	}
	
	public void depositar(double importe) {
		if(importe >= 100) {
			saldo += importe;
		}
	}
	
	public void debitar(double importe) {
		if(saldo >= importe) {
			saldo -= importe;
		}
	}
}

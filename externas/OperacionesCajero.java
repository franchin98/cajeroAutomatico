package externas;

import java.util.Scanner;

public class OperacionesCajero {
	private static final int DEPOSITOS = 1, EXTRACCIONES = 2, CONSULTAR_SALDO = 3, TRANSFERENCIAS = 4, SALIR = 5;

	public static Cuenta validarDni(Cajero cajeroEnUso, int dniIngresado) {
		Cuenta dniEncontrado = null;
		for (int i = 0; i < cajeroEnUso.getCuentasQueIngresan().length; i++) {
			if (cajeroEnUso.getCuentasQueIngresan()[i] != null) {
				dniEncontrado = cajeroEnUso.getCuentasQueIngresan()[i].getDniTiular() == dniIngresado
						? cajeroEnUso.getCuentasQueIngresan()[i]
						: dniEncontrado;
			}
		}

		if (dniEncontrado != null) {
			return dniEncontrado;
		}

		return null;
	}

	public static boolean validarContrasenia(String contrasenia, Cuenta cuentaEnUso) {
		return cuentaEnUso.getContrasenia().equals(contrasenia);
	}

	private static boolean transferir(Cuenta cuentaOrigen, Cuenta cuentaDestino, double importe) {

		if (cuentaOrigen.getSaldo() >= importe) {
			cuentaOrigen.debitar(importe);
			cuentaDestino.depositar(importe);
			return true;
		}

		return false;
	}

	public static void operacionARealizar(Cuenta cuentaEnUso, Scanner leerDatos, Cajero cajeroEnUso,
			int opcionElegida) {
		double importe = 0.0D;
		switch (opcionElegida) {
		case DEPOSITOS:
			System.out.println("\n************ DEPÓSITOS ************");
			do {
				System.out.print("\nINGRESE EL IMPORTE QUE DESEA DEPOSITAR: ");
				importe = leerDatos.nextDouble();
				if (importe < 100.00)
					System.out.println("\nEL IMPORTE MÍNIMO PARA DEPÓSITOS ES DE $100,00.");
			} while (importe < 100.00);

			cuentaEnUso.depositar(importe);
			System.out.println("\n--------------------------------");
			System.out.println("IMPORTE DEPOSITADO: " + importe);
			System.out.println("SALDO ANTERIOR: " + (cuentaEnUso.getSaldo() - importe));
			System.out.println("SALDO ACTUAL: " + cuentaEnUso.getSaldo());
			System.out.println("--------------------------------");

			break;

		case EXTRACCIONES:
			System.out.println("\n*********** EXTRACCIONES ***********");
			do {
				System.out.print("\nINGRESE EL IMPORTE QUE DESEA RETIRAR: ");
				importe = leerDatos.nextDouble();
			} while (importe < 100.00);

			if (cuentaEnUso.getSaldo() >= importe) {
				cuentaEnUso.debitar(importe);
				System.out.println("\n------------------------------");
				System.out.println("IMPORTE RETIRADO: " + importe);
				System.out.println("SALDO ANTERIOR: " + (cuentaEnUso.getSaldo() + importe));
				System.out.println("SALDO ACTUAL: " + cuentaEnUso.getSaldo());
				System.out.println("------------------------------");
			} else
				System.out.println("\n¡SALDO INSUFICIENTE!");

			break;

		case CONSULTAR_SALDO:
			System.out.println("\n*********** CONSULTA DE SALDO ***********");

			System.out.println("\n-----------------------------------------");
			System.out.println("TITULAR: " + cuentaEnUso.getNombreTitular());
			System.out.println("CUENTA: " + cuentaEnUso.getNumCuenta());
			System.out.println("SALDO: " + cuentaEnUso.getSaldo());
			System.out.println("-----------------------------------------");

			break;

		case TRANSFERENCIAS:
			int dni = 0;
			Cuenta cuentaDestino = null;
			char ingresarOtroDni = ' ';

			System.out.println("\n**************** TRANSFERENCIAS ****************");
			do {
				System.out.print("\nINGRESE EL DNI DE LA PERSONA A TRANSFERIR: ");
				dni = leerDatos.nextInt();

				cuentaDestino = validarDni(cajeroEnUso, dni);

				if (cuentaDestino == null) {
					System.out.println("\n¡EL DNI INGRESADO ES INVÁLIDO!");
					System.out.println("¿DESEA INGRESAR OTRO DNI?");
					do {
						System.out.println("\nINGRESE \"S\" PARA INGRESAR OTRO DNI." + "\nINGRESE \"N\" PARA SALIR.");
						System.out.print("INGRESE SU RESPUESTA: ");
						ingresarOtroDni = leerDatos.next().toUpperCase().charAt(0);

						if (ingresarOtroDni != 'S' && ingresarOtroDni != 'N') {
							System.out.println("\nRESPUESTA INCORRECTA.");
						}

					} while (ingresarOtroDni != 'S' && ingresarOtroDni != 'N');

				}

			} while (ingresarOtroDni == 'S');
			
			if(ingresarOtroDni == 'N') {
				break;
			}
			
			System.out.println("\n******** CONFIRMACIÓN DE LA TRANSFERENCIA ********");
			System.out.println("-------------------------------------------");
			System.out.println("TITULAR: " + cuentaDestino.getNombreTitular());
			System.out.println("CUENTA DESTINO: " + cuentaDestino.getNumCuenta());
			System.out.println("-------------------------------------------");

			do {
				System.out.print("\nINGRESE EL IMPORTE A TRANSFERIR: ");
				importe = leerDatos.nextDouble();

				if (cuentaEnUso.getSaldo() < importe) {
					System.out.println("\n¡SALDO INSUFICIENTE!");
				}

			} while (!(cuentaEnUso.getSaldo() == 0.0) && cuentaEnUso.getSaldo() < importe);

			if (transferir(cuentaEnUso, cuentaDestino, importe)) {
				System.out.println("\n---------------------------------------");
				System.out.println("TRANSACCIÓN REALIZADA CON ÉXITO.");
				System.out.println("SALDO ANTERIOR: " + (cuentaEnUso.getSaldo() + importe));
				System.out.println("SALDO ACTUAL: " + cuentaEnUso.getSaldo());
				System.out.println("---------------------------------------");
			} 
			break;
		case SALIR:
			System.out.println("\n**************** GRACIAS POR ELEGIRNOS. HASTA LUEGO ****************");
			break;

		default:
			System.out.println("\n¡OPCIÓN INVÁLIDA!");
			break;
		}
	}

}

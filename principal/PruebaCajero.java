package principal;

import externas.Cajero;
import externas.Cuenta;
import externas.OperacionesCajero;
import externas.Persona;
import java.util.Scanner;

public class PruebaCajero {

	private static final int REGISTRAR_CUENTAS = 1, SALDO_MAS_ALTO = 2, LISTADO_DE_CUENTAS = 3, INGRESAR_AL_ATM = 4;

	public static void main(String[] args) {
		Cajero bancoProvincia = new Cajero("Banco Provincia de Buenos Aires");
		Scanner leerDatos = new Scanner(System.in);
		int opcionIngresada = 0;

		do {
			opcionIngresada = menuPrincipal(leerDatos);
			determinarLaOperacionARealizar(bancoProvincia, leerDatos, opcionIngresada);
		} while (opcionIngresada != 0);

	}

	private static int menuCajero(Scanner leerDatos) {
		int opcionCajero = 0;
		
			System.out.println("\n*************** OPERACIONES DISPONIBLES ***************");
			System.out.println("1. DEPOSITOS");
			System.out.println("2. EXTRACCIONES");
			System.out.println("3. CONSULTAR SALDO");
			System.out.println("4. TRANSFERENCIAS");
			System.out.println("5. SALIR");

			System.out.print("\nINGRESE SU RESPUESTA: ");
			opcionCajero = leerDatos.nextInt();

		return opcionCajero;
	}

	private static int menuPrincipal(Scanner leerDatos) {
		int opcionElegida = 0;

		do {
			System.out.println("\n***************BIENVENIDO/A***************");
			System.out.println("------------------MENÚ---------------------");
			System.out.println("1. REGISTRAR NUEVAS CUENTAS.");
			System.out.println("2. PERSONA QUE TIENE EL SALDO MÁS ALTO.");
			System.out.println("3. MOSTRAR TODAS LAS PERSONAS QUE POSEEN CUENTAS.");
			System.out.println("4. INGRESAR AL CAJERO AUTOMÁTICO.");

			System.out.println("\nINGRESE SU RESPUESTA: ");
			opcionElegida = leerDatos.nextInt();

			if (opcionElegida < 1 || opcionElegida > 4) {
				System.out.println("\nOPCIÓN INCORRECTA. VUELVA A INGRESAR LA RESPUESTA.");
				System.out.println();
			}

		} while (opcionElegida < 1 || opcionElegida > 4);

		return opcionElegida;

	}

	private static void determinarLaOperacionARealizar(Cajero cajeroEnUso, Scanner leerDatos, int opcionElegida) {
		switch (opcionElegida) {
		case REGISTRAR_CUENTAS:
			System.out.println("\n----------------------------------");
			System.out.println("\tALTA DE CUENTAS");
			System.out.println("----------------------------------");

			System.out.print("INGRESE LA CANTIDAD DE CUENTAS QUE QUIERE REGISTRAR: ");
			int cuentasARegistrar = leerDatos.nextInt();

			registrarCuentasBancarias(cajeroEnUso, leerDatos, cuentasARegistrar);

			break;

		case SALDO_MAS_ALTO:
			Cuenta cuentaQuePoseeElSaldoMayor = saldoMasAlto(cajeroEnUso);

			if (cuentaQuePoseeElSaldoMayor != null) {
				System.out.println("\n--------------------------------");
				System.out.println("\tSALDO MAYOR");
				System.out.println("--------------------------------");

				System.out.println("\n*****************************************");
				System.out.println("NOMBRE Y APELLIDO: " + cuentaQuePoseeElSaldoMayor.getNombreTitular());
				System.out.println("DNI: " + cuentaQuePoseeElSaldoMayor.getDniTiular());
				System.out.println("SALDO: " + cuentaQuePoseeElSaldoMayor.getSaldo());
				System.out.println("*****************************************");
			} else {
				System.out.println("\nDEBE DECLARAR LAS CUENTAS PRIMERO PARA CONSULTAR EL SALDO MAYOR.");
			}

			break;

		case LISTADO_DE_CUENTAS:
			if (contarEspaciosDisponibles(cajeroEnUso) != cajeroEnUso.getCuentasQueIngresan().length)
				mostrarCuentas(cajeroEnUso);
			else
				System.out.println("\n¡DEBE REGISTRAR CUENTAS PARA ESTA OPERACIÓN!");

			break;

		case INGRESAR_AL_ATM:
			int dniBuscado = 0, vecesQueFallaElUsuario, opcionCajero = 0;
			Cuenta cuentaValidada;
			String contrasenia = "";
			boolean validaContrasenia;

			if (contarEspaciosDisponibles(cajeroEnUso) != cajeroEnUso.getCuentasQueIngresan().length) {
				do {
					validaContrasenia = false;
					vecesQueFallaElUsuario = 0;
					System.out.println("\n****************** BIENVENIDO/A ******************");

					do {
						System.out.print("INGRESE SU DNI: ");
						dniBuscado = leerDatos.nextInt();
						cuentaValidada = OperacionesCajero.validarDni(cajeroEnUso, dniBuscado);

						if (cuentaValidada == null) {
							System.out.println("\n¡DNI INVÁLIDO!");
							System.out.println();
						}

					} while (cuentaValidada == null);
					
					// limpia el buffer vacío
					leerDatos.nextLine();
					do {
						System.out.print("INGRESE SU CONTRASEÑA: ");
						contrasenia = leerDatos.nextLine();

						validaContrasenia = OperacionesCajero.validarContrasenia(contrasenia, cuentaValidada);

						if (validaContrasenia == false) {
							vecesQueFallaElUsuario++;
							System.out.println("\n¡CONTRASEÑA INCORRECTA!");
						}
					} while (validaContrasenia == false && vecesQueFallaElUsuario < 3);
				} while (vecesQueFallaElUsuario == 3);

				do {
					opcionCajero = menuCajero(leerDatos);

					OperacionesCajero.operacionARealizar(cuentaValidada, leerDatos, cajeroEnUso, opcionCajero);
				} while (opcionCajero != 5);

			} else {
				System.out.println("\n¡DEBE REGISTRAR AL MENOS UNA CUENTA!");
			}
			break;
		}
	}

	private static void mostrarCuentas(Cajero cajeroEnUso) {
		for (int i = 0; i < cajeroEnUso.getCuentasQueIngresan().length; i++) {
			if (cajeroEnUso.getCuentasQueIngresan()[i] != null) {
				System.out.println("\n----------------------------------");
				System.out.println("NOMBRE Y APELLIDO: " + cajeroEnUso.getCuentasQueIngresan()[i].getNombreTitular());
				System.out.println("DNI: " + cajeroEnUso.getCuentasQueIngresan()[i].getDniTiular());
				System.out.println("----------------------------------");
			} else {
				continue;
			}
		}
	}

	private static Cuenta saldoMasAlto(Cajero cajeroEnUso) {
		Cuenta cuentaConMasSaldo = cajeroEnUso.getCuentasQueIngresan()[0];

		for (int i = 0; i < cajeroEnUso.getCuentasQueIngresan().length; i++) {
			if (cajeroEnUso.getCuentasQueIngresan()[i] != null) {
				cuentaConMasSaldo = cajeroEnUso.getCuentasQueIngresan()[i].getSaldo() > cuentaConMasSaldo.getSaldo()
						? cajeroEnUso.getCuentasQueIngresan()[i]
						: cuentaConMasSaldo;
			} else {
				continue;
			}
		}

		if (cuentaConMasSaldo != null) {
			return cuentaConMasSaldo;
		}

		return null;
	}

	private static boolean puedeRegistrarNuevasCuentas(Cajero cajeroQuePoseeLasCuentas) {

		for (int i = 0; i < cajeroQuePoseeLasCuentas.getCuentasQueIngresan().length; i++) {
			if (cajeroQuePoseeLasCuentas.getCuentasQueIngresan()[i] == null) {
				return true;
			}
		}

		return false;
	}

	private static int encontrarLugarEnLaLista(Cajero nuevoCajero) {

		for (int i = 0; i < nuevoCajero.getCuentasQueIngresan().length; i++) {
			if (nuevoCajero.getCuentasQueIngresan()[i] == null) {
				return i;
			}
		}

		return -1;
	}

	private static int contarEspaciosDisponibles(Cajero cajeroEnUso) {
		int espaciosLibres = cajeroEnUso.getCuentasQueIngresan().length;

		for (int i = 0; i < cajeroEnUso.getCuentasQueIngresan().length; i++) {
			if (cajeroEnUso.getCuentasQueIngresan()[i] != null) {
				espaciosLibres--;
			}
		}

		return espaciosLibres;

	}

	private static void registrarCuentasBancarias(Cajero nuevoCajero, Scanner leerDatos, int cuentasARegistrar) {
		Cuenta nuevaCuenta;
		Persona titularDeLaCuenta;
		String nombreTitular = "", apellidoTitular = "", localidad = "";
		int edadTitular = 0, dniTitular = 0;
		long telefono = 0;

		if (puedeRegistrarNuevasCuentas(nuevoCajero) && cuentasARegistrar <= contarEspaciosDisponibles(nuevoCajero)) {
			leerDatos.nextLine();
			for (int i = 0; i < cuentasARegistrar; i++) {
				System.out.println("\n-----------------------------");
				System.out.println("\tNUEVA CUENTA");
				System.out.println("-----------------------------");

				System.out.print("\nNOMBRE DEL TITULAR: ");
				nombreTitular = leerDatos.nextLine();

				System.out.print("APELLIDO DEL TITULAR: ");
				apellidoTitular = leerDatos.nextLine();

				System.out.print("LOCALIDAD: ");
				localidad = leerDatos.nextLine();

				do {
					System.out.print("EDAD DEL TITULAR: ");
					edadTitular = leerDatos.nextInt();

					if (edadTitular < 16) {
						System.out.println("\nLA EDAD DEL TITULAR DEBE SER MAYOR A 16." + "\nREINGRESE LA EDAD.");
						System.out.println();
					}

				} while (edadTitular < 16);

				System.out.print("DNI DEL TITULAR: ");
				dniTitular = leerDatos.nextInt();

				titularDeLaCuenta = new Persona(nombreTitular, apellidoTitular, localidad, edadTitular, dniTitular);

				System.out.print("INGRESE EL TELEFONO DE CONTACTO: ");
				telefono = leerDatos.nextLong();

				titularDeLaCuenta.setTelefono(telefono);

				nuevaCuenta = new Cuenta(titularDeLaCuenta);

				System.out.print("INGRESE UNA CONTRASEÑA PARA SU CUENTA: ");
				String contrasenia = leerDatos.next();

				nuevaCuenta.setContrasenia(contrasenia);

				int k = encontrarLugarEnLaLista(nuevoCajero);

				nuevoCajero.ingresarNuevaCuenta(k, nuevaCuenta);

				leerDatos.nextLine();
			}

		} else if (cuentasARegistrar > contarEspaciosDisponibles(nuevoCajero)
				&& contarEspaciosDisponibles(nuevoCajero) != 0) {

			do {
				System.out.println("\nEL NÚMERO DE CUENTAS NUEVAS A REGISTRAR DEBE SER MENOR O IGUAL A "
						+ contarEspaciosDisponibles(nuevoCajero));
				System.out.print("\nINGRESE EL NÚMERO DE CUENTAS NUEVAMENTE: ");
				cuentasARegistrar = leerDatos.nextInt();

			} while (cuentasARegistrar > contarEspaciosDisponibles(nuevoCajero));

			registrarCuentasBancarias(nuevoCajero, leerDatos, cuentasARegistrar);
		}

		else {
			System.out.println("\n¡LISTA DE CUENTAS LLENA!" + "\nNO PUEDE REGISTRAR MÁS CUENTAS.");
		}

	}

}

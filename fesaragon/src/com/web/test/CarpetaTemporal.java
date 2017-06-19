package com.web.test;

public class CarpetaTemporal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("El directorio temporal del sistema es (java.io.tmpdir)"+ System.getProperty("java.io.tmpdir"));
		System.out.println("El directorio temporal del sistema es (user.home)"+ System.getProperty("user.home"));
		System.out.println("El directorio temporal del sistema es (user.dir)"+ System.getProperty("user.dir"));
		System.out.println("El directorio temporal del sistema es (user.name)"+ System.getProperty("user.name"));
		System.out.println("El directorio temporal del sistema es (file.separator):"+ System.getProperty("file.separator"));
		System.out.println("Ejemplo Viewer:");
		System.out.println(System.getProperty("org.jpedal.Viewer.Prefs"));
				
	}

}

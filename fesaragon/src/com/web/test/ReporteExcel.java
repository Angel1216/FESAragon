package com.web.test;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ReporteExcel {

	private static DataModel dataModelCabecera;
	private static DataModel dataModelContenido;
	 

	
	public void dataModelReporte() {
		 List listaFinal = new ArrayList();
		 List listaCabeceras = new ArrayList();
		  
		 listaCabeceras.add("ID");
		 listaCabeceras.add("TIPO");
		 listaCabeceras.add("TIPO REPORTE");
		 listaCabeceras.add("TUTORIAL");
		 listaCabeceras.add("PAGINA");
		 listaCabeceras.add("DIFICULTAD");
		 this.setDataModelCabecera(new ListDataModel(
		   listaCabeceras));
		 try {
		  List listaFila = null;
		   
		  for (int i = 0; i < 3; i++) {
		   listaFila = new ArrayList();
		   listaFila.add(" " + i);
		   listaFila.add("JAVA " + i);
		   listaFila.add("Excel " + i);
		   listaFila.add("Si " + i);
		   listaFila.add("datojava.blogspot.com " + i);
		   listaFila.add("Facil " + i);
		   listaFinal.add(listaFila);
		  }
		  this.setDataModelContenido(new ListDataModel(listaFinal));
		 }
		 catch (Exception ex) {
		  ex.printStackTrace();
		 } finally {
		  listaFinal = new ArrayList();
		 }
		}
	
	
	public static HSSFWorkbook obtenerExcel(DataModel contenidoCeldas,
			  DataModel cabecerasCeldas, String nombreHoja) {
			 
			 HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			 HSSFSheet hssfSheet = hssfWorkbook.createSheet(nombreHoja);
			 int numeroFila = 0;
			 int numeroColumna = 0;
			 
			 // Crear una nueva fila
			 HSSFRow hssfRow = hssfSheet.createRow(numeroFila++);
			 
			 // Estilo de la cabecera
			 HSSFCellStyle hssfCellStyleCabecera = hssfWorkbook.createCellStyle();
			 hssfCellStyleCabecera.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			 hssfCellStyleCabecera.setFillBackgroundColor(new HSSFColor.BLACK()
			   .getIndex());
			 
			 // Crear la fuente de la cabecera
			 HSSFFont hssfFont = hssfWorkbook.createFont();
			 hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			 hssfFont.setColor(HSSFColor.WHITE.index);
			 
			 // Aplicarle la fuente al estilo de la cabecera
			 hssfCellStyleCabecera.setFont(hssfFont);
			 
			 String columnaCabecera;
			 
			 // Creacion de la celda
			 HSSFCell hssfCell = null;
			 
			 // Pasar los nombres de cabeceras a una lista
			 List cabecerasExcel = (List) cabecerasCeldas.getWrappedData();
			 
			 // Agregar los nombres de las cabeceras a el excel
			 for (int i = 0; i < cabecerasExcel.size(); i++) {
			  columnaCabecera = (String) cabecerasExcel.get(i);
			  hssfCell = hssfRow.createCell((short) numeroColumna++);
			  hssfCell.setCellValue(columnaCabecera);
			 
			  // Agregar el estilo que creamos antes
			  hssfCell.setCellStyle(hssfCellStyleCabecera);
			  //hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);
			 }
			 
			 // Pasar el contenido del excel a una lista
			 List contenidoExcel = (List) contenidoCeldas.getWrappedData();
			 List fila = null;
			 Object valor;
			 
			 for (int i = 0; i < contenidoExcel.size(); i++) {
			  // Obtener el contenido por fila
			  fila = (List) contenidoExcel.get(i);
			 
			  // Crear la fila
			  hssfRow = hssfSheet.createRow(numeroFila++);
			  numeroColumna = 0;
			  for (int x = 0; x < fila.size(); x++) {
			   // Obtener el valor de cada celda
			   valor = fila.get(x);
			 
			   // Insertarlo en la celda
			   hssfCell = hssfRow.createCell((short) numeroColumna++);
			   //hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   hssfCell.setCellValue((String) valor);
			  }
			 }
			 return hssfWorkbook;
			}
	
	
	
		public static void main(String[] args){
			
			ReporteExcel excel = new ReporteExcel();
			
			// Llenar la cabecera y el contenido del excel
			excel.dataModelReporte();
			 
			 // Hacer el excel con su cabecera y contenido
			 HSSFWorkbook hssfWorkbook = obtenerExcel(dataModelContenido,dataModelCabecera, "datojava.blogspot.com");
			 
			 try {
			  // Guardar el Archivo
			  String urlArchivo = System.getProperty("java.io.tmpdir");
			  String nombreArchivo = "Angel.xls";
			  String pathArchivo = urlArchivo + nombreArchivo;
			  
			  
			  
		      System.out.println("Ruta de almacenamiento del Reporte Excel: " + pathArchivo);
			  FileOutputStream fileOutputStream = new FileOutputStream(pathArchivo);
			  hssfWorkbook.write(fileOutputStream);
			  fileOutputStream.close();
		     
			  // Abrir el archivo
			  //File file = new File(pathArchivo);
			  //Desktop.getDesktop().open(file);
			  
			  // Cerrar el fichero Temporal
			  File tempFile = new File(pathArchivo);
			  tempFile.deleteOnExit();
			 
			 
			 } catch (Exception e) {
			  e.printStackTrace();
			 }
		}
	
	
	
	
	
	
	
	
	
	public DataModel getDataModelCabecera() {
	 return dataModelCabecera;
	}
	 
	public void setDataModelCabecera(DataModel dataModelCabecera) {
	 this.dataModelCabecera = dataModelCabecera;
	}
	 
	public DataModel getDataModelContenido() {
	 return dataModelContenido;
	}
	 
	public void setDataModelContenido(DataModel dataModelContenido) {
	 this.dataModelContenido = dataModelContenido;
	}
}

package com.web.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
 
public class GenFile {
 
	private StreamedContent content;
	
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Instanciar Archivo PDF
    	File file = new File("C:\\Users\\Angel\\Documents\\java.pdf");
        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        
        // Convertir Archivo PDF en un Byte[] (Array de Bytes)
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            //Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
        	System.out.println("HOLA");
        }
        byte[] bytes = bos.toByteArray();
 
        // Convertir un Byte[] (Array de Bytes) a un archivo PDF y escribirlo fisicamente en la carpeta temporal del usuario
        try{
        	// Crear archivo en carpeta temporal desde un Byte Array
        	File tempFile = new File(System.getProperty("user.dir")+"AngelSemiFinal.pdf"); // +System.getProperty("file.separator")
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(bytes);
            fos.flush();
            fos.close();
            
            // Cerrar el fichero Temporal
            //tempFile.deleteOnExit();
            //System.out.println("Archivo temporal eliminado de:" + System.getProperty("user.dir")+"AngelSemiFinal.pdf");  // +System.getProperty("file.separator")

        } catch(Exception e){
        	e.printStackTrace();
        }
        
        // Convertir un Byte[]() a un StreamedContent para posteriormente cargar el contenido a un Visor PDF incrustado directamente en un navegador
        try{
        	InputStream is = new ByteArrayInputStream(bytes);
            System.out.println("size file : "+bytes.length);
            StreamedContent image = new DefaultStreamedContent(is);
            System.out.println("dans le convertisseur : "+image.getContentType());
            System.out.println("dans le convertisseur : "+image);
        } catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    // Convertir un Byte[]() a un StreamedContent para posteriormente cargar el contenido a un Visor PDF incrustado directamente en un navegador
    /*public StreamedContent convertFichier(byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        System.out.println("size file : "+bytes.length);
        StreamedContent image = new DefaultStreamedContent(is);
        System.out.println("dans le convertisseur : "+image.getContentType());
        return image;
    }*/
}
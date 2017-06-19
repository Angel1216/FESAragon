package com.web.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


@ManagedBean  
@SessionScoped  
public class BasicDocumentViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private StreamedContent content;  
  
    public void onPrerender(ComponentSystemEvent event) {  
  
        try {  
        	/*
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
  
            Document document = new Document();  
            PdfWriter.getInstance(document, out);  
            document.open();  
  
            for (int i = 0; i < 50; i++) {  
                document.add(new Paragraph("Angel Martinez Leon Prueba PDF en el Navegador"));  
            }  
              
            document.close();
            content = new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), "application/pdf");
            */
        	
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
            //ByteArrayOutputStream out = new ByteArrayOutputStream();  
            
            //Document document = new Document();  
            //PdfWriter.getInstance(document, out);  
            //document.open();  
  
            //for (int i = 0; i < 50; i++) {  
            //    document.add(new Paragraph("Angel Martinez Leon Prueba PDF en el Navegador"));  
            //}    
            //document.close();
            content = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "application/pdf");
            
            
            
            
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public StreamedContent getContent() {  
        return content;  
    }  
  
    public void setContent(StreamedContent content) {  
        this.content = content;  
    }  
}  

package com.fes.utilities;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/*
 * 
Descripcion		Usuario			Password			Encrypt
Super Usuario	meow_equ19		ColvBLFA3A373		ecKtBGMWRJwa4DhiuH8sRQ==
FES Aragon		enc_fesAr01		ResEncLFAr40		TrSRoynba2pDAvz0gMsytA==
FES Acatlan		enc_fesAc02		ResEncLFAc41		TrSRoynba2qIulCPrjfhCA==
FES Cuautitlan	enc_fesCu03		ResEncLFCu39		TrSRoynba2qDoTGOCxsrBQ==
ENP				enc_bENP04		ResEncBEnp42		CXWSE1VEtLBT4VXqt9EGQw==
CCH				enc_bCCH05		ResEncBCch43		74k2zUAxQBAhXavQgmPQBg==
B@UNAM			enc_bBUnam06	ResEncBBAd44		oEAGLQ464OthQLgD3vmZGA==
 * 
 */
public class StringEncrypt {
	
	public static String Encriptar(String texto) {
		 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
	}
	
	
	public static String Desencriptar(String textoEncriptado) throws Exception {
		 
        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
	}
	
	public static void main(String[] args) throws Exception {

		StringEncrypt objStringEncrypt = new StringEncrypt();
        String encriptado = objStringEncrypt.Encriptar("ResEncDGU22FB");
        System.out.println(encriptado);
        String desencriptado = objStringEncrypt.Desencriptar(encriptado);
        System.out.println(desencriptado);

    }

}

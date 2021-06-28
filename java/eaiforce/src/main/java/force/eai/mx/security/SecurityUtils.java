package force.eai.mx.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SecurityUtils {
	private static final String STR_PASS_PHRASE 	= "081119802411201412021980";
	private static final String SITE_SECRET 	= "6LdrgxETAAAAAGeWibKLMcsK-1neE-mke-1mznv3";
	private static final String GRECAPTCHA_URL 	= "https://www.google.com/recaptcha/api/siteverify";
	
	private SecretKey key = null;
    private Cipher cipher = null;
	
	public SecurityUtils() {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
	        key = factory.generateSecret(new DESedeKeySpec(STR_PASS_PHRASE.getBytes()));
	        cipher = Cipher.getInstance("DESede");			
		} catch (Exception e) {
			
		}
	}
	
	public String encryptAes(String value) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return DatatypeConverter.printBase64Binary(cipher.doFinal(value.getBytes()));
	}
	
	public String decryptAes(String value) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(value)));
	}

	public String encode64(byte[] value) {
		Base64 encoder = new Base64();
		byte[] encodedBytes = (byte[]) encoder.encode( value );		
		return new String(encodedBytes);
	}

	public String decode64(String value) {
		Base64 decoder = new Base64();
		byte[] decodedBytes = decoder.decode( value );		
		return new String(decodedBytes);
	}
	
	public String openUrl(String response) throws MalformedURLException, IOException {		
		String abc = "";
		String inputLine;
		String a = GRECAPTCHA_URL + "?secret=" + SITE_SECRET + "&response=" + response;

		URL url = new URL(a);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		// open the stream and put it into BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((inputLine = br.readLine()) != null)
			abc += inputLine;
		
		br.close();
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(abc).getAsJsonObject();
		abc = jsonObject.get("success").toString().trim();
                
		return abc;
	}
	
	public static void main(String a[]) throws Exception {
            String param = "mnavarro@solucioneshipotecarias.com.mx";
            SecurityUtils objeto = new SecurityUtils();
            System.out.println("Text : " + param);

            //t=381e28ac234c41d5de6d53&h=9bf8b76043b1d10c

            System.out.println("Text Encrypted : " + objeto.encryptAes(param));
            System.out.println("Text Decrypted : " + objeto.decryptAes("94/3bY90CvKQxOgwcVdSTn8mrMGSdcC1X49S6mh2CtnamMGGmkySRA=="));

            System.out.println("Bytes " + param.getBytes());
            System.out.println("Encoded 64: " + objeto.encode64(param.getBytes()) );
            System.out.println("Decoded 64: " + objeto.decode64("h8rZfz5gn68="));   
            
            System.out.println(new SecurityUtils().decode64("dXN1YXJpbz1nZXJlbnRlQGJ5Yy5teCZwYXNzd29yZD1mb3JjZQ=="));
            System.out.println(new SecurityUtils().decode64("aWQ9W0JANjE1Y2EwZmUmfCZtc2c9eyJleGNlcHRpb24iOiIiLCJjb2RlIjoiMCIsImRlc2NyaXB0aW9uIjoiIiwibWVzc2FnZSI6IlNvbGljaXR1ZCBJbXByZXNhPGJyPmNhcmdhZG8oYSkgZXhpdG9zYW1lbnRlIn0="));
            System.out.println(new SecurityUtils().decode64("NA=="));
	}	
}

package it.trmn.espertodeglionomastici;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class EspertodeglionomasticiApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/");
        System.setProperty("server.port", "443");
        System.setProperty("server.ssl.key-store-type", "PKCS12");
        System.setProperty("server.ssl.key-store", "classpath:keystore/certificate.pfx");
        System.setProperty("server.ssl.key-store-password", "");
		SpringApplication.run(EspertodeglionomasticiApplication.class, args);
	}

}

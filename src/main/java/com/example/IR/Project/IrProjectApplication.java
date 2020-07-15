package com.example.IR.Project;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.IR.Project.processing.DocumentsParse;

@SpringBootApplication
public class IrProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrProjectApplication.class, args);
		DocumentsParse d = new DocumentsParse();
		
		try {
			d.readFolder("C:\\Users\\Khalil\\Downloads\\Compressed\\IR Homework\\corpus\\corpus");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}

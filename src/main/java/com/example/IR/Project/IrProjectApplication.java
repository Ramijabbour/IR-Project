package com.example.IR.Project;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.IR.Project.Model.FileModel;
import com.example.IR.Project.Service.FileService;
import com.example.IR.Project.processing.DocumentsParse;

@SpringBootApplication
public class IrProjectApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(IrProjectApplication.class, args);
		//DocumentsParse d = new DocumentsParse();
	/*
		try {
			//d.readFolder("C:\\Users\\ramij\\Desktop\\IR\\IR Homework\\corpus 2");
			
			System.out.println("start create index .............. ...");
			//d.CreateIndex();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//FileModel file = new FileModel("rami"); 
		//fileee
		/*ArrayList< String > a = new ArrayList<String>();
		a.add("rami");
		a.add("khalil");
		a.add("rami");
		
		System.out.println(a.contains("rami"));
		*/
	//	d.removeUniqueCharactar("hi man this is my email  # 800$$ khalil@gmail.com, asd,asd.ss asd. 10.10.2020");
		
	}
}

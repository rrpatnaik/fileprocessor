package com.deranz.file.process.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.deranz.time.currenttime.controller", "com.deranz.file.process.controller"})
public class FileProcessorMain {

	public static void main(String[] args) {
		SpringApplication.run(FileProcessorMain.class, args);
	}

}

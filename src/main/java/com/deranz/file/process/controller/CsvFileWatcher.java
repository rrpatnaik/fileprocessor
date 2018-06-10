package com.deranz.file.process.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.deranz.file.process.load.CsvFileProcessor;

@Component
public class CsvFileWatcher implements ApplicationListener<ApplicationReadyEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileWatcher.class);
	private static Properties properties = new  Properties();
	private static InputStream input = null;
	private static String csvFileName = null;

	public void onApplicationEvent(final ApplicationReadyEvent appEvent) {
		
		try {
			input = CsvFileWatcher.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(input);
			String fileWatchLoc = properties.getProperty("csv.file.watch.loc");
			csvFileName = properties.getProperty("csv.file.name");
			
			WatchService watchService = FileSystems.getDefault().newWatchService();
			
			Path csvDir = Paths.get(fileWatchLoc) ;
			LOGGER.info("FileWatcher scanning on directory: " + csvDir);
			
			//Register the given directory with the WatchService
			csvDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
			
			WatchKey key;
			while ((key = watchService.take()) !=null) {
				for(WatchEvent<?> event : key.pollEvents()) {
					String csvFile = event.context().toString();
					String csvFilePath = csvDir.toString() + properties.getProperty("file.separator") + csvFile;
					File inputFile = new File(csvFilePath);
					
					LOGGER.info("Event kind: " + event.kind() + ". File affected: " + event.context() + ".");
					LOGGER.info("File picked for processing: " + csvFile);
					CsvFileProcessor fileProcess = new CsvFileProcessor();
					fileProcess.processInputFile(csvFilePath);
					
				}
			}
		}
		catch(IOException e) {
			LOGGER.error("Config file not found. " + e);
		}
		catch(InterruptedException e) {
			LOGGER.error("Watch Service interrupted. " + e);
		}
		catch(Exception e) {
			LOGGER.error("Internal Error occurred. " + e);
			e.printStackTrace();
		}
		
		
	}
	
	

}

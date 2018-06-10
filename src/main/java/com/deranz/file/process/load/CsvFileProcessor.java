package com.deranz.file.process.load;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

public class CsvFileProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileProcessor.class);
	private static Properties properties = new  Properties();
	private static InputStream input = null;
	
	public boolean processInputFile(String fileName) {
		
		
		final CellProcessor[] allProcessors = new CellProcessor[] { new UniqueHashCode(), // customerNo (must be unique)
                new NotNull(), // firstName
                new NotNull(), // lastName
                new ParseDate("dd/MM/yyyy") }; // birthDate
        
        final CellProcessor[] noBirthDateProcessors = new CellProcessor[] { allProcessors[0], // customerNo
                allProcessors[1], // firstName
                allProcessors[2] }; // lastName
        
        ICsvListReader listReader = null;
        try {
                listReader = new CsvListReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
                
                listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
                
                while( (listReader.read()) != null ) {
                        
                        // use different processors depending on the number of columns
                        final CellProcessor[] processors;
                        if( listReader.length() == noBirthDateProcessors.length ) {
                                processors = noBirthDateProcessors;
                        } else {
                                processors = allProcessors;
                        }
                        
                        final List<Object> customerList = listReader.executeProcessors(processors);
                        System.out.println(String.format("lineNo=%s, rowNo=%s, columns=%s, customerList=%s",
                                listReader.getLineNumber(), listReader.getRowNumber(), customerList.size(), customerList));
                }
                
        }
        catch(Exception e) {
        	LOGGER.error("Exception while reading file. " + e);
        	e.printStackTrace();
        }
        finally {
                if( listReader != null ) {
                        try {
							listReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
                }
        }
		
		
		
		
		return true;
	}

}

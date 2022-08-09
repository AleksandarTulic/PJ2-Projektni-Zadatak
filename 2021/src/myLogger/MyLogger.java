package myLogger;

import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;
import java.io.File;
import java.io.IOException;  

/**
 * @author Aleksandar Tulic
 * @version 1.0
 * @since 2021-07-28
 * */

public class MyLogger {
	private XMLFormatter format;
	private LogRecord lr;
	private FileHandler fh;
	
	public MyLogger() {
		format = new XMLFormatter();
		lr = new LogRecord(Level.INFO, "");
		
			Date cd = new Date();
			long ms = cd.getTime();
			
			try {
				fh = new FileHandler("log" + File.separator + Long.toString(ms) + ".xml");
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
	}

	public XMLFormatter getFormat() {
		return format;
	}

	public void setFormat(XMLFormatter format) {
		this.format = format;
	}

	public LogRecord getLr() {
		return lr;
	}

	public void setLr(LogRecord lr) {
		this.lr = lr;
	}

	public FileHandler getFh() {
		return fh;
	}

	public void setFh(FileHandler fh) {
		this.fh = fh;
	}
	
	public void flush() {
		fh.flush();
	}
	
	public void publish() {
		fh.publish(lr);
	}
	
	public void setValue(Level off, String text) {
		lr.setLevel(off);
		lr.setMessage(text);
	}
}

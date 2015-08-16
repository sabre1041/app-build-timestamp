package com.redhat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/")
public class BuildStatsServlet extends GenericServlet {
	
	private static final String PROPERTIES_FILE_NAME = "app.properties";
	
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		
		PrintWriter writer =  response.getWriter();
		writer.print("<title>Application Build Information</title>");
		
		Properties properties = getProperties(writer);
		
		if(properties != null) {
			
			String version = properties.getProperty("version");
			String buildDate = properties.getProperty("build.date");
			
			writer.print("This application was built on <b>" + buildDate + "</b> and is at version <b>" + version + "</b>");
			
		}
		
		
		
	}

	
	private Properties getProperties(PrintWriter writer) {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
		
		Properties properties = new Properties();
		
		try {
			properties.load(is);
		} catch (IOException e) {
			writer.print("Error Loading Properties File");
		}
		finally {
			try {
				is.close();
			}
			catch(Exception e) {
				
			}
		}
		
		return properties;
	}


}

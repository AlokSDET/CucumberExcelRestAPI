package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class FileUtilities {

	public static String getJSONFileString(Class<?> cls, String file) throws IOException {
		 String fileName = "/testdata/" + file;
		InputStream is = new FileInputStream(new File(URLDecoder.decode(cls.getResource(fileName).getPath(), "UTF-8")));
		return IOUtils.toString(is);
	}

	/**
	 * This method will return the value of provided key from
	 * application.properties file.
	 * 
	 * @param propertyKey
	 *            : property key for which value needs to be return
	 * @return: Returns the value of given property key.
	 */
	public  String getPropertyValue(String propertyKey) {
		String propertyValue = null;
		try {
			// Getting value of given property key from property file.
			propertyValue = loadPropertyFile().getProperty(propertyKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyValue;
	}

	public  Properties loadPropertyFile() throws IOException {
		Properties prop = new Properties();	
		InputStream is = FileUtilities.class.getResourceAsStream("/application.properties");
		// Loading property file
		prop.load(is);
		return prop;
	}
}

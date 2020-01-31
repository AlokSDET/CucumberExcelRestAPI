package common;

import utilities.FileUtilities;

public interface InterfaceReqres {

	// Keeping one static method in Interface so that its implemented class needs not to override and can use directly: Java 8 Concepts
	public static String buildServiceURL() {
		return  new FileUtilities().getPropertyValue("URL");
	}
}

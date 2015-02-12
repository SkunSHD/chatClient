package skuns.chat;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Config {

	private static final String PATH_CONFIG_FILE = "/home/skuns/git/testing/chatClient/bin/skuns/chat/Config.xml";

	public static int PORT;
	public static String HOST_IP;

	static {
		Properties properties = new Properties();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(PATH_CONFIG_FILE);
			properties.loadFromXML(fis);

			PORT = Integer.parseInt(properties.getProperty("server.port"));
			HOST_IP = properties.getProperty("server.host");

		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Read/write error");
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				System.err.println("fis closing error");
				e.printStackTrace();
			}
		}

	}
}
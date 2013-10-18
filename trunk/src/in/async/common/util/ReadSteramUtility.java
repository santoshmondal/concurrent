// REF::http://stackoverflow.com/questions/6608795/what-is-the-difference-between-class-getresource-and-classloader-getresource
package in.async.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ReadSteramUtility {

	private static final String CONFIG_PROP = "config.properties";
	private static Properties prop = new Properties();

	public static void main(String args[]) {
		readUsingClass();
		readUsingClassLoader();
		readUsingClassLoaderThread();
		readUsingResouceBundle();
		readUsingPropResourceBundle();
		readExternalProperty();
	}

	public static void readUsingClass() {
		InputStream ras = null;
		try {
			// WILL THROW NULL
			/*ras = ReadSteramUtility.class.getResourceAsStream(CONFIG_PROP);
			prop.load(ras);
			System.out.println("CLASS::" + prop);
			ras.close();*/

			// nw look inside the package
			ras = ReadSteramUtility.class.getResourceAsStream("packconfig.properties");
			prop.load(ras);
			System.out.println("PACKAGE_CLASS::" + prop);
			ras.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readUsingClassLoader() {
		try {
			InputStream ras = ReadSteramUtility.class.getClassLoader().getResourceAsStream(CONFIG_PROP);
			prop.load(ras);
			System.out.println("CLASS_LOADER::" + prop);
			ras.close();

			// inside package
			ras = ReadSteramUtility.class.getClassLoader().getResourceAsStream("in/async/common/util/packconfig.properties");
			prop.load(ras);
			System.out.println("PACKAGE_CLASS_LOADER::" + prop);
			ras.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readUsingClassLoaderThread() {
		try {
			InputStream ras = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PROP);
			prop.load(ras);
			System.out.println("THREAD_CLASS_LOADER::" + prop);
			ras.close();
		} catch (Exception e) {

		}
	}

	public static void readUsingResouceBundle() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("config");
			System.out.println("ResourceBundle::" + rb.keySet());

			rb = ResourceBundle.getBundle("in/async/common/util/packconfig");
			System.out.println("Package_ResourceBundle::" + rb.keySet());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readUsingPropResourceBundle() {
		try {
			InputStream is = ReadSteramUtility.class.getClassLoader().getResourceAsStream("config.properties");
			ResourceBundle rb = new PropertyResourceBundle(is);
			System.out.println("PROPResourceBundle" + rb.keySet());
		} catch (Exception e) {

		}
	}

	public static void readUsingListResourceBundle() {
		try {

		} catch (Exception e) {

		}
	}

	public static void readExternalProperty() {
		try {
			InputStream is = new FileInputStream(new File("external.properties"));
			Properties iprop = new Properties();
			iprop.load(is);
			System.out.println("External::" + iprop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

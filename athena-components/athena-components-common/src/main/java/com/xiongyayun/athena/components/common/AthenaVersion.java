package com.xiongyayun.athena.components.common;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * AthenaVersion
 *
 * @author Yayun.Xiong
 * @date 2021/9/15
 */
public final class AthenaVersion {

	private AthenaVersion() {
	}

	public static String getVersion() {
		return determineAthenaVersion();
	}

	private static String determineAthenaVersion() {
		String implementationVersion = AthenaVersion.class.getPackage().getImplementationVersion();
		if (implementationVersion != null) {
			return implementationVersion;
		}
		CodeSource codeSource = AthenaVersion.class.getProtectionDomain().getCodeSource();
		if (codeSource == null) {
			return null;
		}
		URL codeSourceLocation = codeSource.getLocation();
		try {
			URLConnection connection = codeSourceLocation.openConnection();
			if (connection instanceof JarURLConnection) {
				return getImplementationVersion(((JarURLConnection) connection).getJarFile());
			}
			try (JarFile jarFile = new JarFile(new File(codeSourceLocation.toURI()))) {
				return getImplementationVersion(jarFile);
			}
		}
		catch (Exception ex) {
			return null;
		}
	}

	private static String getImplementationVersion(JarFile jarFile) throws IOException {
		return jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
	}
}

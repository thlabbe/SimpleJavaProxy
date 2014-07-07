package proxy;

import proxy.ProxyServer.Sens;

public class ProxyUtils {
	public static void log(Sens sens, int length){
		System.out.println(String.format("%s [ %4d bytes ] { ... }\n",sens.string(), length));
	}
	
	public static void logVerbose(Sens sens, byte[] buffer, int length){
		StringBuffer sb = new StringBuffer("# " + sens.string());
		
		sb.append(String.format(" [ %4d bytes ] { \n", length));
		
		for (int i = 0; i < length; i++) {
			sb.append(String.format("%02X ",buffer[i]));
			if ( (i+1) % 16 == 0 ) {
				sb.append('\n');
			}
		}
		sb.append("\n# }");
		
		System.out.println(sb.toString());
	}
}

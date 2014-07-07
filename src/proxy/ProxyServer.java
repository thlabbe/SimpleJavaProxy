package proxy;

import java.io.*;
import java.net.*;
import static proxy.ProxyUtils.*;

public class ProxyServer {

	public enum Sens {
		HOST_TO_PROXY("reception"), CLIENT_TO_PROXY("envoi");
		private String str;

		Sens(String s) {
			this.str = s;
		}

		public String string() {
			return this.str;
		}
	};

	public static void main(String[] args) throws IOException {
		try {
			if (args.length < 2 || args.length > 4)
				throw new IllegalArgumentException("Wrong number of arguments.");

			String host = args[0];
			int remoteport = Integer.parseInt(args[1]);
			int localport = Integer.parseInt(args[2]);
			boolean verbose = false; 
					
			if (args.length == 4) {
				String verboseSwitch = args[3];
				verbose = ( verboseSwitch.equalsIgnoreCase("--verbose"));
			};

			System.out.println("Starting proxy for " + host + ":" + remoteport
					+ " on port " + localport);

			runServer(host, remoteport, localport, verbose); // never returns
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java SimpleProxyServer "
					+ "<host> <remoteport> <localport> [--verbose]");
		}
	}

	public static void runServer(String host, int remoteport, int localport,
			final boolean verbose) throws IOException {
		ServerSocket ss = new ServerSocket(localport);

		final byte[] request = new byte[1024];
		byte[] reply = new byte[4096];

		while (true) {
			Socket client = null, server = null;
			try {
				System.out.println("attente d'une connexion cliente...");
				client = ss.accept();
				System.out.println("connexion cliente en cours");

				final InputStream from_client = client.getInputStream();
				final OutputStream to_client = client.getOutputStream();

				try {
					server = new Socket(host, remoteport);
				} catch (IOException e) {
					PrintWriter out = new PrintWriter(new OutputStreamWriter(
							to_client));
					out.println("Proxy server cannot connect to " + host + ":"
							+ remoteport + ":\n" + e);
					out.flush();
					client.close();
					continue;
				}
				System.out.println("connexion au host");

				final InputStream from_server = server.getInputStream();
				final OutputStream to_server = server.getOutputStream();

				Thread t = new Thread() {

					public void run() {
						int bytes_read;
						try {
							while ((bytes_read = from_client.read(request)) != -1) {
								if (verbose) {
									logVerbose(Sens.CLIENT_TO_PROXY, request,
											bytes_read);
								} else {
									log(Sens.CLIENT_TO_PROXY, bytes_read);
								}
								to_server.write(request, 0, bytes_read);
								to_server.flush();
							}
						} catch (IOException e) {}

						try {
							to_server.close();
						} catch (IOException e) {}
					}
				};

				t.start();
				int bytes_read;
				try {
					while ((bytes_read = from_server.read(reply)) != -1) {
						if (verbose) {
							logVerbose(Sens.HOST_TO_PROXY, reply, bytes_read);
						} else {
							log(Sens.HOST_TO_PROXY, bytes_read);
						}
						to_client.write(reply, 0, bytes_read);
						to_client.flush();
					}
				} catch (IOException e) {}

				to_client.close();
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				try {
					if (server != null)
						server.close();
					if (client != null)
						client.close();
				} catch (IOException e) {}
			}
		}
	}
}

package com.npcode.http;

public class Server {

	static public class RequestMessage {
		String method;
		String uri;
		String version;

		public void build(String buffer) {
			String[] segments = buffer.split(" ");
			method = segments[0];
			uri = segments[1];
			version = segments[2];
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting HTTP Server ..");
		java.io.File directory = new java.io.File(".");
		System.out.println("Current Dir : " + directory.getAbsolutePath());

		eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");

		try {
			java.net.ServerSocket serverSocket = new java.net.ServerSocket(8181);
			while (true) {
				System.out.println("Waiting for acception .. 0.03");

				java.net.Socket client = serverSocket.accept();

				java.io.InputStream client_is = client.getInputStream();
				java.io.OutputStream client_os = client.getOutputStream();

				java.io.BufferedReader bufReader = new java.io.BufferedReader(
						new java.io.InputStreamReader(client_is));

				// read from client
				StringBuilder received_total_buffer = new StringBuilder();
				String received = bufReader.readLine();
				if(received== null){
					client.close();
					continue;
				}
				while (received.length() > 0) {
					received_total_buffer.append(received + "\n");
					received = bufReader.readLine();
				}

				// TODO : parse from reading data
				String received_total = received_total_buffer.toString();
				RequestMessage requestMessage = new RequestMessage();
				requestMessage.build(received_total);

				if (requestMessage.method.equalsIgnoreCase("GET")) {
					java.io.File resource = new java.io.File("./htdocs" + requestMessage.uri);
					if (resource.exists()) {
						java.util.Collection<?> mimeTypes = eu.medsea.mimeutil.MimeUtil.getMimeTypes(resource);
						System.out.println("Mime type is of (" + resource.getName() + ") : " + mimeTypes);

						java.io.FileInputStream fis = new java.io.FileInputStream(resource);

						byte[] b = new byte[1024 * 1024];
						int content_length = fis.read(b);

						String CRLF = "\r\n";

						// TODO : generate responding to client
						String message = "HTTP/1.1 200 OK" + CRLF;
						// message += "Date: Sun, 22 July 2012 17:30:00 GMT\n";
						message += "Server: npcode-http" + CRLF;
						message += "Content-Length: " + content_length + CRLF;
						message += "Content-Type: " + mimeTypes + CRLF + CRLF;;
						client_os.write(message.getBytes());
						//client_os.flush();
						client_os.write(b, 0, content_length);

					} else {
						client_os.write("HTTP/1.1 404 Not Found\n\n".getBytes());
					}
				}

				// close
				bufReader.close();
				client.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}

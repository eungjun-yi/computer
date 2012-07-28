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
    
    static public class FileUtil {
    	public static String getMimeType(java.io.File f){
    		return new javax.activation.MimetypesFileTypeMap().getContentType(f);
    	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting HTTP Server ..");
        java.io.File directory = new java.io.File(".");
        System.out.println("Current Dir : " + directory.getAbsolutePath());
        
        eu.medsea.mimeutil.MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");

        try {
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(8888);
            while (true) {
                System.out.println("Waiting for acception ..");

                java.net.Socket client = serverSocket.accept();

                java.io.InputStream client_is = client.getInputStream();
                java.io.OutputStream client_os = client.getOutputStream();

                java.io.BufferedReader bufReader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(client_is));
                java.io.BufferedWriter bufWriter = new java.io.BufferedWriter(
                        new java.io.OutputStreamWriter(client_os));

                // read from client
                StringBuilder received_total_buffer = new StringBuilder();
                String received = bufReader.readLine();
                while (received.length() > 0) {
                    received_total_buffer.append(received + "\n");
                    received = bufReader.readLine();
                }

                // TODO : parse from reading data
                String received_total = received_total_buffer.toString();
                RequestMessage requestMessage = new RequestMessage();
                requestMessage.build(received_total);

                String response = "";
                if (requestMessage.method.equalsIgnoreCase("GET")) {
                    java.io.File resource = new java.io.File("./htdocs" + requestMessage.uri);
                    if (resource.exists()) {
                    	java.util.Collection<?> mimeTypes = eu.medsea.mimeutil.MimeUtil.getMimeTypes(resource);
                    	//String mimeType = FileUtil.getMimeType(resource);
                    	System.out.println("Mime type is : " + mimeTypes);
                    	
                        java.io.FileInputStream fis = new java.io.FileInputStream(resource);

                        byte[] b = new byte[1024 * 1024];
                        fis.read(b);
                        
                        String CRLF = "\r\n";

                        // TODO : generate responding to client
                        String content = new String(b);
                        String message = "HTTP/1.1 200 OK" + CRLF;
                        // message += "Date: Sun, 22 July 2012 17:30:00 GMT\n";
                        message += "Server: npcode-http" + CRLF;
                        message += "Content-Length: " + content.getBytes().length + CRLF;
                        message += "Content-Type: " + mimeTypes + CRLF + CRLF;;
                        message += content;

                        response = message;
                    } else {
                        response = "HTTP/1.1 404 Not Found\n\n";
                    }
                }

                // write to client
                // System.out.print("SERVER RECEIVED : \n" + received_total);
                // bufWriter.write("SERVER SENT : \n" + received_total);
                bufWriter.write(response);
                bufWriter.flush();

                // close
                bufReader.close();
                bufWriter.close();
                client.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}

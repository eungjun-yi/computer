package com.npcode.http;

public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) {
	    System.out.println("Starting HTTP Server ..");
	    
	    try {
	        java.net.ServerSocket serverSocket = new java.net.ServerSocket(8080);
            while(true){
                System.out.println("Waiting for acception ..");
                
                java.net.Socket client = serverSocket.accept();
                
                java.io.InputStream client_is = client.getInputStream();
                java.io.OutputStream client_os = client.getOutputStream();
                
                java.io.BufferedReader bufReader = new java.io.BufferedReader(new java.io.InputStreamReader(client_is));
                java.io.BufferedWriter bufWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(client_os));
                
                // read from client
                StringBuilder received_total_buffer = new StringBuilder();
                String received = bufReader.readLine();
                while(received.length() > 0 ){
                    received_total_buffer.append(received + "\n");
                    received = bufReader.readLine();
                }
                
                // write to client
                String received_total = received_total_buffer.toString();
                System.out.print("SERVER RECEIVED : \n" + received_total);
                bufWriter.write("SERVER SENT : \n" + received_total);
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

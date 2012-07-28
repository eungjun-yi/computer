package com.npcode.http;

import java.io.IOException;

public class Request {
	
	public static enum METHOD {
		GET,
		
		UNKNOWN
	};
	
	public static Request read(java.io.InputStream _io){
		Request request = new Request();
		java.io.BufferedReader bufReader = new java.io.BufferedReader(new java.io.InputStreamReader(_io));
		StringBuilder received_total_buffer = new StringBuilder();
        String received;
//		try {
//			received = bufReader.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        while (received.length() > 0) {
//            received_total_buffer.append(received + "\n");
//            received = bufReader.readLine();
//        }
		return request;
	}
	
	private METHOD method_ = METHOD.UNKNOWN;
	private String uri_ = null;
	private String version_ = null;
	private Request(){}
	
	
	public METHOD getMethod(){
		return method_;
	}
	
	public String getUri(){
		return uri_;
	}
	
	public String getVersion(){
		return version_;
	}

}

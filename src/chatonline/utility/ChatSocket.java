package chatonline.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatSocket {
	private Socket iSocket;
	private PrintWriter iWriter;
	private Scanner iScanner;
	public ChatSocket(Socket asocket,PrintWriter awriter,Scanner ascanner){
		iSocket=asocket;
		iWriter=awriter;
		iScanner=ascanner;
	}
	public void closeSocket(){
		iScanner.close();
		iWriter.close();
		try {
			iSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String receive(){
		return iScanner.nextLine();
	}
	public void send(String astr){
		iWriter.println(astr);
	}
	public boolean isInitialized(){
		return iSocket!=null&&iWriter!=null&&iScanner!=null;
	}
}

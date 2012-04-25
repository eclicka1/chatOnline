package chatonline.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import chatonline.utility.ChatSocket;

public class ComModuleSer {
	private Socket iCmdSocket;
	private PrintWriter iCmdWriter;
	private Scanner iCmdScanner;

	private Socket iInfoSocket;
	private PrintWriter iInfoWriter;
	private Scanner iInfoScanner;

	//Socket for creating file link
	private Socket iFileSocket;
	private PrintWriter iFileWriter;
	private Scanner iFileScanner;
	/*******************************************************************************
                                initialized function                       
********************************************************************************/
	public void initCom(Socket aSocket) {
		iCmdSocket = aSocket;
		try {
			iCmdScanner = new Scanner(iCmdSocket.getInputStream());
			iCmdWriter = new PrintWriter(iCmdSocket.getOutputStream(), true);// 没有刷新，系统会有缓存功能
			
			// create links:listener
			iCmdWriter.println("OK");
			iInfoSocket = ComModuleSer.getNewSocket();
			iInfoWriter = new PrintWriter(iInfoSocket.getOutputStream(), true);
			iInfoScanner = new Scanner(iInfoSocket.getInputStream());
			
			//create file link
			iCmdWriter.println("OK");
			iFileSocket = ComModuleSer.getNewSocket();
			iFileScanner = new Scanner(iFileSocket.getInputStream());
			iFileWriter = new PrintWriter(iFileSocket.getOutputStream(),true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*******************************************************************************
                                dynamic create link                       
********************************************************************************/
	public ChatSocket getLink(){
		try {
			Socket asocket=ComModuleSer.getNewSocket();
			//parameter true flush immediately
			PrintWriter awriter=new PrintWriter(asocket.getOutputStream(),true);
			Scanner ascanner=new Scanner(asocket.getInputStream());
			return new ChatSocket(asocket, awriter, ascanner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /*******************************************************************************
                                sender and receiver                       
********************************************************************************/
	public void sendCmd(String aCmd) {
		iCmdWriter.println(aCmd);
	}

	public void sendInfo(String aInfo) {
		iInfoWriter.println(aInfo);
	}

	public String getCmd() {
		return iCmdScanner.nextLine();
	}

	public String getInfo() {
		return iInfoScanner.nextLine();
	}
	
	/*******************************************************************************
                                close function                       
********************************************************************************/
	public void closeCom() {
		iCmdScanner.close();
		iCmdWriter.close();
		iInfoScanner.close();
		iInfoWriter.close();
		iFileScanner.close();
		iFileWriter.close();
		try {
			iCmdSocket.close();
			iInfoSocket.close();
			iFileSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*******************************************************************************
                                static methods                       
********************************************************************************/
	public static ComModuleSer getComModule(int aID) {
		return iMap.get(aID);
	}

	public static void clearComModule() {
		iMap.clear();
	}
	// called after login succeed
	public static void addComModule(ComModuleSer aCom, int aID) {
		iMap.put(aID, aCom);
	}
	//called before closing sockets
	public static void delComModule(int aID) {
		iMap.remove(aID);
	}

	public static Socket getNewSocket() throws IOException {
		return iServerSocket.accept();
	}

	public static void setInfoPort(int aPort) {
		iInfoPort = aPort;
	}

	public static int getInfoPort() {
		return iInfoPort;
	}
	public static void staticInit(){
		iMap = new HashMap<Integer, ComModuleSer>();
		try {
			iServerSocket = new ServerSocket(iInfoPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void staticDispose(){
		iMap.clear();
		try {
			iServerSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*******************************************************************************
                                static field                         
********************************************************************************/
	private static int iInfoPort = 1235;
	private static Map<Integer, ComModuleSer> iMap;
	private static ServerSocket iServerSocket;// for create new links
}

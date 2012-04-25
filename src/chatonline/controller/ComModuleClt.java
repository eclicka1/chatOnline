package chatonline.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import chatonline.utility.ChatSocket;

public class ComModuleClt {
	private String iSerIP = "localhost";
	private int iCmdPort = 1234;
	private int iUtilityPort = 1235;

	private Socket iCmdSocket;
	private PrintWriter iCmdWriter;
	private Scanner iCmdScanner;

	private Socket iInfoSocket;
	private PrintWriter iInfoWriter;
	private Scanner iInfoScanner;

	//Socket for receiving file
	private Socket iFileSocket;
	private PrintWriter iFileWriter;
	private Scanner iFileScanner;

	/*******************************************************************************
                                test function                       
********************************************************************************/
	public static void main(String[] args) {
		ComModuleClt bCom = new ComModuleClt();
		bCom.initCom();
		System.out.println(bCom.getInfo());
		bCom.sendInfo("Hello ,I'm Client!");
		bCom.getCmd();
		ChatSocket achat=bCom.getLink();
		System.out.println(achat.isInitialized());
		System.out.println(achat.receive());
		achat.closeSocket();
		bCom.closeCom();
	}

	/*******************************************************************************
                                setter and getter                       
********************************************************************************/
	public int getCmdPort() {
		return iCmdPort;
	}

	public void setCmdPort(int aCmdPort) {
		this.iCmdPort = aCmdPort;
	}

	public int getInfoPort() {
		return iUtilityPort;
	}

	public void setInfoPort(int aInfoPort) {
		this.iUtilityPort = aInfoPort;
	}

	public String getSerIP() {
		return iSerIP;
	}

	public void setSerIP(String aSerIP) {
		this.iSerIP = aSerIP;
	}
	/*******************************************************************************
                                initialized function                       
********************************************************************************/
	public void initCom() {// 创建消息链接
		try {
			iCmdSocket = new Socket(iSerIP, iCmdPort);
			iCmdScanner = new Scanner(iCmdSocket.getInputStream());
			iCmdWriter = new PrintWriter(iCmdSocket.getOutputStream(),true);
			
			// create info link
			iCmdScanner.nextLine();
			iInfoSocket = new Socket(iSerIP, iUtilityPort);
			iInfoScanner = new Scanner(iInfoSocket.getInputStream());
			iInfoWriter = new PrintWriter(iInfoSocket.getOutputStream(),true);
			
			//the following links are controlled by class ComModuleSer
			iCmdScanner.nextLine();
			iFileSocket=new Socket(iSerIP, iUtilityPort);
			iFileScanner = new Scanner(iFileSocket.getInputStream());
			iFileWriter = new PrintWriter(iFileSocket.getOutputStream(),true);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Socket asocket=new Socket(iSerIP, iUtilityPort);
			Scanner ascanner = new Scanner(asocket.getInputStream());
			PrintWriter awriter = new PrintWriter(asocket.getOutputStream(),true);
			return new ChatSocket(asocket, awriter, ascanner);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	// after sent command for closing to both command and Info links.
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
}

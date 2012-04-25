package chatonline;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chatonline.controller.ComModuleSer;
import chatonline.controller.WorkSer;
import chatonline.utility.ChatSocket;

public class ServerListener {
	public static void main(String[] args) {
		int bPort = 1234;
		try {
			ServerSocket bSerSocket = new ServerSocket(bPort);
			ComModuleSer.staticInit();
			while(true) {
				Socket bSocket = bSerSocket.accept();
				new Thread(new TestWorkRunnable(bSocket)).start();
			}
			//ComModuleSer.staticDispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/**
 * @ClassName: TestWorkRunnable 
 * @Description: test Work
 * @author Li 
 * @date 2012-4-21 ÏÂÎç05:49:04 
 *
 */
class TestWorkRunnable implements Runnable {
	private Socket iSocket;

	public TestWorkRunnable(Socket aSocket) {
		iSocket = aSocket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ComModuleSer bCom = new ComModuleSer();
		bCom.initCom(iSocket);
        WorkSer ser=new WorkSer();
        ser.setCom(bCom);
        ser.startupListener();	
	}
}

/**
 * @ClassName: TestComModuleRunnable 
 * @Description: test ComModule
 * @author Li 
 * @date 2012-4-21 ÏÂÎç03:36:42 
 *
 */
class TestComModuleRunnable implements Runnable {
	private Socket iSocket;

	public TestComModuleRunnable(Socket aSocket) {
		iSocket = aSocket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ComModuleSer bCom = new ComModuleSer();
		bCom.initCom(iSocket);
		// work using bCom
		bCom.sendInfo("Hello,I'm Server!");
		System.out.println(bCom.getInfo());
		bCom.sendCmd("ready");
		ChatSocket achat=bCom.getLink();
		System.out.println(achat.isInitialized());
		achat.send("new Link send String!!");
		achat.closeSocket();
		bCom.closeCom();
	}
}
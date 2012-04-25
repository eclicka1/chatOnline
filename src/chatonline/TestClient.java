/**
 * 
 */
package chatonline;

import java.util.Scanner;

import chatonline.controller.ComModuleClt;
import chatonline.controller.WorkClt;
import chatonline.utility.User;

/** @ClassName: TestClient 
 * @Description: test Work layer
 * @author Li 
 * @date 2012-4-22 ÉÏÎç01:19:50 
 * 
 */
public class TestClient {

	/**
	 * @Title: main
	 * @Description: main function
	 * @param args   
	 * @return void    
	 * @throws
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner bscan=new Scanner(System.in);
		ComModuleClt bCom = new ComModuleClt();
		bCom.initCom();
	    WorkClt clt=new WorkClt();
	    clt.setCom(bCom);
	    if(clt.login(11, "123",User.ONLINE)){
	    	clt.startupListener();
	    	while(true){
	    		clt.sentInfo(12, bscan.nextLine());
	    	}
	    	//clt.requestClose();
	    }
	}

}

package chatonline.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import chatonline.access.Access;
import chatonline.utility.AskFrd;
import chatonline.utility.ChatSocket;
import chatonline.utility.Info;
import chatonline.utility.InfoWithPhoto;
import chatonline.utility.User;

public class WorkSer {
	/*******************************************************************************
	 * initialized function
	 ********************************************************************************/
	private ComModuleSer iCom;
	private int iId;

	public void setCom(ComModuleSer aCom) {
		iCom = aCom;
	}

	public void register(Scanner ascan) {
		String bpswd = ascan.next();
		String bstr = ascan.nextLine();
		System.out.println(bpswd);
		System.out.println(bstr);
		
		User buser = new User(bstr, true);
		if (Access.register(buser, bpswd)) {
			iCom.sendCmd(""+buser.iId);
		} else {
			iCom.sendCmd("-1");
		}
	}

	public void login(Scanner ascan) {
		int bid = ascan.nextInt();
		int bstate = ascan.nextInt();
		String bpswd = ascan.nextLine().substring(1);
		// authenticate
		if (Access.login(bid, bpswd, bstate)) {
			iId = bid;
			iCom.sendCmd("true");
		} else {
			iCom.sendCmd("false");
		}
	}

	public void getFrdList(Scanner ascan) {
		Map<String, List<User>> bFrdMapGroup = new HashMap<String, List<User>>();
		Access.getFrdList(iId, bFrdMapGroup);

		StringBuilder bbld = new StringBuilder();
		for (String bkey : bFrdMapGroup.keySet()) {// for each group
			List<User> blist = bFrdMapGroup.get(bkey);
			User.mergeGroup(bbld, User.toPartLine(bkey, blist));
		}
		iCom.sendCmd(bbld.toString());
	}

	public void getNotes(Scanner ascan) {
		List<Info> blist = new LinkedList<Info>();
		List<InfoWithPhoto> blist2=new LinkedList<InfoWithPhoto>();
		Access.getNotes(iId,blist,blist2);

		iCom.sendCmd(Info.toLine(blist));
		iCom.sendCmd(InfoWithPhoto.toLineWithoutPhoto(blist2));

		if(blist2.size()!=0){
			ChatSocket bto=iCom.getLink();
			new Thread(new SendPhoto(bto, blist2)).start();
		}
	}
	class SendPhoto implements Runnable{
		private ChatSocket ito;
		private List<InfoWithPhoto> ilist;
		public SendPhoto(ChatSocket ato,List<InfoWithPhoto> alist){
			ito= ato;
			ilist=alist;
		}
		public void run(){
			ito.send(InfoWithPhoto.toPhotoLine(ilist));
		}
	}
	public void getAskFrd(Scanner ascan) {
		List<AskFrd> blist = new LinkedList<AskFrd>();
		Access.getAskFrd(iId, blist);

		iCom.sendCmd(AskFrd.toLine(blist));
	}

	public void startupListener(Scanner ascan) {
		ComModuleSer.addComModule(iCom, iId);
	}

	private boolean iIsListenCmd;
	private boolean iIsListenInfo;

	public void startupListener() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				iIsListenCmd = true;
				while (iIsListenCmd) {
					Scanner bscan = new Scanner(iCom.getCmd());
					try {
						String acmd = bscan.next();
						if (acmd.equals("requestClose")
								|| acmd.equals("closeWork")) {
							iIsListenCmd = false;
						}
						WorkSer.class.getMethod(acmd,
								new Class[] { Scanner.class }).invoke(
								WorkSer.this, new Object[] { bscan });
					} catch (Exception e) {
						e.printStackTrace();
					}
					bscan.close();
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				iIsListenInfo = true;
				while (iIsListenInfo) {
					Scanner bscan = new Scanner(iCom.getInfo());
					try {
						String acmd = bscan.next();
						if (acmd.equals("requestClose")
								|| acmd.equals("closeWork")) {
							iIsListenInfo = false;
						}
						WorkSer.class.getMethod(acmd,
								new Class[] { Scanner.class }).invoke(
								WorkSer.this, new Object[] { bscan });
					} catch (Exception e) {
						e.printStackTrace();
					}
					bscan.close();
				}
			}
		}).start();
	}

	/*******************************************************************************
	 * manage friends
	 ********************************************************************************/
	public void findFrd(Scanner ascan) {// User auser
		User buser = new User();
		buser.initFromStrWithParameter(ascan.nextLine());
		List<User> blist = new LinkedList<User>();
		Access.findFrd(blist, buser);
		iCom.sendCmd(User.toLine(blist));
	}

	public void addFrd(Scanner ascan) {// int aid
		AskFrd bask = new AskFrd(ascan.nextLine());
		ComModuleSer bcom = ComModuleSer.getComModule(bask.itoid);
		if (bcom != null) {
			bcom.sendInfo("askForFrd " + bask.toLine());
		} else {
			Access.addAskForFrd(bask);
		}
	}

	public void askFrdRespond(Scanner ascan) {
		int bid = ascan.nextInt();
		boolean bis = ascan.nextBoolean();
		if (bis) {
			Access.addFrd(iId, bid);
		}
		ComModuleSer bcom = ComModuleSer.getComModule(bid);
		if (bcom != null) {
			bcom.sendCmd(String.format("getAskFrdRespond %d %s", iId, bis));
		}
	}

	public void delFrd(Scanner ascan) {
		int bid = ascan.nextInt();
		ComModuleSer bcom = ComModuleSer.getComModule(bid);
		if (bcom != null) {
			String bcmd = String.format("delFrd %d", iId);
			bcom.sendInfo(bcmd);
		}
		Access.delFrd(iId, bid);
		iCom.sendCmd("ok");
	}

	public void renameFrd(Scanner ascan) {// int aid, String aname
		int bid = ascan.nextInt();
		String bname = ascan.nextLine();
		Access.renameFrd(iId, bid, bname);
		iCom.sendCmd("ok");
	}

	/*******************************************************************************
	 * manage groups
	 ********************************************************************************/
	public void addGroup(Scanner ascan) {// String aname
		String bname = ascan.next();
		Access.addGroup(iId, bname);
		iCom.sendInfo("ok");
		ascan.close();
	}

	public void delGroup(Scanner ascan) {// String aname
		String bname = ascan.next();
		Access.delGroup(iId, bname);
		iCom.sendInfo("ok");
	}

	public void renameGroup(Scanner ascan) {// String aOldName, String aNewName
		String bold = ascan.next();
		String bnew = ascan.next();
		Access.renameGroup(iId, bold, bnew);
		iCom.sendInfo("ok");
	}

	public void moveToGroup(Scanner ascan) {// int aid, String aname
		int bid = ascan.nextInt();
		String bname = ascan.next();
		Access.moveToGroup(iId, bid, bname);
		iCom.sendInfo("ok");
	}

	/*******************************************************************************
	 * transmit messages
	 ********************************************************************************/
	public void sendInfo(Scanner ascan) {
		String bstr = ascan.nextLine();
		Info binfo = new Info(bstr);
		ComModuleSer bcom = ComModuleSer.getComModule(binfo.iToId);
		if (bcom != null) {
			bcom.sendInfo("sendInfo " + bstr);
			Access.addInfo(binfo, true);
		} else {
			Access.addInfo(binfo, false);
		}
	}

	public void sendInfoWithPhoto(Scanner ascan) {
		InfoWithPhoto binfo = new InfoWithPhoto(ascan.nextLine());
		ComModuleSer bcom = ComModuleSer.getComModule(binfo.iToId);
		ChatSocket bToSocket=null;
		if (bcom != null) {
			bcom.sendInfo("sendInfoWithPhoto "+binfo.toPartLine());
			bToSocket=iCom.getLink();
			
			iCom.sendCmd("ok");
			ChatSocket bFromSocket=iCom.getLink();
			new Thread(new TransmitPhoto(bFromSocket,bToSocket,binfo)).start();
		}else{
			iCom.sendCmd("ok");
			ChatSocket bFromSocket=iCom.getLink();
			new Thread(new GetPhoto(bFromSocket,binfo)).start();
		}
	}
	class GetPhoto implements Runnable{
		private ChatSocket ifrom;
		private InfoWithPhoto iinfo;
		
		public GetPhoto(ChatSocket afrom,InfoWithPhoto ainfo){
			ifrom=afrom;
			iinfo=ainfo;
		}
		public void run(){
			String bstr=ifrom.receive();
			iinfo.initFromPhotoLine(bstr);
			Access.addInfoWithPhoto(iinfo, false);
			iinfo.writePhotoFile();
			ifrom.closeSocket();
		}
	}
	class TransmitPhoto implements Runnable{
		private ChatSocket ifrom;
		private ChatSocket ito;
		private InfoWithPhoto iinfo;
		public TransmitPhoto(ChatSocket afrom,ChatSocket ato,InfoWithPhoto ainfo){
			ifrom=afrom;
			ito=ato;
			iinfo=ainfo;
		}
		@Override
		public void run() {
			String bstr=ifrom.receive();
			iinfo.initFromPhotoLine(bstr);
			ito.send(bstr);
			Access.addInfoWithPhoto(iinfo, true);
			iinfo.writePhotoFile();
			ifrom.closeSocket();
			ito.closeSocket();
		}
	}
	/*******************************************************************************
	 * close Communicate
	 ********************************************************************************/
	// synchronized function make sure close work at second time
	private int icnum = 0;

	public synchronized void requestClose(Scanner ascan) {
		System.out.println("requestClose:" + icnum);
		if (++icnum == 2) {
			iCom.sendInfo("closeWork");
			closeWork();
		} else {
			ComModuleSer.delComModule(iId);
			Access.setUserState(iId, User.OFFLINE);
		}
	}

	public synchronized void closeWork(Scanner ascan) {
		icnum++;
		if (icnum == 2) {
			closeWork();
		}
	}

	public void requestClose() {
		iCom.sendInfo("requestClose");
		ComModuleSer.delComModule(iId);
		Access.setUserState(iId, User.OFFLINE);
	}

	public void closeWork() {// sent request for closing
		System.out.println("closeWork!!");
		iCom.closeCom();
	}
}

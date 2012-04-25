package chatonline.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import chatonline.utility.AskFrd;
import chatonline.utility.ChatSocket;
import chatonline.utility.Info;
import chatonline.utility.InfoWithPhoto;
import chatonline.utility.User;

public class WorkClt {
	private ComModuleClt iCom;
	private int iId;
	private HookListenClt ihook;

	/*******************************************************************************
	 * test function
	 ********************************************************************************/
	public static void main(String[] args) {
		ComModuleClt bCom = new ComModuleClt();
		bCom.initCom();
		WorkClt clt = new WorkClt();
		clt.setCom(bCom);
		User buser;
//		User buser = new User(0, "jiangbin", "d", "xd", new Date(), "googjob!",
//				0, 2, new Date());
//		if (clt.register(buser, "11")) {
//			System.out.println("register success!!");
//		}
		if (clt.login(0,"11",User.HIDE)) {
			Map<String, List<User>> amap = new HashMap<String, List<User>>();
			clt.getFrdList(amap);
			for (String bstr : amap.keySet()) {
				System.out.println(bstr + ":");
				for (User bi : amap.get(bstr)) {
					System.out.println(bi.toPartLine());
				}
			}
			List<Info> blist = new LinkedList<Info>();
			List<InfoWithPhoto> blist2=new LinkedList<InfoWithPhoto>();
			clt.getNotes(blist,blist2);
			for (Info binfo : blist) {
				System.out.println(binfo.toString());
			}
			
			List<AskFrd> basklist=new LinkedList<AskFrd>();
			clt.getAskFrd(basklist);
			for(AskFrd biask:basklist){
				System.out.println(biask.toLine());
			}
			
			clt.startupListener();

			List<User> bUserList = new LinkedList<User>();
			buser = new User();
			buser.iId = 1;
			clt.findFrd(bUserList, buser);
			for (User bi : bUserList) {
				System.out.println(bi);
			}

			clt.addFrd(0, "i am you!");

			clt.requestClose();
		} else {
			clt.requestClose();

		}
	}

	/*******************************************************************************
	 * initialized function all are synchronized before start up listeners!!!!!
	 ********************************************************************************/
	public void setCom(ComModuleClt aCom) {
		iCom = aCom;
	}

	public void setHook(HookListenClt ahook) {
		ihook = ahook;
	}

	public boolean register(User auser, String apswd) {
		iCom.sendCmd(String.format("register %s %s", apswd, auser.toLine()));
		if (iCom.getCmd().equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean login(int aid, String apswd ,int astate) {
		iId = aid;
		String bcmd = String.format("login %d %d %s",iId,astate,apswd);
		iCom.sendCmd(bcmd);
		bcmd = iCom.getCmd();
		if (bcmd.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void getFrdList(Map<String, List<User>> amap) {
		iCom.sendCmd("getFrdList");
		String bcmd = iCom.getCmd();
		if (bcmd.length() == 0)
			return;
		User.toGroupMap(bcmd, amap);
	}

	public void getNotes(List<Info> alist,List<InfoWithPhoto> alist2) {
		iCom.sendCmd("getNotes");
		String bcmd = iCom.getCmd();
		if (bcmd.length() != 0)
			Info.toList(bcmd, alist);

		bcmd=iCom.getCmd();
		if(bcmd.length()!=0){
			InfoWithPhoto.toListWithoutPhoto(alist2, bcmd);
			ChatSocket bfrom=iCom.getLink();
			new Thread(new GetPhoto(alist2, bfrom)).start();
		}
	}
	class GetPhoto implements Runnable{
		private ChatSocket ifrom;
		private List<InfoWithPhoto> ilist;
		public GetPhoto(List<InfoWithPhoto> alist,ChatSocket afrom){
			ilist=alist;
			ifrom=afrom;
		}
		public void run(){
			String bcmd=ifrom.receive();
			InfoWithPhoto.toPhotoList(bcmd,ilist);
			InfoWithPhoto.writePhotoFile(ilist);
			ihook.getPhotos(ilist);
		}
	}
	public void getAskFrd(List<AskFrd> alist){
		iCom.sendCmd("getAskFrd");
		String bcmd=iCom.getCmd();
		if(bcmd.length()==0){
			return;
		}
		AskFrd.toList(bcmd, alist);
	}
	private boolean iIsListenInfo;

	/**
	 * @Title: startupListener
	 * @Description:启动消息监听器，接受消息和更新状态， 例如好友添加成功，上下线，加为好友请求，消息
	 * @return void
	 * @throws
	 */
	public void startupListener() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				iIsListenInfo = true;
				while (iIsListenInfo) {
					Scanner bscan = new Scanner(iCom.getInfo());
					try {
						WorkClt.class.getMethod(bscan.next(),
								new Class[] { Scanner.class }).invoke(
								WorkClt.this, new Object[] { bscan });
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		iCom.sendCmd("startupListener");
	}

	/*******************************************************************************
	 * manage friend
	 ********************************************************************************/
	public void findFrd(List<User> alist, User auser) {
		iCom.sendCmd("findFrd " + auser.toLineWithParameter());
		String bcmd = iCom.getCmd();
		if (bcmd.length() == 0)
			return;
		User.toList(bcmd, alist);
	}

	public void addFrd(int aid, String amsg) {
		AskFrd bask = new AskFrd(iId, aid, amsg, new Date());
		iCom.sendCmd("addFrd " + bask.toLine());
	}
	
	public void getAskFrdRespond(Scanner ascan){
		int bid=ascan.nextInt();
		boolean bis=ascan.nextBoolean();
		
		ihook.getAskFrdRespond(bid,bis);
	}
	public void askForFrd(Scanner ascan) {
		AskFrd bask = new AskFrd(ascan.nextLine());

		// hook
		ihook.askForFrd(bask);
	}

	public void askFrdRespond(int aid, boolean aIsAgree) {
		iCom.sendCmd(String.format("askFrdRespond %d %s", aid, aIsAgree));
	}

	public void delFrd(int aid) {
		iCom.sendCmd("delFrd " + aid);
		iCom.getCmd();
	}

	public void delFrd(Scanner ascan) {
		int bid = ascan.nextInt();

		// hook
		ihook.delFrd(bid);
	}

	public void renameFrd(int aid, String aname) {
		iCom.sendCmd(String.format("renameFrd %d %s", aid, aname));
		iCom.getCmd();
	}

	/*******************************************************************************
	 * manage group
	 ********************************************************************************/
	public void addGroup(String aname) {
		iCom.sendCmd("addGroup " + aname);
		iCom.getCmd();
	}

	public void delGroup(String aname) {
		iCom.sendCmd("delGroup " + aname);
		iCom.getCmd();
	}

	public void renameGroup(String aOldName, String aNewName) {
		iCom.sendCmd(String.format("renameGroup %s %s", aOldName, aNewName));
		iCom.getCmd();
	}

	public void moveToGroup(int aid, String aname) {
		iCom.sendCmd(String.format("moveToGroup %d %s", aid, aname));
		iCom.getCmd();
	}

	/*******************************************************************************
	 * transmit information
	 ********************************************************************************/
	public void sendInfo(int aid, String ainfo) {
		Info binfo = new Info(iId, aid, new Date(), ainfo);
		iCom.sendCmd("sendInfo " + binfo.toLine());
	}

	public void sendInfo(Scanner ascan) {
		String bstr = ascan.nextLine();
		Info binfo = new Info(bstr);

		// hook
		ihook.sentInfo(binfo);
	}

	public void sendInfoWithPhoto(InfoWithPhoto ainfo){
		iCom.sendCmd("sendInfoWithPhoto "+ainfo.toPartLine());
		iCom.getCmd();
		ChatSocket bsocket=iCom.getLink();
		
		new Thread(new SendPhoto(bsocket,ainfo)).start();
	}
	class SendPhoto implements Runnable{
		private ChatSocket ito;
		private InfoWithPhoto iinfo;
		public SendPhoto(ChatSocket ato,InfoWithPhoto ainfo){
			ito=ato;
			iinfo=ainfo;
		}
		public void run(){
			ito.send(iinfo.toPhotoLine());
		}
	}
	/*******************************************************************************
	 * close function
	 ********************************************************************************/
	public void requestClose() {
		System.out.println("requestclose()!!");
		iCom.sendCmd("requestClose");
		iCom.sendInfo("requestClose");
	}

	public void closeWork(Scanner bscan) {
		System.out.println("closeWork(scanner)!!");
		iIsListenInfo = false;
		closeWork();
	}

	public void requestClose(Scanner ascan) {
		iIsListenInfo = false;
		iCom.sendCmd("closeWork");
		iCom.sendInfo("closeWork");
		closeWork();
	}

	public void closeWork() {
		System.out.println("closeWork()!!");
		iCom.closeCom();
	}
}
/*
 * WorkClt：setCom、login、getFrdList、getNotes、startupListener 、findFrd、addFrd
 * 、delFrd、renameFrd、addGroup、delGroup、renameGroup、
 * moveToGroup(id,group)、sentInfo、getInfo、requestClose、closeWork。
 */
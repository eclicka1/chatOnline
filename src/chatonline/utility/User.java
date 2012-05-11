package chatonline.utility;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {
/*******************************************************************************
                                     filed   
                     notice : all the fields don't contain blank                    
********************************************************************************/	
public int iId=-1;
public String iName;
public String iRemark;
public String iAddress;
public Date iBirthday;
public String iSignature;//no contains enter
public int iSex=-1;//0=man 1=woman
public int iState=-1;
public Date iRegisterDay;

/*******************************************************************************
                                   constructor                    
********************************************************************************/
public static int OFFLINE=2;
public static int ONLINE=1;
public static int HIDE=3;
public static int BOY=0;
public static int Girl=1;
public User(){
	
}
public User(int aid, String aname, String aremark, String iaddress,
		Date abirthday, String asignature, int isex
		,int astate,Date aRegisterDay) {
	this.iId = aid;
	this.iName = aname;
	this.iRemark = aremark;
	this.iAddress = iaddress;
	this.iBirthday = abirthday;
	this.iSignature = asignature;
	this.iSex = isex;
	this.iState=astate;
	this.iRegisterDay=aRegisterDay;
}
public User(int aid, String aname, String aremark
		,String asignature,int astate){
	this.iId = aid;
	this.iName = aname;
	this.iRemark = aremark;
	this.iSignature = asignature;
	this.iState=astate;
}
public User(String aline,boolean aIsAll){
	if (aIsAll) {
		initFromLine(aline);
	}else{
		initFromPartLine(aline);
	}
}

/*******************************************************************************
                                String <！！！！>User                       
********************************************************************************/
	@Override
	public String toString() {
		return String.format("%d %s %s %s %d %s %d %d %d", iId, iName, iRemark,
				iAddress, iBirthday.getTime(), iSignature, iSex, iState,
				iRegisterDay.getTime());
	}

	public String toLine() {
		return String.format("%d %s %s %s %d %s %d %d %d", iId, iName, iRemark,
				iAddress, iBirthday.getTime(),
				iSignature.replace(OLD_SPACE, NEW_SPACE), iSex, iState,
				iRegisterDay.getTime());
	}

	public String toPartLine() {
		return String.format("%d %s %s %s %d", iId, iName, iRemark,
				iSignature.replace(OLD_SPACE, NEW_SPACE), iState);
	}

	public String toLineWithParameter() {
		StringBuilder bbld = new StringBuilder();
		if (iId != -1) {
			bbld.append(" iId " + iId);
		}
		if (iName != null) {
			bbld.append(" iName " + iName);
		}
		if (iRemark != null) {
			bbld.append(" iRemark " + iRemark);
		}
		if (iAddress != null) {
			bbld.append(" iAddress " + iAddress);
		}
		if (iBirthday != null) {
			bbld.append(" iBirthday " + iBirthday.getTime());
		}
		if (iSignature != null) {
			bbld.append(" iSignature "
					+ iSignature.replace(OLD_SPACE, NEW_SPACE));
		}
		if (iSex != -1) {
			bbld.append(" iSex " + iSex);
		}
		if (iState != -1) {
			bbld.append(" iState " + iState);
		}
		if (iRegisterDay != null) {
			bbld.append(" iRegisterDay " + iRegisterDay.getTime());
		}
		if (bbld.length() == 0) {
			return null;
		}
		return bbld.substring(1);
	}

	/*
	 * iId,iName,iRemark,iAddress,iBirthday.getTime()
	 * ,iSignature,iSex,iState,iRegisterDay.getTime()
	 */

	public void initFromLine(String astr) {
		Scanner bscan = new Scanner(astr);
		this.iId = bscan.nextInt();
		this.iName = bscan.next();
		this.iRemark = bscan.next();
		this.iAddress = bscan.next();
		this.iBirthday = new Date(bscan.nextLong());
		this.iSignature = bscan.next().replace(NEW_SPACE, OLD_SPACE);
		this.iSex = bscan.nextInt();
		this.iState = bscan.nextInt();
		this.iRegisterDay = new Date(bscan.nextLong());
		bscan.close();
	}

	public void initFromPartLine(String astr) {
		Scanner bscan = new Scanner(astr);
		this.iId = bscan.nextInt();
		this.iName = bscan.next();
		this.iRemark = bscan.next();
		this.iSignature = bscan.next().replace(NEW_SPACE, OLD_SPACE);
		this.iState = bscan.nextInt();
		bscan.close();
	}

	public void initFromStrWithParameter(String astr) {
		System.out.println("initfrom:"+astr);
		
		if(astr==null||astr.equals(""))
			return ;
		Scanner bscan = new Scanner(astr);
		try {
			while (bscan.hasNext()) {
				String bfield = bscan.next();
				if (bfield.equals("iId") || bfield.equals("iState")
						|| bfield.equals("iSex")) {
					getClass().getField(bfield).set(this, bscan.nextInt());
				} else if (bfield.equals("iBirthday")
						|| bfield.equals("iRegisterDay")) {
					getClass().getField(bfield).set(this,
							new Date(bscan.nextLong()));
				} else if (bfield.equals("iSignature")) {
					this.iSignature = bscan.next()
							.replace(NEW_SPACE, OLD_SPACE);
				} else {
					getClass().getField(bfield).set(this, bscan.next());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static char USER_END=(char)0;
	private static char GROUP_END=(char)1;
	private static char NEW_SPACE=(char)2;
	private static char OLD_SPACE=(char)' ';
/*******************************************************************************
                                static method                       
********************************************************************************/
public static String toPartLine(String agroup,List<User> alist){
	StringBuilder bbld=new StringBuilder();
	bbld.append(agroup);
	bbld.append(USER_END);
	for(User busr:alist){
		bbld.append(busr.toPartLine());
		bbld.append(USER_END);
	}
	return bbld.toString();
}

public static String toPartList(String agroup,List<User> alist){
	String[] bstr=agroup.split(""+USER_END);
	for (int i = 1; i < bstr.length; i++) {
		alist.add(new User(bstr[i],false));
	}
	return bstr[0];
}
public static String toLine(List<User> alist){
	StringBuilder bbld=new StringBuilder();
	for(User busr:alist){
		bbld.append(busr.toLine());
		bbld.append(USER_END);
	}
	return bbld.toString();
}

public static void toList(String ausers,List<User> alist){
	String[] bstr=ausers.split(""+USER_END);
	for (String bi:bstr) {
		alist.add(new User(bi,true));
	}
}

public static void mergeGroup(StringBuilder aone,String aother){
	if(aone.length()!=0)
		aone.append(GROUP_END);
	aone.append(aother);
}
public static void toGroupMap(String astr,Map<String,List<User>> amap){
	List<User> blist;
	String bgroup;
	String[] bgroups=astr.split(""+GROUP_END);
	for(String bstr:bgroups){
		blist=new LinkedList<User>();
		bgroup=toPartList(bstr, blist);
		amap.put(bgroup, blist);
	}
}
}






















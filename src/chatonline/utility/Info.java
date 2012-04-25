package chatonline.utility;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Info {//express a message
public int iFromId;
public int iToId;
public Date iDate;
public String iInfo;

public Info(){}
public Info(int afrom,int ato,Date adate,String ainfo){
	iFromId=afrom;
	iToId=ato;
	iDate=adate;
	iInfo=ainfo;
}
public Info(int afrom,int ato,long adate,String ainfo){
	iFromId=afrom;
	iToId=ato;
	iDate=new Date(adate);
	iInfo=ainfo;
}
public Info(String ainfo){
	initFromStr(ainfo);
}
@Override
public String toString(){
	StringBuilder bbld=new StringBuilder();
	bbld.append(iFromId);
	bbld.append(" ");
	bbld.append(iToId);
	bbld.append(" ");
	bbld.append(iDate.getTime());
	bbld.append(" ");
	bbld.append(iInfo);
	return bbld.toString();
}
public String toLine(){
	StringBuilder bbld=new StringBuilder();
	bbld.append(iFromId);
	bbld.append(" ");
	bbld.append(iToId);
	bbld.append(" ");
	bbld.append(iDate.getTime());
	bbld.append(" ");
	bbld.append(iInfo.replace(LINE_OLD, LINE_NEW));
	return bbld.toString();
}
public void initFromStr(String aStr){
	initFromLine(aStr.replace(LINE_OLD, LINE_NEW));
}
public void initFromLine(String aStr){
	Scanner bscan=new Scanner(aStr);
	iFromId=bscan.nextInt();
	iToId=bscan.nextInt();
	iDate=new Date(bscan.nextLong());
	iInfo=bscan.nextLine().replace(LINE_NEW, LINE_OLD);
	iInfo=iInfo.substring(1);//去除第一个空格
	bscan.close();
}
private static char LINE_NEW=(char)0;
private static char LINE_OLD='\n';
private static char INFO_END=(char)1;

public static void toList(String astr,List<Info> alist){
	String[] binfos=astr.split(""+INFO_END);
	for(String binfo:binfos){
		alist.add(new Info(binfo));
	}
}
public static String toLine(List<Info> alist){
	StringBuilder bbld=new StringBuilder();
	for(Info binfo:alist){
		bbld.append(binfo.toLine());
		bbld.append(INFO_END);
	}
	return bbld.toString();
}
//public String toString(){
//	StringBuilder bbld=new StringBuilder();
//	bbld.append(iFromId);
//	bbld.append(" ");
//	bbld.append(iToId);
//	bbld.append(" ");
//	bbld.append(iDate.getTime());
//	bbld.append(" ");
//	bbld.append(iInfo.size());
//	for (int i = 0; i < iInfo.size(); i++) {
//		bbld.append(" ");
//		bbld.append(iInfo.get(i));
//	}
//	return bbld.toString();
//}
}

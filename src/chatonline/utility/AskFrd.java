package chatonline.utility;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AskFrd {
	public static void main(String[] args) {

	}
	public int ifromid;
	public int itoid;
	public Date idate;
	public String imsg;
	
	public AskFrd(){}
	public AskFrd(int afrom,int ato,String amsg,Date adate){
		ifromid=afrom;
		itoid=ato;
		idate=adate;
		imsg=amsg;
	}
	public AskFrd(String aline){
		initFromLine(aline);
	}
	public String toLine(){
		return String.format("%d %d %d %s", ifromid, itoid, idate.getTime(),
				imsg.replace(NEW_LINE, OLD_LINE));
	}
	public void initFromLine(String aline){
		Scanner bscan=new Scanner(aline);
		ifromid=bscan.nextInt();
		itoid=bscan.nextInt();
		idate=new Date(bscan.nextLong());
		imsg=bscan.nextLine().replace(OLD_LINE, NEW_LINE).substring(1);
	}
	private static char NEW_LINE=(char)0;
	private static char OLD_LINE='\n';
	private static char ASKFRD_END=(char)1;
	/*******************************************************************************
                                static method                       
********************************************************************************/
	public static String toLine(List<AskFrd> alist){
		StringBuilder bbld=new StringBuilder();
		for(AskFrd bask:alist){
			bbld.append(bask.toLine());
			bbld.append(ASKFRD_END);
		}
		return bbld.toString();
	}
	public static void toList(String astr,List<AskFrd> alist){
		String[] basks=astr.split(""+ASKFRD_END);
		for(String bstr:basks){
			alist.add(new AskFrd(bstr));
		}
	}
}

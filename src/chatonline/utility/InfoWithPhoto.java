package chatonline.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: InfoWithPhoto
 * @Description:
 * @author Li
 * @date 2012-4-25 上午12:54:09
 * 
 */
public class InfoWithPhoto {
	public List<String> itxt;//含有空格和回车
	private List<String> iphoto;//含有空格和回车
    private List<String> isuffix;//文件后缀

	private static char NEW_LINE = (char) 0;
	private static char OLD_LINE = '\n';
	private static char INFO_END = (char) 1;
	private static char SUFFIX_END=(char) 2;
	
	public void addPhoto(String afile,String aname){
		isuffix.add(aname);
		iphoto.add(afile);
	}
	public void delPhoto(int aindex){
		iphoto.remove(aindex);
		isuffix.remove(aindex);
	}
	public void clearPhoto(){
		iphoto.clear();
		isuffix.clear();
	}
	public InfoWithPhoto() {
		itxt=new LinkedList<String>();
		iphoto=new LinkedList<String>();
		System.out.println("constructor!!");
	}

	public InfoWithPhoto(String aline) {
		this();
		initFromTxtLine(aline);
	}

	public String toTxtLine() {//result 含有空格  没有回车
		StringBuilder bbld = new StringBuilder();
		for (String bstr : itxt) {
			bbld.append(bstr.replace(OLD_LINE, NEW_LINE));
			bbld.append(INFO_END);
		}
		return bbld.toString();
	}

	public void initFromTxtLine(String aline) {//aline 参数只能使用scanner.nextLine()获取，里面含有空格
		String[] bstrs = aline.split("" + INFO_END);
		for (String bstr : bstrs) {
			itxt.add(bstr.replace(NEW_LINE, OLD_LINE));
		}
	}

	public String toFileLine(){//result 含有空格  没有回车
		StringBuilder bbld = new StringBuilder();
		for(String bstr:isuffix){
			bbld.append(bstr);
			bbld.append(INFO_END);
		}
		bbld.append(SUFFIX_END);
		for (String bstr : iphoto) {
			bbld.append(bstr.replace(OLD_LINE, NEW_LINE));
			bbld.append(INFO_END);
		}
		return bbld.toString();
	}
	
	public void initFromFileLine(String aline){//aline 参数只能使用scanner.nextLine()获取，里面含有空格
		String[] bSuffixFile = aline.split("" + SUFFIX_END);
		String[] bsuffix=bSuffixFile[0].split(""+INFO_END);
		String[] bfile=bSuffixFile[1].split(""+INFO_END);
		for(String bstr:bsuffix){
			isuffix.add(bstr);
		}
		for (String bstr : bfile) {
			iphoto.add(bstr.replace(NEW_LINE, OLD_LINE));
		}
	}
	
	public void writePhotoFile(long adate){
		int num=iphoto.size();
		String bfile;
		FileOutputStream out;
		for(int i=0;i<num;i++){
			bfile=String.format("%d%d.%s", adate,i,isuffix.get(i));
			try {
				out=new FileOutputStream(bfile);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
	
	public static void main(String[] args) {
		InfoWithPhoto it=new InfoWithPhoto("ssss");
		it.itxt.add("jjjj");
		System.out.println(it.toTxtLine());
		InfoWithPhoto it2=new InfoWithPhoto(it.toTxtLine());
		System.out.println(it2.toTxtLine());
	}
}

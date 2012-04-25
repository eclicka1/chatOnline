package chatonline.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @ClassName: InfoWithPhoto
 * @Description:
 * @author Li
 * @date 2012-4-25 上午12:54:09
 * 
 */
public class InfoWithPhoto {
	public List<String> itxt;// 含有空格和回车
	private List<String> iphoto;// 含有空格和回车
	private List<String> isuffix;// 文件后缀

	public int iFromId;
	public int iToId;
	public Date idate;

	private static char NEW_LINE = (char) 0;
	private static char OLD_LINE = '\n';
	private static char INFO_END = (char) 1;
	private static char SUFFIX_END = (char) 2;
	private static char INFOPHOTO_END = (char) 3;

	public String toTxtLine() {
		StringBuilder bbld = new StringBuilder();
		for (String bstr : isuffix) {
			bbld.append(bstr);
			bbld.append(" ");
		}
		bbld.append(INFO_END);
		for (String bstr : itxt) {
			bbld.append(bstr);
			bbld.append(INFO_END);
		}
		return bbld.toString();
	}

	public void initFromTxtLine(String aline) {
		String[] bTxtSuffix = aline.split("" + INFO_END);
		Scanner bsuffix = new Scanner(bTxtSuffix[0]);
		while (bsuffix.hasNext()) {
			isuffix.add(bsuffix.next());
		}
		for (int i = 1; i < bTxtSuffix.length; i++) {
			itxt.add(bTxtSuffix[i]);
		}
	}

	public void readPhotoFile() {
		int num = itxt.size() - 1;// null 1 2 null = 3 个photo
		String bfile;
		File file = null;
		FileInputStream in = null;
		for (int i = 0; i < num; i++) {
			bfile = String.format("%d\\%d%d.%s", iFromId, idate.getTime()/1000L, i,
					isuffix.get(i));
			try {
				file = new File(bfile);
				in = new FileInputStream(file);
				BASE64Encoder bencoder = new BASE64Encoder();
				byte[] bbyte = new byte[(int) file.length()];
				in.read(bbyte);
				iphoto.add(bencoder.encode(bbyte));
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getPhotoSize() {
		if (iphoto.size() == 0)
			return iphoto.size();
		else
			return itxt.size() - 1;
	}

	public InfoWithPhoto(int afrom, int ato, Date adate) {
		itxt = new LinkedList<String>();
		iphoto = new LinkedList<String>();
		isuffix = new LinkedList<String>();

		iFromId = afrom;
		iToId = ato;
		idate = adate;
	}

	public InfoWithPhoto(String aline) {
		itxt = new LinkedList<String>();
		iphoto = new LinkedList<String>();
		isuffix = new LinkedList<String>();

		initFromPartLine(aline);
	}

	public String toPartLine() {// result 含有空格 没有回车
		StringBuilder bbld = new StringBuilder();
		bbld.append(String
				.format("%d %d %d%c", iFromId, iToId, idate, INFO_END));
		for (String bstr : itxt) {
			bbld.append(bstr.replace(OLD_LINE, NEW_LINE));
			bbld.append(INFO_END);
		}
		return bbld.toString();
	}

	public void initFromPartLine(String aline) {// 参数只能使用scanner.nextLine()获取，里面含有空格
		String[] bstrs = aline.split("" + INFO_END);
		Scanner bscan = new Scanner(bstrs[0]);
		iFromId = bscan.nextInt();
		iToId = bscan.nextInt();
		idate = new Date(bscan.nextLong());
		for (int i = 1; i < bstrs.length; i++) {
			itxt.add(bstrs[i].replace(NEW_LINE, OLD_LINE));
		}
	}

	public String toPhotoLine() {// result 含有空格 没有回车
		StringBuilder bbld = new StringBuilder();
		for (String bstr : isuffix) {
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

	public void initFromPhotoLine(String aline) {// 参数只能使用scanner.nextLine()获取，里面含有空格
		String[] bSuffixFile = aline.split("" + SUFFIX_END);
		String[] bsuffix = bSuffixFile[0].split("" + INFO_END);
		String[] bfile = bSuffixFile[1].split("" + INFO_END);
		for (String bstr : bsuffix) {
			isuffix.add(bstr);
		}
		for (String bstr : bfile) {
			iphoto.add(bstr.replace(NEW_LINE, OLD_LINE));
		}
	}

	public void writePhotoFile() {
		int num = iphoto.size();
		String bfile;
		FileOutputStream out;
		for (int i = 0; i < num; i++) {
			bfile = String.format("%d\\%d%d.%s", iFromId, idate.getTime()/1000L, i,
					isuffix.get(i));
			try {
				out = new FileOutputStream(bfile);
				BASE64Decoder bdecoder = new BASE64Decoder();
				byte[] bbyte = bdecoder.decodeBuffer(iphoto.get(i));
				for (int ii = 0; ii < bbyte.length; ++ii) {
					if (bbyte[i] < 0) {// 调整异常数据
						bbyte[i] += 256;
					}
				}
				out.write(bbyte);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void readPhotoFile(List<String> alist) {
		int bSuffixIndex;
		File file = null;
		FileInputStream in = null;
		byte[] bbyte = null;
		BASE64Encoder encoder = new BASE64Encoder();
		for (String bstr : alist) {
			bSuffixIndex = bstr.lastIndexOf(".") + 1;
			isuffix.add(bstr.substring(bSuffixIndex));

			try {
				file = new File(bstr);
				in = new FileInputStream(file);
				bbyte = new byte[(int) file.length()];
				in.read(bbyte);
				iphoto.add(encoder.encode(bbyte));
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String toPhotoString(String afilename) {
		FileInputStream in = null;
		byte[] bbyte = null;
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			in = new FileInputStream(afilename);
			in.read(bbyte);
			in.close();
			return encoder.encode(bbyte);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void toListWithoutPhoto(List<InfoWithPhoto> alist,
			String aline) {
		String[] binfos=aline.split(""+INFOPHOTO_END);
		for(String binfo:binfos){
			alist.add(new InfoWithPhoto(binfo));
		}
	}
	
	public static String toLineWithoutPhoto(List<InfoWithPhoto> alist) {
		StringBuilder bbld=new StringBuilder();
		for(InfoWithPhoto binfo:alist){
			bbld.append(binfo.toPartLine());
			bbld.append(INFOPHOTO_END);
		}
		return bbld.toString();
	}

	public static String toPhotoLine(List<InfoWithPhoto> alist){
		StringBuilder bbld=new StringBuilder();
		for(InfoWithPhoto binfo:alist){
			bbld.append(binfo.toPhotoLine());
			bbld.append(INFOPHOTO_END);
		}
		return bbld.toString();
	}
	
	public static void toPhotoList(String aline,List<InfoWithPhoto> alist){
		String[] binfos=aline.split(""+INFOPHOTO_END);
		for(int i=0;i<binfos.length;i++){
			alist.get(i).initFromPhotoLine(binfos[i]);
		}
	}
	public static void writePhotoFile(List<InfoWithPhoto> alist){
		for(InfoWithPhoto binfo:alist){
			binfo.writePhotoFile();
		}
	}
	public void addPhoto(String afile, String aname) {
		isuffix.add(aname);
		iphoto.add(afile);
	}

	public void delPhoto(int aindex) {
		iphoto.remove(aindex);
		isuffix.remove(aindex);
	}

	public void clearPhoto() {
		iphoto.clear();
		isuffix.clear();
	}

	public static void main(String[] args) {
		InfoWithPhoto it = new InfoWithPhoto("ssss");
		it.itxt.add("jjjj");
		System.out.println(it.toPartLine());
		InfoWithPhoto it2 = new InfoWithPhoto(it.toPartLine());
		System.out.println(it2.toPartLine());
	}
}

package chatonline;
public class ForTest {
	public static void main(String[] args) {
		/*
		 * PrintStream P=System.out; //测试不可见字符的读取不受影响 String
		 * bStr=String.format("123 %s %d 1%c2   ","Hello",33,(char)0); Scanner
		 * bScanner=new Scanner(bStr); String bTemp;
		 * P.println(bScanner.nextInt()); P.println(bScanner.next());
		 * P.println(bScanner.nextInt()); P.println(bTemp=bScanner.next());
		 * P.println(bTemp.charAt(1)==0); P.println(bScanner.hasNext());
		 */

		/*
		 * String bStr="1 \nhello \nworld \n!!"; String bStr2=bStr.replace('\n',
		 * (char) 0); Scanner bscan=new Scanner(bStr2);
		 * System.out.println(bStr); System.out.println(bscan.nextInt());
		 * System.out.println(bscan.nextLine().substring(1));
		 * System.out.println("split result!"); String[]
		 * barray="1231321".split("1"); System.out.println(barray[0]);
		 * System.out.println(barray[1]); System.out.println(barray[2]); //
		 * System.out.println(barray[3]);
		 * 
		 * System.out.println("1234".split("4").length);
		 */

		/*
		 * StringBuilder bbld=new StringBuilder(); bbld.append((char)1);
		 * System.out.println(bbld.length()); int i=0; System.out.println(++i);
		 */

		/*
		 * JFrame j=new JFrame();
		 * j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 * j.setVisible(true);
		 * 
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub int
		 * i=0; while(true){ System.out.println(i++); try {
		 * TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } } }).start();
		 */

		// System.out.println(new User()); error

		/*
		 * System.out.println(new User().toStringWithParameter()); User
		 * buser=new User(11,"lhl","lihailin","xd",new
		 * Date(),"author",1,User.ONLINE,new Date());
		 * System.out.println(buser.toStringWithParameter()); User buser2=new
		 * User();
		 * buser2.initFromStrWithParameter(buser.toStringWithParameter());
		 * System.out.println(buser2.toString());
		 */

		/*
		 * Scanner bscan=new Scanner(System.in); ComModuleClt bCom = new
		 * ComModuleClt(); bCom.initCom(); WorkClt clt=new WorkClt();
		 * clt.setCom(bCom); if(clt.login(12, "123")){ clt.startupListener();
		 * while(true){ clt.sentInfo(11, bscan.nextLine()); }
		 * //clt.requestClose(); }
		 */

		/*
		 * StringBuilder bbld=new StringBuilder();
		 * if(bbld.toString().equals("")){
		 * System.out.println("there are not any chars!!"); }
		 * System.out.println(bbld);
		 * 
		 * System.out.println(bbld.toString().split(" ").length);
		 */

		/*
		 * SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * System.out.println(df.format(new Date()));
		 */

		/*
		 * Connection con = DBMSTool.getConnection(); PreparedStatement stmt =
		 * null; String sqlstr;
		 * 
		 * try { sqlstr = "insert into chatuser values(?,?,?,?,?,?,?,?,?)"; stmt
		 * = con.prepareStatement(sqlstr); stmt.setInt(1, 5); stmt.setString(2,
		 * "jbb"); stmt.setString(3, "11"); stmt.setString(4, "xd");
		 * stmt.setString(5, "2001-03-12 12:12:12"); stmt.setString(6, "ddd");
		 * stmt.setInt(7, 1); stmt.setInt(8, 2); stmt.setString(9,
		 * "2001-03-12 12:12:12");
		 * 
		 * stmt.executeUpdate(); } catch (Exception e) { e.printStackTrace(); }
		 */

		/*
		 * boolean i=false; System.out.print(String.format("%s", i));
		 */

		/*
		 * Scanner it=new Scanner("123\n45678910"); it.useDelimiter("6|\n");
		 * System.out.println(it.next()); System.out.println(it.nextInt());
		 */
		
		/*******************************************************************************
                                photo file to string                       
********************************************************************************/
		/*File file = new File("1.bmp");
		FileInputStream in = null;

		File filein = new File("2.bmp");
		FileOutputStream out = null;
		try {
			in = new FileInputStream(file);
			byte[] buf = new byte[(int) file.length()];
			in.read(buf);
			BASE64Encoder encoder = new BASE64Encoder();  
		    String bstr=encoder.encode(buf);
		    
		    
		    BASE64Decoder decoder=new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(bstr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            } 
			out = new FileOutputStream(filein);
			out.write(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		String bstr="123.lhl";
		int bint=bstr.lastIndexOf(".")+1;
		System.out.println(bstr.substring(bint));
	}
}

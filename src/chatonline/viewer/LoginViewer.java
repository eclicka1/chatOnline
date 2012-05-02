package chatonline.viewer;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;

import chatonline.controller.ComModuleClt;
import chatonline.controller.HookListenClt;
import chatonline.controller.WorkClt;
import chatonline.utility.AskFrd;
import chatonline.utility.Info;
import chatonline.utility.InfoWithPhoto;
import chatonline.utility.User;
import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import java.awt.event.WindowAdapter;

/** @ClassName: LoginViewer 
 * @Description: viewer for login
 * @author Li 
 * @date 2012-4-23 下午08:33:55 
 * 
 */
public class LoginViewer extends JFrame{
	private CardLayout iCardLayout=new CardLayout(0, 0);
	public LoginViewer() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				iWork.requestClose();
			}
		});
		iCom=new ComModuleClt();
		iCom.initCom();
		iWork=new WorkClt();
		iWork.setCom(iCom);
		iWork.setHook(new HookListenClt() {
			@Override
			public void sentInfo(Info ainfo) {
			}
			@Override
			public void getAskFrdRespond(int aid, boolean ais) {
			}
			@Override
			public void delFrd(int aid) {
			}
			@Override
			public void askForFrd(AskFrd aask) {
			}
			@Override
			public void getPhotos(List<InfoWithPhoto> alist) {
			}
		});
		
		/*******************************************************************************
                                view codes                       
********************************************************************************/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(iCardLayout);
		
		iRegisterPane = new JPanel();
		
		JLabel label_2 = new JLabel("\u540D\u5B57");
		
		iNameTxtFld = new JTextField();
		iNameTxtFld.setColumns(10);
		
		JLabel label_4 = new JLabel("\u4E2A\u6027\u7B7E\u540D");
		
		iSignatureTxtFld = new JTextField();
		iSignatureTxtFld.setColumns(10);
		
		JLabel label_3 = new JLabel("\u751F\u65E5");
		
		JLabel label_5 = new JLabel("\u6027\u522B");
		
		label_6 = new JLabel("\u5BC6\u7801");
		
		label_7 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		
		label_8 = new JLabel("\u5730\u5740");
		
		iAddressTxtFld = new JTextField();
		iAddressTxtFld.setColumns(10);
		
		iBoyRadioBttn = new JRadioButton("\u7537");
		buttonGroup.add(iBoyRadioBttn);
		
		iGirlRadioBttn = new JRadioButton("\u5973");
		buttonGroup.add(iGirlRadioBttn);
		
		JButton RegisterBttn = new JButton("\u6CE8\u518C");
		RegisterBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String bpswd1=new String(iPswdFld1.getPassword());
				String bpswd2=new String(iPswdFld2.getPassword());
				if(!bpswd1.equals(bpswd2)){
					JOptionPane.showMessageDialog(null, "password isn't same!", "warn",JOptionPane.ERROR_MESSAGE);
					return;
				}
				int bsex=User.BOY;
				if(iGirlRadioBttn.isSelected()){
					bsex=User.Girl;
				}
				try {
					User buser;
					buser = new User(0, iNameTxtFld.getText(), "none", iAddressTxtFld.getText()
							, iDateFormat.parse(iBirthdayTxtFld.getText())
							, iSignatureTxtFld.getText(), bsex, User.OFFLINE, new Date());
					if(iWork.register(buser, bpswd1)){
						iIdLabel.setText(""+buser.iId);
						iIdBuf=buser.iId;
						iCardLayout.show(LoginViewer.this.getContentPane(), "name_14760469390449");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton RegisterToLoginBttn = new JButton("\u767B\u5F55");
		RegisterToLoginBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iCardLayout.show(LoginViewer.this.getContentPane(),"name_29273997257395");
			}
		});
		
		iLoginPane = new JPanel();
		getContentPane().add(iLoginPane, "name_29273997257395");
		getContentPane().add(iRegisterPane, "name_29260218592308");
		iDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		DateFormatter df=new DateFormatter(iDateFormat);
		iBirthdayTxtFld = new JFormattedTextField(df);
		
		iPswdFld1 = new JPasswordField();
		
		iPswdFld2 = new JPasswordField();
		GroupLayout gl_iRegisterPane = new GroupLayout(iRegisterPane);
		gl_iRegisterPane.setHorizontalGroup(
			gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_6)
								.addComponent(label_8))
							.addGap(24)
							.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_iRegisterPane.createSequentialGroup()
									.addComponent(iPswdFld1, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(label_7)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(iPswdFld2, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
								.addComponent(iAddressTxtFld, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2)
								.addComponent(label_3))
							.addGap(24)
							.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_iRegisterPane.createSequentialGroup()
									.addComponent(iNameTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_iRegisterPane.createSequentialGroup()
									.addComponent(iBirthdayTxtFld)
									.addGap(27)))
							.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(Alignment.TRAILING, gl_iRegisterPane.createSequentialGroup()
									.addComponent(label_5)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(iBoyRadioBttn)
									.addGap(18)
									.addComponent(iGirlRadioBttn))
								.addComponent(iSignatureTxtFld, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))))
					.addGap(157))
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(240)
					.addComponent(RegisterToLoginBttn)
					.addGap(6)
					.addComponent(RegisterBttn)
					.addGap(74))
		);
		gl_iRegisterPane.setVerticalGroup(
			gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2)
						.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(iNameTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_4)
							.addComponent(iSignatureTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(iBirthdayTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(iGirlRadioBttn)
						.addComponent(iBoyRadioBttn)
						.addComponent(label_5))
					.addGap(10)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_6)
						.addComponent(iPswdFld1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_7)
						.addComponent(iPswdFld2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_8)
						.addComponent(iAddressTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addComponent(RegisterToLoginBttn)
						.addComponent(RegisterBttn)))
		);
		iRegisterPane.setLayout(gl_iRegisterPane);
		JLabel label = new JLabel("\u5E10\u53F7\uFF1A");
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		
		iPswdTxtFld0 = new JPasswordField();
		
		JButton LoginBttn = new JButton("\u767B\u5F55");
		LoginBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//get data
				long bid=(Long)iIdTxtFld.getValue();
				String bpswd=new String(iPswdTxtFld0.getPassword());
				int bstate;
				if(iHideRadioBttn.isSelected()){
					bstate=User.HIDE;
				}else{
					bstate=User.ONLINE;
				}
				//operate data
				iWork.login((int)bid, bpswd, bstate);
				
				LoginViewer.this.setVisible(false);
				JFrame bfrdView=new JFrame();
				bfrdView.addWindowListener(new WindowListener(){
				@Override
				public void windowActivated(WindowEvent arg0) {
				}
				public void windowClosed(WindowEvent arg0) {
				}
				public void windowClosing(WindowEvent arg0) {
					iWork.requestClose();
				}
				public void windowDeactivated(WindowEvent arg0) {
				}
				public void windowDeiconified(WindowEvent arg0) {
				}
				public void windowIconified(WindowEvent arg0) {
				}
				public void windowOpened(WindowEvent arg0) {
				}
             } );
				bfrdView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				bfrdView.setSize(200, 200);
				//bfrdView.setLocation(200, 200);
				bfrdView.setLocationRelativeTo(null);//在屏幕中央显示
				bfrdView.setVisible(true);
			}
		});
		
		JButton ToRegisterBttn = new JButton("\u6CE8\u518C");
		ToRegisterBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iCardLayout.show(LoginViewer.this.getContentPane(),"name_29260218592308");
			}
		});
		
		iHideRadioBttn = new JRadioButton("\u6F5C\u6C34");
		
		iIdTxtFld = new JFormattedTextField(NumberFormat.getInstance());
		GroupLayout gl_iLoginPane = new GroupLayout(iLoginPane);
		gl_iLoginPane.setHorizontalGroup(
			gl_iLoginPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iLoginPane.createSequentialGroup()
					.addGap(109)
					.addGroup(gl_iLoginPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(label)
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_iLoginPane.createParallelGroup(Alignment.LEADING)
						.addComponent(iIdTxtFld, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(iPswdTxtFld0, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
					.addGap(108))
				.addGroup(gl_iLoginPane.createSequentialGroup()
					.addGap(124)
					.addComponent(iHideRadioBttn)
					.addGap(18)
					.addComponent(ToRegisterBttn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(LoginBttn)
					.addContainerGap(123, Short.MAX_VALUE))
		);
		gl_iLoginPane.setVerticalGroup(
			gl_iLoginPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iLoginPane.createSequentialGroup()
					.addGap(63)
					.addGroup(gl_iLoginPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(iIdTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_iLoginPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(iPswdTxtFld0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(40)
					.addGroup(gl_iLoginPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(iHideRadioBttn)
						.addComponent(ToRegisterBttn)
						.addComponent(LoginBttn))
					.addContainerGap(84, Short.MAX_VALUE))
		);
		iLoginPane.setLayout(gl_iLoginPane);
		
		iInfoPane = new JPanel();
		getContentPane().add(iInfoPane, "name_14760469390449");
		
		JLabel label_9 = new JLabel("\u606D\u559C\u4F60\u6CE8\u518C\u6210\u529F\uFF1A");
		
		JLabel label_10 = new JLabel("\u4F60\u7684\u5E10\u53F7\uFF1A");
		
		iIdLabel = new JLabel("");
		
		JButton InfoToLoginBttn = new JButton("\u767B\u5F55");
		InfoToLoginBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iIdTxtFld.setValue(new Long(iIdBuf));
				iCardLayout.show(LoginViewer.this.getContentPane(),"name_29273997257395");
			}
		});
		GroupLayout gl_iInfoPane = new GroupLayout(iInfoPane);
		gl_iInfoPane.setHorizontalGroup(
			gl_iInfoPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iInfoPane.createSequentialGroup()
					.addGroup(gl_iInfoPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_iInfoPane.createSequentialGroup()
							.addGap(57)
							.addComponent(label_9))
						.addGroup(gl_iInfoPane.createSequentialGroup()
							.addGap(125)
							.addComponent(label_10)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(iIdLabel)))
					.addContainerGap(243, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_iInfoPane.createSequentialGroup()
					.addContainerGap(264, Short.MAX_VALUE)
					.addComponent(InfoToLoginBttn)
					.addGap(113))
		);
		gl_iInfoPane.setVerticalGroup(
			gl_iInfoPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iInfoPane.createSequentialGroup()
					.addGap(50)
					.addComponent(label_9)
					.addGap(18)
					.addGroup(gl_iInfoPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_10)
						.addComponent(iIdLabel))
					.addGap(76)
					.addComponent(InfoToLoginBttn)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		iInfoPane.setLayout(gl_iInfoPane);
	}
	
	private ComModuleClt iCom;
	private WorkClt iWork;
	
	private int iIdBuf;
	
	private DateFormat iDateFormat;
	
	private static final long serialVersionUID = 1L;
	private JPanel iRegisterPane;
	private JPanel iLoginPane;
	private JPasswordField iPswdTxtFld0;
	private JTextField iNameTxtFld;
	private JTextField iSignatureTxtFld;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JTextField iAddressTxtFld;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton iBoyRadioBttn;
	private JRadioButton iGirlRadioBttn;
	private JRadioButton iHideRadioBttn;
	private JFormattedTextField iIdTxtFld;
	private JFormattedTextField iBirthdayTxtFld;
	private JPasswordField iPswdFld1;
	private JPasswordField iPswdFld2;
	private JPanel iInfoPane;
	private JLabel iIdLabel;
	public static void main(String[] args) {
		System.out.println("Hello World!!");
		LoginViewer it=new LoginViewer();
		it.setLocation(400, 250);
		it.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		it.setVisible(true);
		it.pack();
	}
}

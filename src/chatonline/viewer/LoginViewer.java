package chatonline.viewer;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.NumberFormat;
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

/** @ClassName: LoginViewer 
 * @Description: viewer for login
 * @author Li 
 * @date 2012-4-23 下午08:33:55 
 * 
 */
public class LoginViewer extends JFrame{
	private CardLayout iCardLayout=new CardLayout(0, 0);
	public LoginViewer() {
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
		
		iPswdTxtFld1 = new JTextField();
		iPswdTxtFld1.setColumns(10);
		
		label_7 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		
		iPswdTxtFld2 = new JTextField();
		iPswdTxtFld2.setColumns(10);
		
		label_8 = new JLabel("\u5730\u5740");
		
		iAddressTxtFld = new JTextField();
		iAddressTxtFld.setColumns(10);
		
		iBirthdayTxtFld = new JTextField();
		iBirthdayTxtFld.setColumns(10);
		
		iBoyRadioBttn = new JRadioButton("\u7537");
		buttonGroup.add(iBoyRadioBttn);
		
		iGirlRadioBttn = new JRadioButton("\u5973");
		buttonGroup.add(iGirlRadioBttn);
		
		JButton iRegisterBttn = new JButton("\u6CE8\u518C");
		
		JButton iToLoginBttn = new JButton("\u767B\u5F55");
		iToLoginBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iCardLayout.next(LoginViewer.this.getContentPane());
			}
		});
		
		iLoginPane = new JPanel();
		getContentPane().add(iLoginPane, "name_29273997257395");
		getContentPane().add(iRegisterPane, "name_29260218592308");
		GroupLayout gl_iRegisterPane = new GroupLayout(iRegisterPane);
		gl_iRegisterPane.setHorizontalGroup(
			gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(240)
					.addComponent(iToLoginBttn)
					.addGap(6)
					.addComponent(iRegisterBttn))
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addComponent(label_6)
							.addGap(24)
							.addComponent(iPswdTxtFld1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(label_7)
							.addGap(16)
							.addComponent(iPswdTxtFld2))
						.addGroup(Alignment.LEADING, gl_iRegisterPane.createSequentialGroup()
							.addComponent(label_8)
							.addGap(24)
							.addComponent(iAddressTxtFld, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE))))
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addComponent(label_2)
							.addGap(24)
							.addComponent(iNameTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(label_4)
							.addGap(19)
							.addComponent(iSignatureTxtFld))
						.addGroup(Alignment.LEADING, gl_iRegisterPane.createSequentialGroup()
							.addComponent(label_3)
							.addGap(24)
							.addComponent(iBirthdayTxtFld, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(label_5)
							.addGap(35)
							.addComponent(iBoyRadioBttn)
							.addGap(34)
							.addComponent(iGirlRadioBttn))))
		);
		gl_iRegisterPane.setVerticalGroup(
			gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_iRegisterPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(3)
							.addComponent(label_2))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(3)
							.addComponent(iNameTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(6)
							.addComponent(label_4))
						.addComponent(iSignatureTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(4)
							.addComponent(label_3))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(4)
							.addComponent(iBirthdayTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(7)
							.addComponent(label_5))
						.addComponent(iBoyRadioBttn)
						.addComponent(iGirlRadioBttn))
					.addGap(7)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(3)
							.addComponent(label_6))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(3)
							.addComponent(iPswdTxtFld1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(6)
							.addComponent(label_7))
						.addComponent(iPswdTxtFld2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label_8)
						.addGroup(gl_iRegisterPane.createSequentialGroup()
							.addGap(2)
							.addComponent(iAddressTxtFld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(38)
					.addGroup(gl_iRegisterPane.createParallelGroup(Alignment.LEADING)
						.addComponent(iToLoginBttn)
						.addComponent(iRegisterBttn)))
		);
		iRegisterPane.setLayout(gl_iRegisterPane);
		JLabel label = new JLabel("\u5E10\u53F7\uFF1A");
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		
		iPswdTxtFld0 = new JPasswordField();
		
		JButton iLoginBttn = new JButton("\u767B\u5F55");
		iLoginBttn.addActionListener(new ActionListener() {
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
		
		JButton iToRegisterBttn = new JButton("\u6CE8\u518C");
		iToRegisterBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iCardLayout.next(LoginViewer.this.getContentPane());
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
					.addComponent(iToRegisterBttn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(iLoginBttn)
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
						.addComponent(iToRegisterBttn)
						.addComponent(iLoginBttn))
					.addContainerGap(84, Short.MAX_VALUE))
		);
		iLoginPane.setLayout(gl_iLoginPane);
	}
	
	private ComModuleClt iCom;
	private WorkClt iWork;
	
	private static final long serialVersionUID = 1L;
	private JPanel iRegisterPane;
	private JPanel iLoginPane;
	private JPasswordField iPswdTxtFld0;
	private JTextField iNameTxtFld;
	private JTextField iSignatureTxtFld;
	private JLabel label_6;
	private JTextField iPswdTxtFld1;
	private JLabel label_7;
	private JTextField iPswdTxtFld2;
	private JLabel label_8;
	private JTextField iAddressTxtFld;
	private JTextField iBirthdayTxtFld;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton iBoyRadioBttn;
	private JRadioButton iGirlRadioBttn;
	private JRadioButton iHideRadioBttn;
	private JFormattedTextField iIdTxtFld;
	public static void main(String[] args) {
		System.out.println("Hello World!!");
		LoginViewer it=new LoginViewer();
		it.setLocation(400, 250);
		it.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		it.setVisible(true);
		it.pack();
	}
}

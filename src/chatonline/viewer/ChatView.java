package chatonline.viewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chatonline.controller.WorkClt;
import chatonline.utility.Info;
import chatonline.utility.User;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** @ClassName: ChatView 
 * @Description: 
 * @author Li 
 * @date 2012-5-3 上午11:20:19 
 * 
 */
public class ChatView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatView frame = new ChatView(new User(1,"lihailin","rr","isme",User.ONLINE),null);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatView(User auser,WorkClt awork) {
		iTo=auser;
		iwork=awork;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(180);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		iShowTxtArea = new JTextArea();
		iShowTxtArea.setEditable(false);
		iShowTxtArea.setAutoscrolls(true);
		splitPane.setLeftComponent(iShowTxtArea);
		
		iInfoTxtArea = new JTextArea();
		iInfoTxtArea.setAutoscrolls(true);
		splitPane.setRightComponent(iInfoTxtArea);
		
		JButton button = new JButton("\u53D1\u9001");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String binfo=iInfoTxtArea.getText();
				iInfoTxtArea.setText("");
				iShowTxtArea.append(String.format("我说(%s)：\n%s\n",idf.format(new Date()),binfo));
				iwork.sendInfo(iTo.iId, binfo);
			}
		});
		contentPane.add(button, BorderLayout.EAST);
	}
	private WorkClt iwork;
	private User iTo;
	private JTextArea iShowTxtArea;
	private JTextArea iInfoTxtArea;
	private DateFormat idf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public void sendInfo(Info ainfo){
		iShowTxtArea.append(String.format("%s 说(%s)：\n%s\n", iTo.iRemark
				,idf.format(ainfo.iDate),ainfo.iInfo));
	}
}

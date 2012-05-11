package chatonline.viewer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.ListCellRenderer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import chatonline.controller.WorkClt;
import chatonline.utility.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @ClassName: FindFrdView
 * @Description:
 * @author Li
 * @date 2012-5-3 下午11:24:53
 * 
 */
public class FindFrdView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList iUserList;
	private JTextField iNameTxtFld;
	private JTextField iAddressTxtFld;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindFrdView frame = new FindFrdView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private WorkClt iwork;
	private JFormattedTextField iIdTxtFld;
	private JRadioButton iBoyRadioBttn;
	private JRadioButton iGirlRadioBttn;

	public FindFrdView(WorkClt awork) {
		this();
		iwork = awork;
	}

	/**
	 * Create the frame.
	 */
	public FindFrdView() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(110);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		iUserList = new JList();
		iUserList.addMouseListener(new MouseAdapter() {
			private JPopupMenu imenu;
			{
				imenu=new JPopupMenu();
				
				JMenuItem bitem=new JMenuItem("加为好友");
				bitem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						User buser=(User)iUserList.getSelectedValue();
						String bmsg=JOptionPane.showInputDialog(iUserList, "输入验证消息:", "无");
						if(bmsg==null||bmsg.equals(""))
							bmsg="none";
						iwork.addFrd(buser.iId, bmsg);
					}
				});
				imenu.add(bitem);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()){
					showPopupMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()){
					showPopupMenu(e);
				}
			}
			private void showPopupMenu(MouseEvent e){
				int bpos=iUserList.locationToIndex(e.getPoint());
				if(bpos!=-1){
					iUserList.setSelectedIndex(bpos);
					imenu.show(iUserList, e.getX(), e.getY());
				}
			}
		});
		
		iUserList.setModel(new DefaultListModel());// 默认的list model不是
													// DefaultListModel
		iUserList.setCellRenderer(new CellRenderer());
		JScrollPane bscrollpane=new JScrollPane(iUserList);
		splitPane.setRightComponent(bscrollpane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("\u6309\u7167\u5E10\u53F7\u67E5\u627E", null, panel,
				null);

		NumberFormat bNumberFormat = NumberFormat.getInstance();
		bNumberFormat.setGroupingUsed(false);
		bNumberFormat.setParseIntegerOnly(true);
		iIdTxtFld = new JFormattedTextField(bNumberFormat);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long bid = (Long) iIdTxtFld.getValue();
				List<User> blist = new LinkedList<User>();
				User buser = new User();
				buser.iId = (int) bid;
				iwork.findFrd(blist, buser);
				DefaultListModel bmodel = (DefaultListModel) iUserList
						.getModel();
				bmodel.clear();
				for (User bi : blist) {
					bmodel.addElement(bi);
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addGap(25)
						.addComponent(iIdTxtFld, GroupLayout.PREFERRED_SIZE,
								210, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(button)
						.addContainerGap(83, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(25)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														iIdTxtFld,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button))
								.addContainerGap(24, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u6309\u7167\u6761\u4EF6\u67E5\u627E", null,
				panel_1, null);

		JLabel label = new JLabel("\u7F51\u540D");

		iNameTxtFld = new JTextField();
		iNameTxtFld.setColumns(10);

		JLabel label_1 = new JLabel("\u5730\u5740");

		iAddressTxtFld = new JTextField();
		iAddressTxtFld.setColumns(10);

		iBoyRadioBttn = new JRadioButton("\u7537");
		buttonGroup.add(iBoyRadioBttn);

		iGirlRadioBttn = new JRadioButton("\u5973");
		buttonGroup.add(iGirlRadioBttn);

		JButton button_1 = new JButton("\u786E\u5B9A");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<User> blist = new LinkedList<User>();
				String bname = iNameTxtFld.getText();
				String baddress = iAddressTxtFld.getText();

				User buser = new User();
				if (bname != null && !bname.equals("")) {
					buser.iName = bname;
				}
				if (baddress != null && !baddress.equals("")) {
					buser.iAddress = baddress;
				}
				if (iBoyRadioBttn.isSelected()) {
					buser.iSex = User.BOY;
				}
				if (iGirlRadioBttn.isSelected()) {
					buser.iSex = User.Girl;
				}
				iwork.findFrd(blist, buser);
				DefaultListModel bmodel = (DefaultListModel) iUserList
						.getModel();
				bmodel.clear();
				for (User bi : blist) {
					bmodel.addElement(bi);
				}
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				iBoyRadioBttn)
																		.addGap(18)
																		.addComponent(
																				iGirlRadioBttn)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				button_1))
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				label)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				iNameTxtFld,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				label_1)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				iAddressTxtFld,
																				GroupLayout.PREFERRED_SIZE,
																				154,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(101, Short.MAX_VALUE)));
		gl_panel_1
				.setVerticalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(label)
														.addComponent(
																iNameTxtFld,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_1)
														.addComponent(
																iAddressTxtFld,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																iBoyRadioBttn)
														.addComponent(
																iGirlRadioBttn)
														.addComponent(button_1))
										.addContainerGap(10, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
	}

	class CellRenderer extends JLabel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

		CellRenderer() {
			setOpaque(true);
		}

		/* 从这里到结束：实作getListCellRendererComponent()方法 */
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			if (value != null) {
				User buser = (User) value;
				setText(String.format("%d %s %s", buser.iId, buser.iName,
						buser.iAddress));
			}
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				// 设置选取与取消选取的前景与背景颜色.
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			return this;
		}
	}
}

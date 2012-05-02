package chatonline.viewer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import chatonline.controller.WorkClt;
import chatonline.utility.AskFrd;
import chatonline.utility.Info;
import chatonline.utility.InfoWithPhoto;
import chatonline.utility.User;

/**
 * @ClassName: FrdListView
 * @Description:
 * @author Li
 * @date 2012-4-25 下午09:50:23
 * 
 */
public class FrdListView extends JFrame {
	private WorkClt iwork;

	public FrdListView(WorkClt awork) {
		this();
		iwork = awork;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				iwork.requestClose();
			}
		});

		// insert data into tree
		System.out.println("friends:");
		DefaultMutableTreeNode broot = new DefaultMutableTreeNode("me:"+iwork.iId);
		DefaultTreeModel bmodel = new DefaultTreeModel(broot);
		itree.setModel(bmodel);

//		itree.setRootVisible(false);
		itree.setShowsRootHandles(true);
		Map<String, List<User>> bmap = new HashMap<String, List<User>>();
		iwork.getFrdList(bmap);
		List<User> blist;
		DefaultMutableTreeNode bGroupNode;
		DefaultMutableTreeNode bnode;
		for (String bgroup : bmap.keySet()) {
			blist = bmap.get(bgroup);
			bGroupNode = new DefaultMutableTreeNode(bgroup);
			for (User buser : blist) {
				bnode = new DefaultMutableTreeNode(buser);
				bnode.setAllowsChildren(false);
				bGroupNode.add(bnode);
			}
			bmodel.insertNodeInto(bGroupNode, broot, broot.getChildCount());
		}

		// get notes
		System.out.println("notes:");
		List<Info> bInfoList = new LinkedList<Info>();
		List<InfoWithPhoto> bInfoWithPhotoList = new LinkedList<InfoWithPhoto>();
		iwork.getNotes(bInfoList, bInfoWithPhotoList);
		/***
		 * 弹出一个留言板，显示留言信息
		 */
		for (InfoWithPhoto infoWithPhoto : bInfoWithPhotoList) {
			System.out.println(infoWithPhoto.toTxtLine());
		}
		for (Info info : bInfoList) {
			System.out.println(info.toString());
		}

		// get ask for friend
		System.out.println("ask for friend:");
		List<AskFrd> bask = new LinkedList<AskFrd>();
		iwork.getAskFrd(bask);
		for (AskFrd askFrd : bask) {
			System.out.println(askFrd.toLine());
		}

		// add mouse listener to tree
		itree.addMouseListener(new MouseAdapter() {
			private JPopupMenu iFrdMenu;
			private JPopupMenu iGroupMenu;
			{
				iFrdMenu = new JPopupMenu();
				JMenuItem item;

				item = new JMenuItem();
				item.setText("删除好友");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						User buser=getUserFromNode(itree.getLastSelectedPathComponent());
						iwork.delFrd(buser.iId);
					}
				});
				iFrdMenu.add(item);

				item = new JMenuItem();
				item.setText("修改备注");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						User buser=getUserFromNode(itree.getLastSelectedPathComponent());
						String bname=JOptionPane.showInputDialog(itree, "输入新备注:",buser.iRemark);
						if(bname==null||bname.equals(""))
							return ;
						iwork.renameFrd(buser.iId, bname);
						buser.iRemark=bname;
					}
				});
				iFrdMenu.add(item);

				item = new JMenuItem();
				item.setText("修改权限");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				iFrdMenu.add(item);

				item = new JMenuItem();
				item.setText("开始聊天");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub

					}
				});
				iFrdMenu.add(item);

				iGroupMenu=new JPopupMenu();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.isPopupTrigger()) {
					showPopupMenu(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.isPopupTrigger()) {
					showPopupMenu(e);
				}
			}

			private void showPopupMenu(MouseEvent e) {
				TreePath bpath = itree.getPathForLocation(e.getX(), e.getY());
				if (bpath != null) {
					itree.setSelectionPath(bpath);
					DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
							.getLastSelectedPathComponent();
					if (bnode.getAllowsChildren())
						iGroupMenu.show(itree, e.getX(), e.getY());
					else
						iFrdMenu.show(itree, e.getX(), e.getY());
				}
			}
		});
	}
	private User getUserFromNode(Object avalue){
		DefaultMutableTreeNode node=(DefaultMutableTreeNode) avalue;
		return (User) node.getUserObject();
	}
	private FrdListView() {

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		textField = new JTextField();
		textField.setColumns(10);

		JButton button_1 = new JButton("\u786E\u5B9A");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(textField, GroupLayout.PREFERRED_SIZE,
								321, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(button_1).addGap(40)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(6)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														textField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(button_1))
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		JButton button = new JButton("\u67E5\u627E");
		panel_1.add(button);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		itree = new JTree() {
			private static final long serialVersionUID = 1L;

			@Override
			public String convertValueToText(Object value, boolean selected,
					boolean expanded, boolean leaf, int row, boolean hasFocus) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) value;
				if (bnode.getAllowsChildren())
					return (String) bnode.getUserObject();
				else {
					User buser = (User) bnode.getUserObject();
					return buser.iRemark;
				}
			}
		};
		scrollPane.setViewportView(itree);
	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTree itree;

}

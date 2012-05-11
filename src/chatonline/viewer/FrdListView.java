package chatonline.viewer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
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
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import chatonline.controller.HookListenClt;
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
	private Map<Integer, ChatView> iChatMap;

	private ChatView getChatView(int aid) {
		if (!iChatMap.containsKey(aid)) {
			User buser = getUserById(aid);
			ChatView bview = new ChatView(buser, iwork);
			iChatMap.put(aid, bview);
		}
		return iChatMap.get(aid);
	}

	public WorkClt getWork() {
		return iwork;
	}

	private User getUserById(int aid) {
		DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
				.getModel().getRoot();
		Enumeration<?> benumeration = bnode.depthFirstEnumeration();
		while (benumeration.hasMoreElements()) {
			bnode = (DefaultMutableTreeNode) benumeration.nextElement();
			if (bnode.getUserObject() instanceof String)
				continue;
			User buser = (User) bnode.getUserObject();
			if (buser.iId == aid) {
				return buser;
			}
		}
		return null;
	}

	private DefaultMutableTreeNode getNodeById(int aid) {
		DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
				.getModel().getRoot();
		Enumeration<?> benumeration = bnode.depthFirstEnumeration();
		while (benumeration.hasMoreElements()) {
			bnode = (DefaultMutableTreeNode) benumeration.nextElement();
			if (bnode.getUserObject() instanceof String)
				continue;
			User buser = (User) bnode.getUserObject();
			if (buser.iId == aid) {
				return bnode;
			}
		}
		return null;
	}

	private DefaultMutableTreeNode setPosForUser(String atxt) {
		DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
				.getModel().getRoot();
		Enumeration<?> benumeration = bnode.depthFirstEnumeration();
		while (benumeration.hasMoreElements()) {
			bnode = (DefaultMutableTreeNode) benumeration.nextElement();
			if (bnode.getUserObject() instanceof String)
				continue;
			User buser = (User) bnode.getUserObject();
			if (buser.iRemark.contains(atxt) || buser.iName.contains(atxt)
					|| buser.iSignature.contains(atxt)
					|| (buser.iId + "").contains(atxt)) {
				return bnode;
			}
		}
		return null;
	}

	public FrdListView(WorkClt awork) {
		this();
		iwork = awork;
		iFindFrdView = new FindFrdView(iwork);
		iwork.setHook(new HookListenClt() {

			@Override
			public void sentInfo(Info ainfo) {
				// TODO Auto-generated method stub
				ChatView bview = getChatView(ainfo.iFromId);
				if (!bview.isVisible())
					bview.setVisible(true);
				bview.sendInfo(ainfo);
			}

			@Override
			public void getPhotos(List<InfoWithPhoto> alist) {
				// TODO Auto-generated method stub

			}

			@Override
			public void getAskFrdRespond(int aid, boolean ais) {
				System.out.println("getAskFrdRespond!!");
				if (!ais) {
					String binfo = String.format("%d 没有同意你的好友申请！", aid);
					JOptionPane.showMessageDialog(FrdListView.this, binfo,
							"提示消息", JOptionPane.PLAIN_MESSAGE);
				} else {
					String binfo = String.format("%d 已经同意你的好友申请！", aid);
					JOptionPane.showMessageDialog(FrdListView.this, binfo,
							"提示消息", JOptionPane.PLAIN_MESSAGE);

					User buser = new User();
					buser.iId = aid;
					List<User> blist = new LinkedList<User>();
					iwork.findFrd(blist, buser);

					DefaultMutableTreeNode bnode = new DefaultMutableTreeNode(
							blist.get(0));
					bnode.setAllowsChildren(false);
					DefaultTreeModel bmodel = (DefaultTreeModel) itree
							.getModel();
					DefaultMutableTreeNode bfrd = (DefaultMutableTreeNode) bmodel
							.getChild(bmodel.getRoot(), 0);
					bmodel.insertNodeInto(bnode, bfrd, bfrd.getChildCount());
				}
			}

			@Override
			public void delFrd(int aid) {
				DefaultMutableTreeNode bnode = getNodeById(aid);
				DefaultTreeModel bmodel = (DefaultTreeModel) itree.getModel();
				bmodel.removeNodeFromParent(bnode);
			}

			@Override
			public boolean askForFrd(AskFrd aask) {
				String bcontent = String.format("是否同意来自%d的好友请求？\n%s",
						aask.ifromid, aask.imsg);
				int bIsOk = JOptionPane.showConfirmDialog(FrdListView.this,
						bcontent, "提示消息", JOptionPane.YES_NO_OPTION);
				return bIsOk == JOptionPane.YES_OPTION;
			}

			@Override
			public void afterAskFrd(int aid, boolean ais) {
				if (ais) {
					User buser = new User();
					buser.iId = aid;
					List<User> blist = new LinkedList<User>();
					iwork.findFrd(blist, buser);

					DefaultMutableTreeNode bnode = new DefaultMutableTreeNode(
							blist.get(0));
					bnode.setAllowsChildren(false);
					DefaultTreeModel bmodel = (DefaultTreeModel) itree
							.getModel();
					DefaultMutableTreeNode bfrd = (DefaultMutableTreeNode) bmodel
							.getChild(bmodel.getRoot(), 0);
					bmodel.insertNodeInto(bnode, bfrd, bfrd.getChildCount());
				}
			}
		});
		iChatMap = new HashMap<Integer, ChatView>();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				iwork.requestClose();
			}
		});

		// insert data into tree
		System.out.println("friends:");
		DefaultMutableTreeNode broot = new DefaultMutableTreeNode("me:"
				+ iwork.iId);
		DefaultTreeModel bmodel = new DefaultTreeModel(broot);
		itree.setModel(bmodel);

		// itree.setRootVisible(false);
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
			if (bgroup.equals("friend"))
				bmodel.insertNodeInto(bGroupNode, broot, 0);
			else
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
						DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
								.getLastSelectedPathComponent();
						User buser = getUserFromNode(bnode);
						iwork.delFrd(buser.iId);
						DefaultTreeModel bmodel = (DefaultTreeModel) itree
								.getModel();
						bmodel.removeNodeFromParent(bnode);
					}
				});
				iFrdMenu.add(item);

				item = new JMenuItem();
				item.setText("修改备注");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						User buser = getUserFromNode(itree
								.getLastSelectedPathComponent());
						String bname = JOptionPane.showInputDialog(itree,
								"输入新备注:", buser.iRemark);
						if (bname == null || bname.equals(""))
							return;
						iwork.renameFrd(buser.iId, bname);
						buser.iRemark = bname;
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
						User buser = getUserFromNode(itree
								.getLastSelectedPathComponent());
						getChatView(buser.iId).setVisible(true);
					}
				});
				iFrdMenu.add(item);

				iGroupMenu = new JPopupMenu();

				item = new JMenuItem();
				item.setText("更改组名");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String bold = getGroupFromNode(itree
								.getLastSelectedPathComponent());
						if (bold.equals("friend")) {
							JOptionPane.showMessageDialog(itree,
									"不能修改friend组名！！", "提示消息",
									JOptionPane.WARNING_MESSAGE);
						} else {
							String bnew = JOptionPane.showInputDialog(itree,
									"输入新组名:", bold);
							if (bnew == null || bnew.equals(""))
								return;
							iwork.renameGroup(bold, bnew);
							DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
									.getLastSelectedPathComponent();
							bnode.setUserObject(bnew);
							// itree.getModel().valueForPathChanged(new
							// TreePath(bnode.getPath()), bnew);
						}
					}
				});
				iGroupMenu.add(item);

				item = new JMenuItem();
				item.setText("删除该组");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String bgroup = getGroupFromNode(itree
								.getLastSelectedPathComponent());
						if (bgroup.equals("friend")) {
							JOptionPane.showMessageDialog(itree,
									"不能删除friend组！！", "提示消息",
									JOptionPane.WARNING_MESSAGE);
						} else {
							iwork.delGroup(bgroup);

							// 该组成员全部转移到friend组中
							DefaultTreeModel bmodel = (DefaultTreeModel) itree
									.getModel();
							DefaultMutableTreeNode bfrd = (DefaultMutableTreeNode) bmodel
									.getChild(bmodel.getRoot(), 0);
							DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) itree
									.getLastSelectedPathComponent();
							for (int i = 0; i < bnode.getChildCount(); i++) {
								bmodel.insertNodeInto(
										(MutableTreeNode) bnode.getChildAt(i),
										bfrd, bfrd.getChildCount());
							}
							bmodel.removeNodeFromParent(bnode);
						}
					}
				});
				iGroupMenu.add(item);

				item = new JMenuItem();
				item.setText("新建组");
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String bgroup = JOptionPane.showInputDialog(itree,
								"输入新组名", "none");
						if (bgroup != null && !bgroup.equals("")) {
							iwork.addGroup(bgroup);
							DefaultTreeModel bmodel = (DefaultTreeModel) itree
									.getModel();
							DefaultMutableTreeNode bnode = new DefaultMutableTreeNode(
									bgroup);

							bmodel.insertNodeInto(bnode,
									(DefaultMutableTreeNode) bmodel.getRoot(),
									1);
						}
					}
				});
				iGroupMenu.add(item);
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
					if (bnode.isRoot())
						return;
					if (bnode.getAllowsChildren())
						iGroupMenu.show(itree, e.getX(), e.getY());
					else
						iFrdMenu.show(itree, e.getX(), e.getY());
				}
			}
		});

		iwork.startupListener();
	}

	private User getUserFromNode(Object avalue) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) avalue;
		return (User) node.getUserObject();
	}

	private String getGroupFromNode(Object avalue) {
		DefaultMutableTreeNode bnode = (DefaultMutableTreeNode) avalue;
		return (String) bnode.getUserObject();
	}

	private FrdListView() {

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		iSetPosTxtFld = new JTextField();
		iSetPosTxtFld.setColumns(10);

		JButton button_1 = new JButton("\u786E\u5B9A");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String btxt = iSetPosTxtFld.getText();
				if (btxt != null && !btxt.equals("")) {
					DefaultMutableTreeNode bnode = setPosForUser(btxt);
					if (bnode != null) {
						TreePath bpath = new TreePath(bnode.getPath());
						itree.setSelectionPath(bpath);
						itree.scrollPathToVisible(bpath);
					} else {
						JOptionPane.showMessageDialog(itree, "没有找到相应的好友",
								"提示消息", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(iSetPosTxtFld,
								GroupLayout.PREFERRED_SIZE, 321,
								GroupLayout.PREFERRED_SIZE)
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
														iSetPosTxtFld,
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
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iFindFrdView.setLocationRelativeTo(null);
				iFindFrdView.setVisible(true);
			}
		});
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
					if (buser.iRemark.equals("none"))
						return buser.iName;
					else
						return buser.iRemark;
				}
			}
		};
		// itree.setRootVisible(false);
		scrollPane.setViewportView(itree);
	}

	private static final long serialVersionUID = 1L;
	private JTextField iSetPosTxtFld;
	private JTree itree;
	private FindFrdView iFindFrdView;
}

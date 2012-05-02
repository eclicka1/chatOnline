package chatonline.viewer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import chatonline.controller.WorkClt;
import chatonline.utility.User;

/** @ClassName: FrdListView 
 * @Description: 
 * @author Li 
 * @date 2012-4-25 ÏÂÎç09:50:23 
 * 
 */
public class FrdListView extends JFrame {
	private WorkClt iwork;
	public FrdListView(WorkClt awork){
		this();
		iwork=awork;
		
		//insert data into tree
		Map<String,List<User>> bmap=new HashMap<String, List<User>>();
		iwork.getFrdList(bmap);
		List<User> blist;
		for(String bgroup: bmap.keySet()){
			blist=bmap.get(bgroup);
			
		}
		
		//
	}
	private FrdListView() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton button_1 = new JButton("\u786E\u5B9A");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_1)
					.addGap(40))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton button = new JButton("\u67E5\u627E");
		panel_1.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
	}
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
}

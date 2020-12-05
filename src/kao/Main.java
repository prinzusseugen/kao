package kao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import kao.Get;
public class Main extends JFrame {
	private Register register = null;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		try {
			   UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		setTitle("\uB85C\uADF8\uC778");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 185);
		getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(82, 66, 209, 27);
		getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(82, 26, 209, 27);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\uB85C\uADF8\uC778");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String users = null;
				try {
					users = Get.get(Config.ServerURL+"kkaousers?userID="+textField.getText(), null, "utf-8");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					JSONArray jsonArr = (JSONArray) jsonParser.parse(users);
					if (jsonArr.size() == 0) {
						JOptionPane.showMessageDialog(null , "로그인 실패(ID나 패스워드가 맞지 않습니다)");
					}
					else {
						JSONObject user = (JSONObject) jsonArr.get(0);
						if(user.get("userPW").equals(String.valueOf(passwordField.getPassword()))) {
							JOptionPane.showMessageDialog(null , "로그인 성공");
							Main.this.dispose();
							Menu menu = new Menu((long)user.get("id"), (String)user.get("userID"));
							menu.setVisible(true);
							
						}
						else
							JOptionPane.showMessageDialog(null , "로그인 실패(ID나 패스워드가 맞지 않습니다)");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(303, 26, 84, 52);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(register==null || register.isShowing()==false) {
					register = new Register();
					register.setVisible(true);
				}				
				
			}
		});
		btnNewButton_1.setBounds(82, 103, 97, 33);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(44, 33, 26, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setBounds(46, 72, 57, 15);
		getContentPane().add(lblNewLabel_1);
	}
}

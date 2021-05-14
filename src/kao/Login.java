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
import javax.swing.ImageIcon;
public class Login extends JFrame {
	private Register register = null;
	private PasswordFind pwfind = null;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			Login login = new Login();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("\uB85C\uADF8\uC778");
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 447);
		getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(96, 297, 209, 27);
		getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(96, 258, 209, 27);
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
							Login.this.dispose();
							Menu menu = new Menu((long)user.get("id"), (String)user.get("nickname"));
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
		btnNewButton.setBounds(317, 272, 84, 52);
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
		btnNewButton_1.setBounds(78, 336, 97, 33);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(53, 265, 26, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setBounds(53, 304, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_2 = new JButton("\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pwfind==null || pwfind.isShowing()==false) {
					pwfind = new PasswordFind();
					pwfind.setVisible(true);
				}	
				
			}
		});
		
		btnNewButton_2.setBounds(205, 336, 118, 33);
		getContentPane().add(btnNewButton_2);
	}
}

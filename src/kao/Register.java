package kao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import kao.Post;
public class Register extends JFrame {
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			Register regi = new Register();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setTitle("\uD68C\uC6D0\uAC00\uC785");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 449);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("\uD68C\uC6D0\uAC00\uC785 \uC644\uB8CC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.getText();
				String nickname = String.valueOf(textField_1.getText());
				String myPass=String.valueOf(passwordField.getPassword());
				String myPass1=String.valueOf(passwordField_1.getPassword());
				if(myPass.length()<4) {
					JOptionPane.showMessageDialog(null , "비밀번호 길이가 짧습니다");
				}
				else if(myPass.contentEquals(myPass1)&&nickname.length()>0) {
					String user = "{\"userID\":\""+textField.getText()+"\",\"userPW\":\""+myPass+"\",\"nickname\":\""+nickname+"\"}";
			    	System.out.println(user);
			    	try {
						String body = Post.post(Config.ServerURL+"kkaousers", user, "json");
						System.out.println(body);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null , "회원가입 성공");
					Register.this.dispose();
				}
				else if(nickname.length()<=0) {
					JOptionPane.showMessageDialog(null , "닉네임을 입력해주세요");
				}
				else
					JOptionPane.showMessageDialog(null , "회원가입 실패(비밀번호가 다릅니다)");
			}
		});
		btnNewButton.setBounds(185, 350, 198, 50);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(123, 121, 368, 27);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 187, 368, 27);
		getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(123, 250, 368, 27);
		getContentPane().add(passwordField_1);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(51, 127, 30, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setBounds(51, 193, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PW \uD655\uC778");
		lblNewLabel_2.setBounds(51, 256, 46, 15);
		getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(123, 62, 368, 27);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\uB2C9\uB124\uC784");
		lblNewLabel_3.setBounds(51, 68, 57, 15);
		getContentPane().add(lblNewLabel_3);
	}

}

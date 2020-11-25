package kao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import kao.Post;
public class Register extends JFrame {
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 449);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("\uD68C\uC6D0\uAC00\uC785 \uC644\uB8CC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.getText();
				String myPass=String.valueOf(passwordField.getPassword());
				String myPass1=String.valueOf(passwordField_1.getPassword());
				if(myPass.length()<4) {
					JOptionPane.showMessageDialog(null , "비밀번호 길이가 짧습니다");
				}
				else if(myPass.contentEquals(myPass1)) {
					String user = "{\"userID\":\""+textField.getText()+"\",\"userPW\":\""+myPass+"\"}";
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
				else
					JOptionPane.showMessageDialog(null , "회원가입 실패(비밀번호가 다릅니다)");
			}
		});
		btnNewButton.setBounds(185, 350, 198, 50);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(123, 40, 368, 27);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 102, 368, 27);
		getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(123, 165, 368, 27);
		getContentPane().add(passwordField_1);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(65, 46, 30, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setBounds(65, 108, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PW \uD655\uC778");
		lblNewLabel_2.setBounds(65, 171, 46, 15);
		getContentPane().add(lblNewLabel_2);
	}

}

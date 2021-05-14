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

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PasswordFind extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			PasswordFind pass = new PasswordFind();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordFind frame = new PasswordFind();
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
	public PasswordFind() {
		setTitle("\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		setBounds(100, 100, 433, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(96, 23, 266, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\uC544\uC774\uB514 \uC785\uB825");
		lblNewLabel.setBounds(12, 25, 72, 21);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("\uD655\uC778");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String users = null;
				try {
					users = Get.get(Config.ServerURL+"kkaousers?userID="+textField.getText(),null , "utf-8");
				} catch(IOException e1) {
					e1.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					JSONArray jsonArr = (JSONArray) jsonParser.parse(users);
					if (jsonArr.size() == 0) {
						JOptionPane.showMessageDialog(null , "아이디를 찾을 수 없습니다");
					}
					else {
						JSONObject user = (JSONObject) jsonArr.get(0);
						if(user.get("userID").equals(String.valueOf(textField.getText()))&&user.get("nickname").equals(String.valueOf(textField_1.getText()))) {
							JOptionPane.showMessageDialog(null , "당신의 비밀번호는 "+user.get("userPW")+"입니다");
							PasswordFind.this.dispose();							
						}
						else
							JOptionPane.showMessageDialog(null , "아이디를 찾을 수 없습니다");
					}
				} catch(ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(147, 123, 132, 26);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("\uB2C9\uB124\uC784 \uC785\uB825");
		lblNewLabel_1.setBounds(12, 78, 72, 15);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(96, 73, 266, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}

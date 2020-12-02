package kao;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;

public class ChatRoom extends JFrame {

	private JPanel contentPane;
	private int userID = 1; // 임시
	private int chatroomID = 1; // 임시
	private long lastupdatedmessage = 0; // 임시
	private SimpleAttributeSet attribute;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom frame = new ChatRoom();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void sendMessage(String message) {
		try {
			Post.post(Config.ServerURL+"messages", "{\"chatcontent\":\""+message+"\",\r\n\"kkaouser\":"+userID+",\r\n\"chatroom\":"+chatroomID+"\r\n}", "json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public ChatRoom() {
		setTitle("\uCC44\uD305\uBC29");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 682, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("\uBCF4\uB0B4\uAE30");
		btnNewButton.setBounds(513, 633, 141, 44);
		contentPane.add(btnNewButton);
		
		JTextField textArea = new JTextField();
		textArea.setBounds(12, 627, 499, 50);
		contentPane.add(textArea);
		
		


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 642, 606);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar()=='\n'&&textArea.getText().length()!=0) {
						sendMessage(textArea.getText());
						textArea.setText("");	
				}
			}
		});
		textArea.setFocusable(true);
		textArea.requestFocus();
		textPane.setBackground(new Color(255, 255, 204));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// textPane.setText(textPane.getText()+textArea.getText()+"\n");
				if(textArea.getText().length()!=0) {
					sendMessage(textArea.getText());
					textArea.setText("");
				}
			}
		});
		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String body = Get.get(Config.ServerURL+"messages?id_gt="+lastupdatedmessage+"&chatroom="+chatroomID, null, "utf-8");
				try {
					JSONParser jsonParser = new JSONParser();
					JSONArray jsonArr = (JSONArray) jsonParser.parse(body);
					for(int i=0; i<jsonArr.size();i++) {
						JSONObject chatmessage = (JSONObject) jsonArr.get(i);
						long id = (long) chatmessage.get("id");
						String message = (String) chatmessage.get("chatcontent");
						try {
							message = new String(message.getBytes(),"UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JSONObject user = (JSONObject) chatmessage.get("kkaouser");
						String username = (String) user.get("userID");
						long messageuserid = (long) user.get("id");
						textPane.setText(textPane.getText()+username+":"+message+"\n");
						lastupdatedmessage = id;
						
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		timer.start();
	}
}

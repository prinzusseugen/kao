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
import javax.swing.UIManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ChatRoom extends JFrame {

	private JPanel contentPane;
	private long userID; // 임시
	private long chatroomID ; // 임시
	private long lastupdatedmessage = 0; // 임시
	private SimpleAttributeSet attribute;
	private Timer timer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			ChatRoom chat = new ChatRoom();
		}*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatRoom frame = new ChatRoom(1,1);
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
	public ChatRoom(long iD, long l) {
		this.userID = iD;
		this.chatroomID = l;
		
		setTitle("\uCC44\uD305\uBC29");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		textPane.setContentType("text/html");
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
		timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String body = null;
				try {
					body = Get.get(Config.ServerURL+"messages?id_gt="+lastupdatedmessage+"&chatroom="+l, null, "utf-8");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					JSONArray jsonArr = (JSONArray) jsonParser.parse(body);
					for(int i=0; i<jsonArr.size();i++) {
						JSONObject chatmessage = (JSONObject) jsonArr.get(i);
						long id = (long) chatmessage.get("id");
						String message = (String) chatmessage.get("chatcontent");
						JSONObject user = (JSONObject) chatmessage.get("kkaouser");
						String username = (String) user.get("nickname");
						
						long messageuserid = (long) user.get("id");
						StyledDocument doc = textPane.getStyledDocument();
                        SimpleAttributeSet attrset = new SimpleAttributeSet();
                        boolean isMe = messageuserid == iD;
                        if(isMe) {
                            StyleConstants.setAlignment(attrset, StyleConstants.ALIGN_RIGHT);
                        }

                        try {
                            if(isMe) {
                                doc.insertString(doc.getLength(),username+"\n"+message+"\n", attrset);
                            }
                            else {
                                doc.insertString(doc.getLength(), username+"\n"+message+"\n", attrset);
                            }
                        } catch (BadLocationException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
						lastupdatedmessage = id;
						
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		timer.start();
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				timer.stop();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/*public ChatRoom() {
		// TODO Auto-generated constructor stub
	}*/
}

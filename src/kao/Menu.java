package kao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class Menu extends JFrame {

	private JPanel contentPane;
	private long ID;
	private JTextField textField;
	private JTextField textField_1;
	private ChatRoom chatroom;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			Menu menu = new Menu();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu(1, "prinzeugen");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	void OpenChatRoom(String userName, String otherUser) {
		System.out.println("\n"+userName+otherUser+"\n");
		String filter;
		String roomName = new String();
		if(otherUser.compareTo(userName)<=0) {
			roomName = otherUser+userName;
		}
		else
			roomName = userName+otherUser;
		try {
			filter = Get.get(Config.ServerURL+"chatrooms?name="+roomName, null, "utf-8");
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArr = (JSONArray) jsonParser.parse(filter);
			if(jsonArr.size()>0) {
			JSONObject Room = (JSONObject)jsonArr.get(0);
			chatroom = new ChatRoom(ID, (long)Room.get("id"));
			chatroom.setVisible(true);
			}
			else {
				String room = "{\"name\": \""+roomName+"\"}";
				String chatRoom = Post.post(Config.ServerURL+"chatrooms",room, "json");
				filter = Get.get(Config.ServerURL+"chatrooms?name="+roomName, null, "utf-8");
    			JSONParser jsonParser1 = new JSONParser();
    			JSONArray jsonArr1 = (JSONArray) jsonParser1.parse(filter);
    			
    			JSONObject Room = (JSONObject)jsonArr1.get(0);
    			chatroom = new ChatRoom(ID, (long)Room.get("id"));
    			chatroom.setVisible(true);
			}
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*catch(FileNotFoundException F) {
			String room = "{\"name\": \""+roomName+"\"}";
			try {
				String chatRoom = Post.post(Config.ServerURL+"chatrooms",room, "json");
				filter = Get.get(Config.ServerURL+"chatrooms?name="+roomName, null, "utf-8");
    			JSONParser jsonParser = new JSONParser();
    			JSONArray jsonArr = (JSONArray) jsonParser.parse(filter);
    			
    			JSONObject Room = (JSONObject)jsonArr.get(0);
    			chatroom = new ChatRoom(ID, (long)Room.get("id"));
    			chatroom.setVisible(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the frame.
	 */
	public Menu(long ID, String userName) {		
		setTitle("\uCE5C\uAD6C \uB9AC\uC2A4\uD2B8");
		this.ID = ID;
		System.out.println(ID);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 611);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 97, 422, 465);
		contentPane.add(scrollPane);
		
		Vector userList = new Vector();
		String users = null;
		
		
		
		try {
			users = Get.get(Config.ServerURL+"kkaousers", null, "utf-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArr = (JSONArray) jsonParser.parse(users);
			for(int i=0; i<jsonArr.size();i++) {
				JSONObject user = (JSONObject) jsonArr.get(i);
				if(userName.equals((String)user.get("nickname")))
					continue;
				else
					userList.add((String)user.get("nickname"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JList list = new JList(userList);
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	if(evt.getClickCount() == 2) {
		    		String otherUser = (String) list.getSelectedValue();
		    		OpenChatRoom(userName, otherUser);
		    	}
		    }

			private void chatroom() {
				// TODO Auto-generated method stub
				
			}
		    });
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("\uCC44\uD305\uC2DC\uC791");
		btnNewButton.setBounds(345, 15, 89, 32);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String otherUser1 = null;
				try {
					otherUser1 = Get.get(Config.ServerURL+"kkaousers?nickname="+textField_1.getText(), null, "utf-8");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					JSONArray jsonArr = (JSONArray) jsonParser.parse(otherUser1);
					if(jsonArr.size()==0) {
						JOptionPane.showMessageDialog(null, "찾는 유저가 없습니다.");
					}
					else {
						OpenChatRoom(userName, textField_1.getText());
					}
						
						
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
					
				
			}
		});
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("\uB2C9\uB124\uC784");
		lblNewLabel.setBounds(12, 10, 68, 43);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(80, 13, 110, 38);
		textField.setFont(new Font("굴림", Font.PLAIN, 14));
		textField.setBackground(SystemColor.control);
		textField.setText(userName);
		textField.setBorder(null);
		
		textField.setEditable(false);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(190, 21, 133, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String users = null;
				Vector userList = new Vector();
				try {
					users = Get.get(Config.ServerURL+"kkaousers", null, "utf-8");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					JSONArray jsonArr = (JSONArray) jsonParser.parse(users);
					for(int i=0; i<jsonArr.size();i++) {
						JSONObject user = (JSONObject) jsonArr.get(i);
						if(userName.equals((String)user.get("nickname")))
							continue;
						else
							userList.add((String)user.get("nickname"));
							JList list = new JList(userList);
							scrollPane.setViewportView(list);
							list.addMouseListener(new MouseAdapter() {
							    public void mouseClicked(MouseEvent evt) {
							    	if(evt.getClickCount() == 2) {
							    		String otherUser = (String) list.getSelectedValue();
							    		OpenChatRoom(userName, otherUser);
							    	}
							    }

								private void chatroom() {
									// TODO Auto-generated method stub
									
								}
							    });
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(167, 61, 97, 23);
		contentPane.add(btnNewButton_1);
	}

	public Menu() {
		// TODO Auto-generated constructor stub
	}
}

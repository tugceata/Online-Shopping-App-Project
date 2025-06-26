import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;

public class RegisterPage extends JFrame {
	
	private JLabel nameLbl;
	private JLabel surnameLbl;
	private JLabel userNameLbl;
	private JLabel passwordLbl;
	private JLabel ePostaLbl;
	private JTextField nameTf;
	private JTextField surnameTf;
	private JTextField userNameTf;
	private JTextField ePostaTf;
	private JTextField passwordTf;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JButton registerBtn;
	private JButton backToLoginBtn;
	private GridLayout frameGl;
	private GridLayout topPanelGl;
	private FlowLayout bottomPanelFl;
	private LoginPage loginpage;
	
	public RegisterPage(LoginPage lp) {
		
		loginpage = lp;
		
		Container cp = getContentPane();
		
		BackgroundColor backgroundColor = new BackgroundColor();
		
		frameGl = new GridLayout(2, 1, 3, 3);
		cp.setLayout(frameGl);
		
		topPanel = new JPanel();
		topPanel.setBackground(backgroundColor.getBackgroundColor());
		topPanelGl = new GridLayout(5,2,3,3);
		topPanel.setLayout(topPanelGl);
		
		nameLbl = new JLabel("İsim:");
		nameLbl.setForeground(Color.WHITE);  
		topPanel.add(nameLbl);
		nameTf = new JTextField();
		topPanel.add(nameTf);
		
		surnameLbl = new JLabel("Soyisim:");
		surnameLbl.setForeground(Color.WHITE);  
		topPanel.add(surnameLbl);
		surnameTf = new JTextField();
		topPanel.add(surnameTf);
		
		ePostaLbl = new JLabel("E-Posta:");
		ePostaLbl.setForeground(Color.WHITE);  
		topPanel.add(ePostaLbl);
		ePostaTf = new JTextField();
		topPanel.add(ePostaTf);
		
		userNameLbl = new JLabel("Kullanıcı Adı:");
		userNameLbl.setForeground(Color.WHITE);  
		topPanel.add(userNameLbl);
		userNameTf = new JTextField();
		topPanel.add(userNameTf);
		
		passwordLbl = new JLabel("Şifre:");
		passwordLbl.setForeground(Color.WHITE);  
		topPanel.add(passwordLbl);
		passwordTf = new JTextField();
		topPanel.add(passwordTf);
		
		cp.add(topPanel);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(backgroundColor.getBackgroundColor());
		bottomPanelFl = new FlowLayout(FlowLayout.CENTER);
		bottomPanel.setLayout(bottomPanelFl);
		
		registerBtn = new JButton("Kayıt Ol");
		btnRegisterActionListener register = new btnRegisterActionListener();
		registerBtn.addActionListener(register);
		bottomPanel.add(registerBtn);
		
		backToLoginBtn = new JButton("Geri Dön");
		btnRegisterActionListener back = new btnRegisterActionListener();
		backToLoginBtn.addActionListener(back);
		bottomPanel.add(backToLoginBtn);
		
		cp.add(bottomPanel, BorderLayout.SOUTH); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("Kayıt Olma Sayfası"); 
	    setSize(600, 400);       
	    setLocationRelativeTo(null);
	    setVisible(true); 
		
		 }
		
       private class btnRegisterActionListener implements ActionListener {
	    	@Override
	    	public void actionPerformed(ActionEvent evt) {
	    		if(evt.getSource() == registerBtn) {
				FileReader fr=null;
				BufferedReader br = null;
				boolean found = false;
				int tempID = 0;
				try {
					fr = new FileReader("src/kullanıcılar.txt"); 
					br = new BufferedReader(fr);
					String line;
					String[] strArray;
					while((line = br.readLine()) != null) {
						strArray = line.split(",");
						tempID = Integer.parseInt(strArray[0]);
						if((strArray[4].equals(userNameTf.getText().trim()))) { 
							found = true;
							 JOptionPane.showMessageDialog(null, "Bu kullanıcı adı alınmış","Hata", JOptionPane.ERROR_MESSAGE);
							System.out.println("Bu kullanıcı adı alınmış.");
							break;
						}
					}
			} catch (IOException e) {
				System.out.println("Dosya okuma hatası oluştu.");
			} finally {
				if ( fr != null) {
					try {
						fr.close();
					}
					catch (IOException e) {
						System.out.println("Dosya kapatma hatası oluştu.");
					}
				}
			}
				if (found == false) {
					FileWriter filewriter = null;
					try {
						tempID++;
						String temp = (tempID + "," + nameTf.getText().trim() + "," + surnameTf.getText().trim() + ","
								+ ePostaTf.getText().trim() + "," + userNameTf.getText().trim() + "," + 
								passwordTf.getText().trim() + "\r\n");          //ID-Ad-Soyad-Eposta-KullanıcıAdı-Şifre
						filewriter = new FileWriter("src/kullanıcılar.txt", true); //kullanıcılar.txt dosyasına yazılır
		            	    filewriter.write(temp); 
		                 JOptionPane.showMessageDialog(null, "Kayıt olma işlemi başarılı.");
		 
		                 LoginUser.setUser(nameTf.getText().trim(), surnameTf.getText().trim(), 
		                		 userNameTf.getText().trim(), ePostaTf.getText().trim(), passwordTf.getText().trim());		                 
		                 dispose(); 
		                 loginpage.setLocationRelativeTo(null);
		         	     loginpage.setVisible(true); 
					}
					catch (IOException e ){
						System.out.println("Kayıt olma işlemi başarısız.");
					} finally {
						if (filewriter != null) {
							try {
								filewriter.close();
							}
							catch(IOException e) {
								System.out.println("Dosya kapatma hatası oluştu.");
							}
						}
					}
				}
					} else if(evt.getSource()== backToLoginBtn) {
						loginpage.setVisible(true);
						dispose();
					}
				}
		public static void main(String[] args) {
   		SwingUtilities.invokeLater(new Runnable() {
   			public void run() {
   				new RegisterPage(new LoginPage());
   			}
   		});
	}
}
}



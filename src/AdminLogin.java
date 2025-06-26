import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminLogin extends JFrame {
	
	private JLabel adminUserNameLbl;
	private JLabel adminPasswordLbl;
	private JTextField adminUserNameTf;
	private JPasswordField adminPasswordPf;
	private JButton adminLoginBtn;
	private JButton backToLoginBtn;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private GridLayout frameGl;
	private GridLayout topPanelGl;
	private FlowLayout bottomPanelFl;
	
	public AdminLogin() {
		
		Container cp = getContentPane();
		
		BackgroundColor backgroundColor = new BackgroundColor();
		
		frameGl = new GridLayout(2,1,3,3);
		cp.setLayout(frameGl);
		
		topPanel = new JPanel();
		topPanel.setBackground(backgroundColor.getBackgroundColor());
		topPanelGl = new GridLayout(2,2,6,6);
		topPanel.setLayout(topPanelGl);
		
		adminUserNameLbl = new JLabel("Yönetici Kullanıcı Adı:");
		adminUserNameLbl.setForeground(Color.WHITE);  
		topPanel.add(adminUserNameLbl);
		adminUserNameTf = new JTextField();
		topPanel.add(adminUserNameTf);
		
		adminPasswordLbl = new JLabel("Yönetici Şifresi:");
		adminPasswordLbl.setForeground(Color.WHITE);  
		topPanel.add(adminPasswordLbl);
		adminPasswordPf = new JPasswordField();
		adminPasswordPf.setEchoChar('*');
		topPanel.add(adminPasswordPf);
		
		cp.add(topPanel);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(backgroundColor.getBackgroundColor());
		bottomPanelFl = new FlowLayout(FlowLayout.CENTER,3,3);
		bottomPanel.setLayout(bottomPanelFl); 
		
		adminLoginBtn = new JButton("Giriş");
		btnAdminLoginActionListener adminlgn = new btnAdminLoginActionListener();
		adminLoginBtn.addActionListener(adminlgn);
		bottomPanel.add(adminLoginBtn);
		
		backToLoginBtn = new JButton("Geri dön");
		btnAdminLoginActionListener back = new btnAdminLoginActionListener();
		backToLoginBtn.addActionListener(back);
		bottomPanel.add(backToLoginBtn);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    setTitle("Yönetici Girişi Sayfası"); 
	    setSize(600, 200);        
	    setLocationRelativeTo(null);
	    setVisible(true); 
	    
		cp.add(bottomPanel); }
	    
	   private class btnAdminLoginActionListener implements ActionListener {
		   @Override
		   public void actionPerformed(ActionEvent evt) {
			   if(evt.getSource()== adminLoginBtn) {
				   if(adminUserNameTf.getText().equals("admin") //kullanıcı adı: admin
						   && new String(adminPasswordPf.getPassword()).equals("password")) { //şifre: password
					   AdminHomePage adminhomepage = new AdminHomePage();
					   adminhomepage.setVisible(true);
					   dispose();
				   } else  {
					   JOptionPane.showMessageDialog(null, "Yanlış kullanıcı adı veya şifre","Hata", JOptionPane.ERROR_MESSAGE);
				   }
			   }
				   else if (evt.getSource()==backToLoginBtn) {
					   LoginPage loginpage = new LoginPage();
					   loginpage.setVisible(true);
					   dispose();
				   }
					   
			   }
		   }
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AdminLogin();
			}
		});
	}
	    }

	    


	



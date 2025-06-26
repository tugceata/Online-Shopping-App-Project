import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPage extends JFrame {

    private JLabel userNameLbl;
    private JLabel passwordLbl;
    private JTextField userNameTf;
    private JPasswordField passwordPf;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton loginBtn;
    private JButton registerBtn;
    private JButton adminBtn;
    private GridLayout frameGl;
    private GridLayout topPanelGl;
    private FlowLayout bottomPanelFl;

    public LoginPage() {
    	
    	BackgroundColor backgroundColor = new BackgroundColor();

        Container cp = getContentPane();

        frameGl = new GridLayout(2, 1, 3, 3);
        cp.setLayout(frameGl);

        topPanel = new JPanel();
        topPanelGl = new GridLayout(2, 2, 6, 6);
        topPanel.setBackground(backgroundColor.getBackgroundColor());
        topPanel.setLayout(topPanelGl);

        userNameLbl = new JLabel("Kullanıcı Adı:");
        userNameLbl.setForeground(Color.WHITE);        
        topPanel.add(userNameLbl);
        userNameTf = new JTextField();
        topPanel.add(userNameTf);

        passwordLbl = new JLabel("Şifre:");
        passwordLbl.setForeground(Color.WHITE);  
        topPanel.add(passwordLbl);
        passwordPf = new JPasswordField();
        passwordPf.setEchoChar('*');
        topPanel.add(passwordPf);

        cp.add(topPanel);

        bottomPanel = new JPanel();
        bottomPanelFl = new FlowLayout(FlowLayout.CENTER, 3, 3);
        bottomPanel.setBackground(backgroundColor.getBackgroundColor());
        bottomPanel.setLayout(bottomPanelFl);

        loginBtn = new JButton("Giriş");
        loginBtn.addActionListener(new btnLoginActionListener());
        bottomPanel.add(loginBtn);

        adminBtn = new JButton("Yönetici Girişi");
        adminBtn.addActionListener(new btnLoginActionListener());
        bottomPanel.add(adminBtn);

        registerBtn = new JButton("Kayıt Ol");
        registerBtn.addActionListener(new btnLoginActionListener());
        bottomPanel.add(registerBtn);

        cp.add(bottomPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Giriş Sayfası");
        setSize(600, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class btnLoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == adminBtn) { 
                AdminLogin adminlogin = new AdminLogin();
                adminlogin.setVisible(true); 
                dispose();
            } else if (evt.getSource() == registerBtn) { 
                RegisterPage registerpage = new RegisterPage(new LoginPage());
                registerpage.setVisible(true);
                dispose(); 
                } 
                	
                 else if (evt.getSource() == loginBtn) {
                FileReader fr = null;
                BufferedReader br = null;
                boolean found = false;
                try {
                    fr = new FileReader("src/kullanıcılar.txt");
                    br = new BufferedReader(fr);
                    String line;
                    String[] strArray;
                    while ((line = br.readLine()) != null) {
                        strArray = line.split(",");
                        if ((strArray[4].equals(userNameTf.getText().trim()))
                                && (strArray[5].equals(new String(passwordPf.getPassword()).trim()))) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        System.out.println("Giriş yapıldı.");
                        HomePage homepage = new HomePage();
                        homepage.setVisible(true);
                        dispose();
                        
                    } else {
                    System.out.println("Böyle bir kullanıcı bulunamadı.");
                   JOptionPane.showMessageDialog(null, "Böyle bir kullanıcı bulunamadı.","Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                    System.out.println("Dosya okuma hatası oluştu.");
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                        if (fr != null) {
                            fr.close();
                        }
                    } catch (IOException e) {
                        System.out.println("Dosya kapatma hatası oluştu.");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPage();
            }
        });
    }
}




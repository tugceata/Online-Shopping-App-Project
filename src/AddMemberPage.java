import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddMemberPage extends JFrame {
    
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
    private GridLayout frameGl;
    private GridLayout topPanelGl;
    private FlowLayout bottomPanelFl;
    private JButton addMemberBtn;
    private JButton backToMembersPageBtn;
    private MembersPage mp;

    public AddMemberPage(MembersPage mp) {
        
        this.mp = mp;

        Container cp = getContentPane();

        BackgroundColor backgroundColor = new BackgroundColor();

        frameGl = new GridLayout(2, 1, 3, 3);
        cp.setLayout(frameGl);

        topPanel = new JPanel();
        topPanel.setBackground(backgroundColor.getBackgroundColor());
        topPanelGl = new GridLayout(5, 2, 3, 3);
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
        bottomPanelFl = new FlowLayout(FlowLayout.CENTER);
        bottomPanel.setBackground(backgroundColor.getBackgroundColor());
        bottomPanel.setLayout(bottomPanelFl);

        addMemberBtn = new JButton("Kullanıcıyı Ekle");
        addMemberBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addMember();
            }
        });
        bottomPanel.add(addMemberBtn);
        
        backToMembersPageBtn = new JButton("Geri dön");
        backToMembersPageBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MembersPage memberspage = new MembersPage();
                memberspage.setVisible(true);
                dispose();
            }
        });
        bottomPanel.add(backToMembersPageBtn);
        
        cp.add(bottomPanel);

        setTitle("Yeni kullanıcı ekleme sayfası");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }

    private void addMember() {
        if (isUsernameTaken(userNameTf.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten kayıtlı.");
            return;
        }
        try (FileWriter fWriter = new FileWriter("src/kullanıcılar.txt", true)) { //kullanıcılar.txt'nin içine yazılacak
            String temp = generateNewMemberId() + "," + nameTf.getText().trim() + "," + surnameTf.getText().trim() + ","
                    + ePostaTf.getText().trim() + "," + userNameTf.getText().trim() + "," +
                    passwordTf.getText().trim() + "\r\n"; //id,isim,soyisim,e-posta,kullanıcı adı,şifre

            fWriter.write(temp);
            JOptionPane.showMessageDialog(this, "Kullanıcı eklendi.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kullanıcı eklenirken bir hata oluştu.");
        }
    }

    private boolean isUsernameTaken(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/kullanıcılar.txt"))) { //kullanıcılar.txt içinde arar
            String line;
            while ((line = br.readLine()) != null) {
                String[] strArray = line.split(",");
                if (strArray.length > 4 && strArray[4].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int generateNewMemberId() {
        int tempId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("src/kullanıcılar.txt"))) { //kullanıcılar.txt içinde arar
            String line;
            while ((line = br.readLine()) != null) { //eğer kullanıcılar.txt içine kayıtlı değilse işlemi gerçekleştirir
                String[] strArray = line.split(",");
                tempId = Integer.parseInt(strArray[0]);
                tempId++; //her kullanıcıda sıra numarası bir artar
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempId;
    }
}



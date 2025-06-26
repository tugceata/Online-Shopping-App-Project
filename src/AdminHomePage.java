import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminHomePage extends JFrame {
    
    private JButton technologyBtn;
    private JButton fashionBtn;
    private JButton bookBtn;
    private JButton marketBtn;
    private JButton furnitureBtn;
    private JButton makeupBtn;
    private JButton backToLoginBtn;
    private JButton membersBtn;
    private JButton purchasedProductsBtn;
    private JPanel panel;
    
    public AdminHomePage() {
        
        BackgroundColor backgroundColor = new BackgroundColor();
        
        panel = new JPanel();
        panel.setBackground(backgroundColor.getBackgroundColor());
        panel.setLayout(new GridLayout(3, 2, 3, 3));
        
        technologyBtn = new JButton("Teknoloji");
        panel.add(technologyBtn);
        
        fashionBtn = new JButton("Moda");
        panel.add(fashionBtn);
        
        bookBtn = new JButton("Kitap");
        panel.add(bookBtn);
        
        furnitureBtn = new JButton("Eşya");
        panel.add(furnitureBtn);
        
        makeupBtn = new JButton("Makyaj");
        panel.add(makeupBtn);
        
        marketBtn = new JButton("Market");
        panel.add(marketBtn);
        
        membersBtn = new JButton("Kullanıcılar");
        backToLoginBtn = new JButton("Geri dön");
        purchasedProductsBtn = new JButton("Satın Alınan Ürünler"); // Yeni buton
        
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(3, 1, 5, 5));
        southPanel.add(purchasedProductsBtn);
        southPanel.add(membersBtn);
        southPanel.add(backToLoginBtn);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(panel, BorderLayout.CENTER);
        cp.add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Yönetici Ana Sayfası");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        backToLoginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LoginPage loginpage = new LoginPage();
                loginpage.setVisible(true);
                dispose();
            }
        });
        technologyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AdminTechnologyPage admintechnologypage = new AdminTechnologyPage();
                admintechnologypage.setVisible(true);
                dispose();
            }
        });
         fashionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AdminFashionPage adminfashionpage = new AdminFashionPage();
                adminfashionpage.setVisible(true);
                dispose();
            }
        }); 
        bookBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AdminBookPage adminbookpage = new AdminBookPage();
                adminbookpage.setVisible(true);
                dispose();
            }
        });
        marketBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AdminMarketPage adminmarketpage = new AdminMarketPage();
                adminmarketpage.setVisible(true);
                dispose();
            }
        });
        furnitureBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AdminFurniturePage adminfurniturepage = new AdminFurniturePage();
                adminfurniturepage.setVisible(true);
                dispose();
            }
        });
        makeupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AdminMakeupPage adminmakeuppage = new AdminMakeupPage();
                adminmakeuppage.setVisible(true);
                dispose();
            }
        }); 
        membersBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MembersPage memberspage = new MembersPage();
                memberspage.setVisible(true);
                dispose();
            }
        }); 
        purchasedProductsBtn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent evt) {
                PurchasedProducts purchasedproducts = new PurchasedProducts();
                purchasedproducts.setVisible(true);
                dispose();
               
            }
        }); 
    } 
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminHomePage();
            }
        });
    }
}
	



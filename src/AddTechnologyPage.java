import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;  

public class AddTechnologyPage extends JFrame {
	
    private JLabel productNameLbl;
    private JLabel productPriceLbl;
    private JLabel productStockLbl;
    private JTextField productNameTf;
    private JTextField productPriceTf;
    private JTextField productStockTf;
    private JButton backBtn;
    private JButton addProductBtn;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private GridLayout frameGl;
    private GridLayout topPanelGl;
    private FlowLayout bottomPanelFl;
     
    public AddTechnologyPage() {
    	
    	 Container cp = getContentPane();

         BackgroundColor backgroundcolor = new BackgroundColor(); 
         
         frameGl = new GridLayout(2, 1, 3, 3);
         cp.setLayout(frameGl);

         topPanel = new JPanel();
         topPanel.setBackground(backgroundcolor.getBackgroundColor()); 
         topPanelGl = new GridLayout(3, 2, 3, 3);
         topPanel.setLayout(topPanelGl);

         productNameLbl = new JLabel("Ürün İsmi");
         productNameLbl.setForeground(Color.WHITE);
         topPanel.add(productNameLbl);
         productNameTf = new JTextField();
         topPanel.add(productNameTf);

         productPriceLbl = new JLabel("Ürün Fiyatı");
         productPriceLbl.setForeground(Color.WHITE);
         topPanel.add(productPriceLbl);
         productPriceTf = new JTextField();
         topPanel.add(productPriceTf);

         productStockLbl = new JLabel("Ürün Stoğu");
         productStockLbl.setForeground(Color.WHITE);
         topPanel.add(productStockLbl);
         productStockTf = new JTextField();
         topPanel.add(productStockTf);

         cp.add(topPanel);

         bottomPanel = new JPanel();
         bottomPanel.setBackground(backgroundcolor.getBackgroundColor());
         bottomPanelFl = new FlowLayout(FlowLayout.CENTER);
         bottomPanel.setLayout(bottomPanelFl);

         backBtn = new JButton("Geri Dön");
         addTechnologyPageBtnActionListener back = new addTechnologyPageBtnActionListener();
         backBtn.addActionListener(back);
         bottomPanel.add(backBtn);

         addProductBtn = new JButton("Ürünü Kaydet");
         addTechnologyPageBtnActionListener add = new addTechnologyPageBtnActionListener();
         addProductBtn.addActionListener(add);
         bottomPanel.add(addProductBtn);

         cp.add(bottomPanel, BorderLayout.SOUTH);

         setTitle("Yeni Ürün Ekle");
         setSize(900, 400);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
         setVisible(true);
         
     }

     private class addTechnologyPageBtnActionListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent evt) {
             if (evt.getSource() == addProductBtn) {
                 boolean found = false;
                 int maxID = 0;

                 try (BufferedReader br = new BufferedReader(new FileReader("src/techproducts.txt"))) {
                     String line;
                     while ((line = br.readLine()) != null) {
                         String[] strArray = line.split(",");
                         int currentID = Integer.parseInt(strArray[1]);
                         maxID = Math.max(maxID, currentID);
                         if (strArray[2].equals(productNameTf.getText().trim())) {
                             found = true;
                             JOptionPane.showMessageDialog(null, "Bu ürün zaten mevcut.", "Hata", JOptionPane.ERROR_MESSAGE);
                             break;
                         }
                     }
                 } catch (IOException e) {
                     System.out.println("Dosya okuma hatası oluştu.");
                 }

                 if (!found) {
                     try (FileWriter filewriter = new FileWriter("src/techproducts.txt", true)) {
                         maxID++;
                         String temp = ("Teknoloji," + maxID + "," + productNameTf.getText().trim() + ","
                                 + productPriceTf.getText().trim() + "," + productStockTf.getText().trim() + "\r\n");
                         filewriter.write(temp);
                         JOptionPane.showMessageDialog(null, "Yeni ürün kaydedildi.");
                         dispose();
                         new AdminTechnologyPage().setVisible(true);
                     } catch (IOException e) {
                         System.out.println("Ürün kaydetme başarısız oldu.");
                     }
                 }
             } else if (evt.getSource() == backBtn) {
                 new AdminTechnologyPage().setVisible(true);
                 dispose();
             }
         }
     }

     public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                 new AddTechnologyPage();
             }
         });
     }
 


   }



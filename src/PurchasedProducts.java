import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PurchasedProducts extends JFrame {

    public PurchasedProducts() {

        setTitle("Satın Alınan Ürünler");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tarih sütununu da içerecek şekilde sütun isimlerini güncelledik
        String[] columnNames = {"Kategori", "ID", "Ürün İsmi", "Ürün Fiyatı", "Ürün Stoğu", "Kullanıcı Adı", "Tarih"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);

        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadCartData(model);

        JButton backButton = new JButton("Geri dön");
        backButton.addActionListener(e -> {
            dispose();
            AdminHomePage adminhomePage = new AdminHomePage();
            adminhomePage.setVisible(true);
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadCartData(DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                model.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PurchasedProducts purchasedproducts = new PurchasedProducts();
            purchasedproducts.setVisible(true);
        });
    }
}


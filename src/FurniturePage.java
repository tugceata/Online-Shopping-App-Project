import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FurniturePage extends JFrame {

    private static JTable table;
    private DefaultTableModel model;
    private List<String[]> productList;

    public FurniturePage() {

        setTitle("Eşyalar");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Kategori", "ID", "Ürün İsmi", "Ürün Fiyatı", "Ürün Stoğu", ""}; //sütun isimleri
        
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; //Sadece sepete ekle butonunun olduğu sütun düzenlenilebilir
            }
        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false); //Sütun başlıkları düzenlenemez

        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox())); //5.sütunda buton olmasını sağlar

        JScrollPane scrollPane = new JScrollPane(table); //tablo büyüdüğünde aşağı doğru kaydırılabilir
        add(scrollPane, BorderLayout.CENTER); 

        JButton backToHomePageBtn = new JButton("Ana sayfaya geri dön");
        backToHomePageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homepage = new HomePage(); 
                homepage.setVisible(true);
                dispose();
            }
        });
        add(backToHomePageBtn, BorderLayout.SOUTH); 

        productList = new ArrayList<>(); //productList dizisinin içinde teknoloji ürünleri var (dosyadan yüklendi)
        loadProductsFromFile("src/furnitureproducts.txt");
        setVisible(true);
    }

    private void loadProductsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                rowData = new String[]{rowData[0], rowData[1], rowData[2], rowData[3], rowData[4], "Sepete Ekle"};
                model.addRow(rowData); //5.sütundaki her butonun üstüne 'Sepete Ekle' yazar
                productList.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace(); //hata ayıklama
        }
    }
    public static JTable getTable() {
        return table;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FurniturePage furniturepage = new FurniturePage();
            furniturepage.setVisible(true);
        });
    }
    // Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true); //setvisible
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString()); 
            return this;
        }
    }
    // Button Editor
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped(); //düzenleme biter
                    saveToCart(table, row);
                }
            });
            isPushed = true;
            return button; //butona tıklanıldığında sepete eklenir
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                isPushed = false;
            }
            return label; 
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        private void saveToCart(JTable table, int row) {
            String category = table.getValueAt(row, 0).toString();
            String id = table.getValueAt(row, 1).toString();
            String productName = table.getValueAt(row, 2).toString();
            String price = table.getValueAt(row, 3).toString();
            String stock = table.getValueAt(row, 4).toString();

            //Sepete eklenmeden önce stok sayısını kontrol edilir ve azaltılır
            int stockCount = Integer.parseInt(stock);
            if (stockCount > 0) {
                stockCount--; //Stok sayısını azalt
                table.setValueAt(stockCount, row, 4); //Tabloda stok sayısını güncelle

                updateProductStock(id, stockCount); //dosyayı güncelle
            } else {
                JOptionPane.showMessageDialog(null, "Bu ürün stokta mevcut değil.");
                return;
            }

            // Tarih ve zaman bilgisini al
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            try (FileWriter writer = new FileWriter("src/cart.txt", true)) {
                writer.write(category + "," + id + "," + productName + "," + price + "," + stockCount + "," + LoginUser.getUserName() + "," + formattedDateTime + "\n");
                JOptionPane.showMessageDialog(null, "Ürün sepete eklendi.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void removeFromCart(JTable table, int row) {
            String id = table.getValueAt(row, 1).toString();
            String stock = table.getValueAt(row, 4).toString();

            int stockCount = Integer.parseInt(stock);
            stockCount++; //ürün sepetten çıkarıldığında stok tekrar artar

            table.setValueAt(stockCount, row, 4);

            updateProductStock(id, stockCount);
        }
    }
    private void updateProductStock(String id, int newStock) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/furnitureproducts_temp.txt"))) {
            for (String[] product : productList) {
                if (product[1].equals(id)) {
                    product[4] = String.valueOf(newStock); //Yeni stok sayısını ayarla
                }
                writer.write(String.join(",", product) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File oldFile = new File("src/furnitureproducts.txt");
        File newFile = new File("src/furnitureproducts_temp.txt");
        if (newFile.exists() && oldFile.delete()) {
            newFile.renameTo(oldFile); //Yeni dosyanın adını eski dosyanın adına değiştir
        }
    }
}


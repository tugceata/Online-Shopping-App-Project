import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminFurniturePage extends JFrame {

    private static JTable table;
    private DefaultTableModel model;
    private boolean isEditing = false;
    private int editingRow = -1;

    public AdminFurniturePage() {

        setTitle("Eşyalar");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Kategori", "ID", "Ürün İsmi", "Ürün Fiyatı", "Ürün Stoğu"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditing && row == editingRow;
            }
        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton refreshPageBtn = new JButton("Sayfayı Yenile");
        refreshPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPage();
            }
        });
        topPanel.add(refreshPageBtn);
        
        add(topPanel, BorderLayout.NORTH);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backToHomePageBtn = new JButton("Ana sayfaya geri dön");
        backToHomePageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminHomePage adminhomepage = new AdminHomePage();
                adminhomepage.setVisible(true);
            }
        });
        bottomPanel.add(backToHomePageBtn);

        JButton deleteProductBtn = new JButton("Ürün Kaldır");
        deleteProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProduct();
            }
        });
        bottomPanel.add(deleteProductBtn);

        JButton editProductBtn = new JButton("Ürün Bilgilerini Düzenle");
        editProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedProduct();
            }
        });
        bottomPanel.add(editProductBtn);

        JButton saveProductBtn = new JButton("Kaydet");
        saveProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEditedProduct();
            }
        });
        bottomPanel.add(saveProductBtn);
        
        JButton addFurnitureBtn = new JButton("Yeni ürün ekle");
        addFurnitureBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		if(evt.getSource() == addFurnitureBtn) {
        			AddFurniturePage addfurniturepage = new AddFurniturePage();
        			addfurniturepage.setVisible(true);
        			dispose();
        		}
        	}
        });
        bottomPanel.add(addFurnitureBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        loadProductsFromFile("src/furnitureproducts.txt");
        setVisible(true);
    }

    private void loadProductsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                model.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir satır seçin.");
            return;
        }

        String idToDelete = table.getValueAt(selectedRow, 1).toString();

        model.removeRow(selectedRow);
        deleteProductFromFile(idToDelete);
    }

    private void deleteProductFromFile(String idToDelete) {
        File inputFile = new File("src/furnitureproducts.txt");
        File tempFile = new File("src/temp_furnitureproducts.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] rowData = currentLine.split(",");
                if (!rowData[1].equals(idToDelete)) {
                    writer.write(currentLine + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        } else {
            JOptionPane.showMessageDialog(this, "Ürün silinirken bir hata oluştu.");
        }
    }

    private void editSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen düzenlemek için bir satır seçin.");
            return;
        }

        isEditing = true;
        editingRow = selectedRow;
        model.fireTableRowsUpdated(selectedRow, selectedRow);
    }

    private void saveEditedProduct() {
        if (editingRow == -1) {
            JOptionPane.showMessageDialog(this, "Düzenlemek için bir satır seçmediniz.");
            return;
        }

        String idToEdit = table.getValueAt(editingRow, 1).toString();
        List<String[]> allProducts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/furnitureproducts.txt"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] rowData = currentLine.split(",");
                if (rowData[1].equals(idToEdit)) {
                    rowData = new String[] {
                        table.getValueAt(editingRow, 0).toString(),
                        table.getValueAt(editingRow, 1).toString(),
                        table.getValueAt(editingRow, 2).toString(),
                        table.getValueAt(editingRow, 3).toString(),
                        table.getValueAt(editingRow, 4).toString()
                    };
                }
                allProducts.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/furnitureproducts.txt"))) {
            for (String[] product : allProducts) {
                writer.write(String.join(",", product) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        isEditing = false;
        editingRow = -1;
        model.fireTableRowsUpdated(0, model.getRowCount() - 1);
        JOptionPane.showMessageDialog(this, "Ürün güncellendi.");
    }

    private void refreshPage() {
        model.setRowCount(0); 
        loadProductsFromFile("src/furnitureproducts.txt"); 
    }

    public static JTable getTable() {
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFurniturePage adminfurniturepage = new AdminFurniturePage();
            adminfurniturepage.setVisible(true);
        });
    }
}

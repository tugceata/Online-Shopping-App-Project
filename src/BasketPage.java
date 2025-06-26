import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BasketPage extends JFrame {

    public BasketPage() {
        setTitle("Sepetim");
        setSize(900, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Kategori", "ID", "Ürün İsmi", "Ürün Fiyatı", "Ürün Stoğu", "Kullanıcı Adı", "Tarih", "Ürünü Kaldır"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Sadece "Ürünü Kaldır" sütunu düzenlenebilir
            }
        };

        JTable table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 7) return JButton.class; // "Ürünü Kaldır" sütunu için JButton sınıfını belirt
                return Object.class;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadCartData(model);

        JButton resetButton = new JButton("Sepeti Sıfırla");
        resetButton.addActionListener(e -> {
            model.setRowCount(0);
            try {
                PrintWriter writer = new PrintWriter("src/cart.txt");
                writer.print("");
                writer.close();
                JOptionPane.showMessageDialog(this, "Sepet Sıfırlandı");

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sepeti sıfırlama işleminde hata oluştu.");
            }
        });

        JButton backButton = new JButton("Ana sayfaya geri dön");
        backButton.addActionListener(e -> {
            dispose();
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        table.getColumn("Ürünü Kaldır").setCellRenderer(new ButtonRenderer());
        table.getColumn("Ürünü Kaldır").setCellEditor(new ButtonEditor(new JCheckBox()));

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

    private void updateCartFile(DefaultTableModel model) {
        try (FileWriter writer = new FileWriter("src/cart.txt")) {
            for (int row = 0; row < model.getRowCount(); row++) {
                StringBuilder rowString = new StringBuilder();
                for (int col = 0; col < model.getColumnCount() - 1; col++) {
                    rowString.append(model.getValueAt(row, col).toString()).append(",");
                }
                rowString.deleteCharAt(rowString.length() - 1); // Son virgülü kaldır
                writer.write(rowString.toString() + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasketPage basketPage = new BasketPage();
            basketPage.setVisible(true);
        });
    }

    // Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Ürünü Kaldır" : value.toString());
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

        JButton button = new JButton("Ürünü Kaldır");

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Ürünü Kaldır" : value.toString();
            JButton button = new JButton(label);
            button.addActionListener(e -> {
                fireEditingStopped();
                int modelRow = table.convertRowIndexToModel(row);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                updateCartFile((DefaultTableModel) table.getModel());
            });
            return button;
        }

        @Override
        public Object getCellEditorValue() {
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
    }
}

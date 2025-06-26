import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MembersPage extends JFrame {

    private DefaultTableModel model;

    public MembersPage() {
    	
        setTitle("Kullanıcılar");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "İsim", "Soyisim", "E-Posta", "Kullanıcı Adı", "Şifre", "Sil"};
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; //Sadece 'Kaldır' butonunun olduğu sütun düzenlenebilir.
            }
        };
        JTable table = new JTable(model);

        //Buton hücreleri için özel renderer ve editor ayarları
        table.getColumn("Sil").setCellRenderer(new ButtonRenderer());
        table.getColumn("Sil").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollpane = new JScrollPane(table);
        add(scrollpane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Sayfayı yenile");
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPage(); 
            }
        });

        JButton backBtn = new JButton("Ana sayfaya geri dön");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminHomePage adminhomepage = new AdminHomePage();
                adminhomepage.setVisible(true);
                dispose();
            }
        });
      
     	JPanel southPanel = new JPanel();
    	southPanel.setLayout(new GridLayout(1, 2, 5, 5));
    	southPanel.add(refreshBtn);
    	southPanel.add(backBtn);
    	
    	add(southPanel, BorderLayout.SOUTH);

        JButton addMemberBtn = new JButton("Yeni kullanıcı ekle");
        addMemberBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMemberPage addmemberpage = new AddMemberPage(null);
                addmemberpage.setVisible(true);
                dispose();
            }
        });
        add(addMemberBtn, BorderLayout.NORTH);

        loadMembersData();
        setVisible(true);
    }

    private void loadMembersData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/kullanıcılar.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                rowData = appendRemoveButton(rowData); //Kaldır butonu eklenir
                model.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] appendRemoveButton(String[] rowData) {
        String[] newRowData = new String[rowData.length + 1];
        System.arraycopy(rowData, 0, newRowData, 0, rowData.length);
        newRowData[rowData.length] = "Sil";
        return newRowData;
    }

    private void refreshPage() {
        model.setRowCount(0);
        loadMembersData();
    }

    private void removeRowAndUpdateFile(int row) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Bu kullanıcıyı silmek istiyor musunuz?", "Uyarı", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
           
            String id = (String) model.getValueAt(row, 0); //silinen satır kaldırılır
            model.removeRow(row);

            List<String> lines = new ArrayList<>(); //kullanıcılar.txt dosyası güncellenir
            try (BufferedReader reader = new BufferedReader(new FileReader("src/kullanıcılar.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith(id + ",")) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/kullanıcılar.txt"))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MembersPage::new); //güncellenmiş 
    }

    // Buton render ve editör sınıfları
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
                    fireEditingStopped();
                    removeRowAndUpdateFile(row);
                }
            });
            isPushed = true;
            return button;
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
    }
}


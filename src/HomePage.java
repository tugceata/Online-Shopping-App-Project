import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame {
	
	private JButton technologyBtn;
	private JButton fashionBtn;
	private JButton bookBtn;
	private JButton marketBtn;
	private JButton furnitureBtn;
	private JButton makeupBtn;
	private JButton backToLoginBtn;
	private JButton basketBtn;
	private JPanel panel;
	
	public HomePage() {
		
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
		
		basketBtn = new JButton("Sepetim");
		backToLoginBtn = new JButton("Geri dön");

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 1, 5, 5));
		southPanel.add(basketBtn);
		southPanel.add(backToLoginBtn);

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(panel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
		
		setTitle("Ana Sayfa");
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
				TechnologyPage technologypage = new TechnologyPage();
				technologypage.setVisible(true);
				dispose();
			}
		});
		fashionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				FashionPage fashionpage = new FashionPage();
				fashionpage.setVisible(true);
				dispose();
			}
		});
		bookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BookPage bookpage = new BookPage();
				bookpage.setVisible(true);
				dispose();
			}
		});
		marketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MarketPage marketpage = new MarketPage();
				marketpage.setVisible(true);
				dispose();
			}
		});
		furnitureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				FurniturePage furniturepage = new FurniturePage();
				furniturepage.setVisible(true);
				dispose();
			}
		});
		makeupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MakeupPage makeuppage = new MakeupPage();
				makeuppage.setVisible(true);
				dispose();
			}
		});
		basketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BasketPage basketpage = new BasketPage();
				basketpage.setVisible(true);
				dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new HomePage();
			}
		});
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import backend.Book;
import backend.BookRepository;
import controller.BookController;

public class LibraryGUI extends JFrame {

    String role, name;

    JTable table;
    DefaultTableModel tableModel;
    JTextArea areaHistory;
    JTextField tfCode, tfTitle, tfAuthor, tfStock, tfSearch;

    JButton btnAdd, btnToggle, btnBorrow, btnReturn, btnUpdateStock;

    // ===== Constructor untuk Login =====
    public LibraryGUI() {
        setTitle("Form Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color lightBlue = new Color(173, 216, 230);
        Color darkBlue = new Color(0, 102, 204);
        Color white = Color.WHITE;

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(lightBlue);
        tabbedPane.setForeground(darkBlue);

        // ===== Tab Pustakawan =====
        JPanel panelPustakawan = new JPanel(new GridBagLayout());
        panelPustakawan.setBackground(white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(darkBlue);
        gbc.gridx = 0; gbc.gridy = 0;
        panelPustakawan.add(lblUser, gbc);

        JTextField tfUser = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panelPustakawan.add(tfUser, gbc);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(darkBlue);
        gbc.gridx = 0; gbc.gridy = 1;
        panelPustakawan.add(lblPass, gbc);

        JPasswordField pfPass = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panelPustakawan.add(pfPass, gbc);

        JButton btnLoginP = new JButton("Login");
        btnLoginP.setBackground(darkBlue);
        btnLoginP.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 2;
        panelPustakawan.add(btnLoginP, gbc);

        // ===== Tab Peminjaman =====
        JPanel panelPeminjam = new JPanel(new GridBagLayout());
        panelPeminjam.setBackground(white);

        JLabel lblUser2 = new JLabel("Username:");
        lblUser2.setForeground(darkBlue);
        gbc.gridx = 0; gbc.gridy = 0;
        panelPeminjam.add(lblUser2, gbc);

        JTextField tfUser2 = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panelPeminjam.add(tfUser2, gbc);

        JButton btnLoginM = new JButton("Login");
        btnLoginM.setBackground(darkBlue);
        btnLoginM.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        panelPeminjam.add(btnLoginM, gbc);

        tabbedPane.addTab("Pustakawan", panelPustakawan);
        tabbedPane.addTab("Peminjaman", panelPeminjam);

        add(tabbedPane);
        setVisible(true);

        // ===== Action Login =====
        btnLoginP.addActionListener(e -> {
            String user = tfUser.getText();
            String pass = new String(pfPass.getPassword());
            if (pass.equals("admin")) { // contoh validasi password
                dispose();
                new LibraryGUI("Pustakawan", user); // masuk ke tampilan utama
            } else {
                JOptionPane.showMessageDialog(this, "Password salah!");
            }
        });

        btnLoginM.addActionListener(e -> {
            String user = tfUser2.getText();
            if (!user.isEmpty()) {
                dispose();
                new LibraryGUI("Peminjam", user); // masuk ke tampilan utama
            } else {
                JOptionPane.showMessageDialog(this, "Username wajib diisi!");
            }
        });
    }

    // ===== Constructor untuk Tampilan Utama =====
    public LibraryGUI(String role, String name) {
        this.role = role;
        this.name = name;

        setTitle("Manajemen Perpustakaan");
        setSize(980, 680);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Warna tema
        Color lightBlue = new Color(173, 216, 230);
        Color white = Color.WHITE;
        Color darkBlue = new Color(0, 102, 204);

        getContentPane().setBackground(lightBlue);

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(white);
        headerPanel.setBounds(0, 0, 980, 50);
        headerPanel.setLayout(null);

        JLabel lblUser = new JLabel("Login: " + role + " - " + name);
        lblUser.setForeground(darkBlue);
        lblUser.setFont(lblUser.getFont().deriveFont(14f).deriveFont(Font.BOLD));
        lblUser.setBounds(20, 10, 400, 25);
        headerPanel.add(lblUser);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(darkBlue);
        btnLogout.setForeground(white);
        btnLogout.setBounds(860, 10, 90, 25);
        headerPanel.add(btnLogout);

        btnLogout.addActionListener(e -> {
            dispose();
            new LibraryGUI(); // kembali ke login
        });

        add(headerPanel);

        // ===== SEARCH PANEL =====
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(white);
        searchPanel.setBounds(20, 60, 930, 50);
        searchPanel.setLayout(null);

        JLabel lblSearch = new JLabel("Cari Buku:");
        lblSearch.setBounds(10, 15, 100, 20);
        lblSearch.setForeground(darkBlue);
        searchPanel.add(lblSearch);

        tfSearch = new JTextField();
        tfSearch.setBounds(120, 12, 350, 25);
        searchPanel.add(tfSearch);

        add(searchPanel);

        // ===== TABLE PANEL =====
        tableModel = new DefaultTableModel(
                new String[]{"Kode", "Judul", "Penulis", "Stok", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setBackground(white);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(lightBlue);
        table.getTableHeader().setBackground(darkBlue);
        table.getTableHeader().setForeground(white);

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(white);
        tablePanel.setBounds(20, 120, 930, 200);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(tablePanel);

        // ===== FORM PANEL (PUSTAKAWAN) =====
        JPanel formPanel = new JPanel();
        formPanel.setBackground(lightBlue);
        formPanel.setBounds(20, 330, 930, 80);
        formPanel.setLayout(null);

        JLabel lblForm = new JLabel("Kelola Buku (Pustakawan)");
        lblForm.setBounds(10, 10, 300, 20);
        lblForm.setForeground(darkBlue);
        formPanel.add(lblForm);

        tfCode = new JTextField();
        tfTitle = new JTextField();
        tfAuthor = new JTextField();
        tfStock = new JTextField();

        tfCode.setBounds(20, 40, 80, 25);
        tfTitle.setBounds(110, 40, 220, 25);
        tfAuthor.setBounds(340, 40, 220, 25);
        tfStock.setBounds(570, 40, 60, 25);

        formPanel.add(tfCode);
        formPanel.add(tfTitle);
        formPanel.add(tfAuthor);
        formPanel.add(tfStock);

        btnAdd = new JButton("Tambah Buku");
        btnAdd.setBounds(650, 40, 130, 25);
        btnAdd.setBackground(darkBlue);
        btnAdd.setForeground(white);
        formPanel.add(btnAdd);

        btnToggle = new JButton("Set Tersedia");
        btnToggle.setBounds(790, 40, 130, 25);
        btnToggle.setBackground(darkBlue);
        btnToggle.setForeground(white);
        formPanel.add(btnToggle);

        btnUpdateStock = new JButton("Update Stok");
        btnUpdateStock.setBounds(650, 10, 130, 25);
        btnUpdateStock.setBackground(darkBlue);
                btnUpdateStock.setForeground(white);
        formPanel.add(btnUpdateStock);

        add(formPanel);

        // ===== ACTION PANEL (PEMINJAM) =====
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(white);
        actionPanel.setBounds(20, 420, 930, 50);
        actionPanel.setLayout(null);

        btnBorrow = new JButton("Pinjam");
        btnBorrow.setBackground(darkBlue);
        btnBorrow.setForeground(white);
        btnBorrow.setBounds(20, 10, 120, 30);
        actionPanel.add(btnBorrow);

        btnReturn = new JButton("Kembalikan");
        btnReturn.setBackground(darkBlue);
        btnReturn.setForeground(white);
        btnReturn.setBounds(150, 10, 140, 30);
        actionPanel.add(btnReturn);

        add(actionPanel);

        // ===== HISTORY PANEL =====
        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(white);
        historyPanel.setBounds(20, 480, 930, 150);
        historyPanel.setLayout(new BorderLayout());

        JLabel lblHistory = new JLabel("Riwayat Peminjaman dan Pengembalian");
        lblHistory.setForeground(darkBlue);
        historyPanel.add(lblHistory, BorderLayout.NORTH);

        areaHistory = new JTextArea();
        areaHistory.setBackground(white);
        areaHistory.setForeground(Color.BLACK);
        areaHistory.setBorder(BorderFactory.createLineBorder(darkBlue));
        areaHistory.setEditable(false);
        historyPanel.add(new JScrollPane(areaHistory), BorderLayout.CENTER);

        add(historyPanel);

        // ===== ROLE ACCESS =====
        boolean isLibrarian = role.equalsIgnoreCase("Pustakawan");

        lblForm.setVisible(isLibrarian);
        tfCode.setVisible(isLibrarian);
        tfTitle.setVisible(isLibrarian);
        tfAuthor.setVisible(isLibrarian);
        tfStock.setVisible(isLibrarian);
        btnAdd.setVisible(isLibrarian);
        btnToggle.setVisible(isLibrarian);
        btnUpdateStock.setVisible(isLibrarian);

        btnBorrow.setVisible(!isLibrarian);
        btnReturn.setVisible(!isLibrarian);

        // ===== ACTIONS =====
        btnAdd.addActionListener(e -> {
            try {
                BookController.addBook(
                        tfCode.getText(),
                        tfTitle.getText(),
                        tfAuthor.getText(),
                        Integer.parseInt(tfStock.getText())
                );
                clearForm();
                refresh(tfSearch.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input buku tidak valid");
            }
        });

        btnToggle.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih buku di tabel");
                return;
            }
            BookController.toggleAvailability(BookRepository.books.get(row));
            refresh(tfSearch.getText());
        });

        btnUpdateStock.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih buku di tabel");
                return;
            }
            String newStockStr = JOptionPane.showInputDialog(this, "Masukkan stok baru:");
            try {
                int newStock = Integer.parseInt(newStockStr);
                Book book = BookRepository.books.get(row);
                book.setStock(newStock);
                refresh(tfSearch.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input stok tidak valid");
            }
        });

        btnBorrow.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih buku terlebih dahulu");
                return;
            }
            Book book = BookRepository.books.get(row);

            SpinnerDateModel model = new SpinnerDateModel();
            model.setValue(java.sql.Date.valueOf(LocalDate.now()));
            JSpinner spinner = new JSpinner(model);
            spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));

            int result = JOptionPane.showConfirmDialog(this, spinner, "Pilih Tanggal Pinjam", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                LocalDate chosenDate = ((java.util.Date) spinner.getValue()).toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();

                if (chosenDate.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Tanggal tidak boleh sebelum hari ini!");
                    return;
                }

                try {
                    BookController.borrowBook(name, book);
                    BookRepository.loanHistory.add(
                            name + " meminjam buku " + book.getTitle() + " dengan penulis " + book.getAuthor() + ", pada tanggal " + chosenDate
                    );
                    refresh(tfSearch.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

        btnReturn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih buku terlebih dahulu");
                return;
            }
            Book book = BookRepository.books.get(row);

            SpinnerDateModel model = new SpinnerDateModel();
            model.setValue(java.sql.Date.valueOf(LocalDate.now()));
            JSpinner spinner = new JSpinner(model);
            spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));

            int result = JOptionPane.showConfirmDialog(this, spinner, "Pilih Tanggal Kembali", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                LocalDate chosenDate = ((java.util.Date) spinner.getValue()).toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();

                if (chosenDate.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Tanggal tidak boleh sebelum hari ini!");
                    return;
                }

                BookController.returnBook(name, book);
                BookRepository.loanHistory.add(
                        name + " mengembalikan buku " + book.getTitle() + " dengan penulis " + book.getAuthor() + ", pada tanggal " + chosenDate
                );
                refresh(tfSearch.getText());
            }
        });

        tfSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refresh(tfSearch.getText());
            }
        });

        refresh("");
        setVisible(true);
    }

    void refresh(String keyword) {
        tableModel.setRowCount(0);
        areaHistory.setText("");

        for (Book b : BookRepository.books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getCode().toLowerCase().contains(keyword.toLowerCase())) {

                tableModel.addRow(new Object[]{
                        b.getCode(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getStock(),
                        b.isAvailable() ? "Tersedia" : "Tidak Tersedia"
                });
            }
        }

        for (String h : BookRepository.loanHistory) {
            areaHistory.append(h + "\n");
        }
    }

    void clearForm() {
        tfCode.setText("");
        tfTitle.setText("");
        tfAuthor.setText("");
        tfStock.setText("");
        tfCode.requestFocus();
    }

    // Entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryGUI::new);
    }
}
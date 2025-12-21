package gui;

import backend.*;
import controller.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryGUI extends JFrame {

    private JTextField txtKode, txtJudul, txtStok, txtCari;
    private JTable table;
    private DefaultTableModel model;
    private BookController controller = new BookController();

    public LibraryGUI() {
        setTitle("Aplikasi Perpustakaan");
        setSize(650, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === FORM INPUT ===
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        txtKode = new JTextField();
        txtJudul = new JTextField();
        txtStok = new JTextField();

        form.add(new JLabel("Kode Buku"));
        form.add(txtKode);
        form.add(new JLabel("Judul Buku"));
        form.add(txtJudul);
        form.add(new JLabel("Stok"));
        form.add(txtStok);

        JButton btnTambah = new JButton("Tambah");
        form.add(new JLabel(""));
        form.add(btnTambah);

        add(form, BorderLayout.NORTH);

        // === TABLE ===
        model = new DefaultTableModel(new String[]{"Kode", "Judul", "Stok"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // === BUTTON PANEL ===
        JPanel panelBtn = new JPanel();

        JButton btnHapus = new JButton("Hapus");
        JButton btnPinjam = new JButton("Pinjam");
        JButton btnKembali = new JButton("Kembalikan");

        txtCari = new JTextField(10);
        JButton btnCari = new JButton("Cari");

        panelBtn.add(btnHapus);
        panelBtn.add(btnPinjam);
        panelBtn.add(btnKembali);
        panelBtn.add(txtCari);
        panelBtn.add(btnCari);

        add(panelBtn, BorderLayout.SOUTH);

        // === EVENT HANDLING ===
        btnTambah.addActionListener(e -> tambahBuku());
        btnHapus.addActionListener(e -> hapusBuku());
        btnPinjam.addActionListener(e -> pinjamBuku());
        btnKembali.addActionListener(e -> kembalikanBuku());
        btnCari.addActionListener(e -> cariBuku());
    }

    private void tambahBuku() {
        Book b = new Book(
                txtKode.getText(),
                txtJudul.getText(),
                Integer.parseInt(txtStok.getText())
        );
        controller.getRepo().tambah(b);
        refreshTable();
    }

    private void hapusBuku() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            controller.getRepo().hapus(row);
            refreshTable();
        }
    }

    private void pinjamBuku() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            try {
                Book b = controller.getRepo().getAll().get(row);
                controller.pinjamBuku(b);
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void kembalikanBuku() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Book b = controller.getRepo().getAll().get(row);
            controller.kembalikanBuku(b);
            refreshTable();
        }
    }

    private void cariBuku() {
        Book b = controller.getRepo().cari(txtCari.getText());
        if (b != null) {
            JOptionPane.showMessageDialog(this, "Buku ditemukan: " + b.getJudul());
        } else {
            JOptionPane.showMessageDialog(this, "Buku tidak ditemukan");
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Book b : controller.getRepo().getAll()) {
            model.addRow(new Object[]{
                    b.getKode(),
                    b.getJudul(),
                    b.getStok()
            });
        }
    }
}
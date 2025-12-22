package gui;

import backend.*;
import controller.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryGUI extends JFrame {

    private JTextField txtKode, txtJudul, txtPenulis, txtStok, txtCari;
    private JTable table;
    private DefaultTableModel model;
    private BookController controller = new BookController();

    public LibraryGUI() {
        setTitle("Perpustakaan");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== JUDUL =====
        JLabel title = new JLabel("Perpustakaan", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(9, 1, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtKode = new JTextField();
        txtJudul = new JTextField();
        txtPenulis = new JTextField();
        txtStok = new JTextField();

        form.add(new JLabel("Kode Buku"));
        form.add(txtKode);
        form.add(new JLabel("Judul Buku"));
        form.add(txtJudul);
        form.add(new JLabel("Penulis"));
        form.add(txtPenulis);
        form.add(new JLabel("Stok"));
        form.add(txtStok);

        JButton btnTambah = new JButton("Tambah");
        form.add(btnTambah);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"Kode", "Judul", "Penulis", "Stok"}, 0);
        table = new JTable(model);
        table.setFont(font);
        table.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(table);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, scroll);
        split.setDividerLocation(300);
        add(split, BorderLayout.CENTER);

        // ===== BOTTOM =====
        JPanel bottom = new JPanel();

        JButton btnHapus = new JButton("Hapus");
        JButton btnPinjam = new JButton("Pinjam");
        JButton btnKembali = new JButton("Kembalikan");

        txtCari = new JTextField(15);
        JButton btnCari = new JButton("Cari");

        bottom.add(btnHapus);
        bottom.add(btnPinjam);
        bottom.add(btnKembali);
        bottom.add(txtCari);
        bottom.add(btnCari);

        add(bottom, BorderLayout.SOUTH);

        // ===== EVENT =====
        btnTambah.addActionListener(e -> tambah());
        btnHapus.addActionListener(e -> hapus());
        btnPinjam.addActionListener(e -> pinjam());
        btnKembali.addActionListener(e -> kembali());
        btnCari.addActionListener(e -> cari());
    }

    private void tambah() {
        Book b = new Book(
            txtKode.getText(),
            txtJudul.getText(),
            txtPenulis.getText(),
            Integer.parseInt(txtStok.getText())
        );
        controller.getRepo().tambah(b);
        refresh();
    }

    private void hapus() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            controller.getRepo().hapus(row);
            refresh();
        }
    }

    private void pinjam() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            try {
                controller.pinjamBuku(controller.getRepo().getAll().get(row));
                refresh();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void kembali() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            controller.kembalikanBuku(controller.getRepo().getAll().get(row));
            refresh();
        }
    }

    private void cari() {
        Book b = controller.getRepo().cari(txtCari.getText());
        if (b != null) {
            JOptionPane.showMessageDialog(this,
                "Judul: " + b.getJudul() +
                "\nPenulis: " + b.getPenulis() +
                "\nStok: " + b.getStok()
            );
        } else {
            JOptionPane.showMessageDialog(this, "Buku tidak ditemukan");
        }
    }

    private void refresh() {
        model.setRowCount(0);
        for (Book b : controller.getRepo().getAll()) {
            model.addRow(new Object[]{
                b.getKode(),
                b.getJudul(),
                b.getPenulis(),
                b.getStok()
            });
        }
    }
}
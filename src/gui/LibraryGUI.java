package gui;

import backend.Book;
import controller.BookController;

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

        model = new DefaultTableModel(new String[]{"Kode", "Judul", "Stok"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

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

        btnTambah.addActionListener(e -> tambahBuku());
        btnHapus.addActionListener(e -> hapusBuku());
        btnPinjam.addActionListener(e -> pinjamBuku());
        btnKembali.addActionListener(e -> kembalikanBuku());
        btnCari.addActionListener(e -> cariBuku());
    }

    private void tambahBuku() {
        controller.tambahBuku(
                txtKode.getText(),
                txtJudul.getText(),
                Integer.parseInt(txtStok.getText())
        );
        refreshTable();
    }

    private void hapusBuku() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            controller.hapusBuku(row);
            refreshTable();
        }
    }

    private void pinjamBuku() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            try {
                controller.pinjamBuku(row);
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void kembalikanBuku() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            controller.kembalikanBuku(row);
            refreshTable();
        }
    }

    private void cariBuku() {
        Book b = controller.cariBuku(txtCari.getText());
        if (b != null) {
            JOptionPane.showMessageDialog(this, "Buku ditemukan: " + b.getJudul());
        } else {
            JOptionPane.showMessageDialog(this, "Buku tidak ditemukan");
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Book b : controller.getAllBuku()) {
            model.addRow(new Object[]{
                    b.getKode(),
                    b.getJudul(),
                    b.getStok()
            });
        }
    }
}
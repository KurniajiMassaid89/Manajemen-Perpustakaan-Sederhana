package controller;

import java.util.ArrayList;

import backend.Book;
import backend.BookRepository;

public class BookController {

    private BookRepository repo = new BookRepository();

    public void tambahBuku(String kode, String judul, int stok) {
        repo.tambah(new Book(kode, judul, stok));
    }

    public void hapusBuku(int index) {
        repo.hapus(index);
    }

    public void pinjamBuku(int index) throws Exception {
        repo.getAll().get(index).pinjam();
    }

    public void kembalikanBuku(int index) {
        repo.getAll().get(index).kembalikan();
    }

    public ArrayList<Book> getAllBuku() {
        return repo.getAll();
    }

    public Book cariBuku(String keyword) {
        return repo.cari(keyword);
    }
}

package backend;

import java.util.ArrayList;

public class BookRepository {

    private ArrayList<Book> books = new ArrayList<>();

    public void tambah(Book b) {
        books.add(b);
    }

    public void hapus(int index) {
        books.remove(index);
    }

    public ArrayList<Book> getAll() {
        return books;
    }

    // cari dari judul ATAU penulis
    public Book cari(String keyword) {
        for (Book b : books) {
            if (
                b.getJudul().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getPenulis().toLowerCase().contains(keyword.toLowerCase())
            ) {
                return b;
            }
        }
        return null;
    }
}
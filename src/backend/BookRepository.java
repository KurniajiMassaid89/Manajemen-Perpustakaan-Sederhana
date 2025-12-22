package backend;

import java.util.ArrayList;

public class BookRepository {
    private ArrayList<Book> books = new ArrayList<>();

    public void tambah(Book book) {
        books.add(book);
    }

    public void hapus(int index) {
        books.remove(index);
    }

    public ArrayList<Book> getAll() {
        return books;
    }

    public Book cari(String keyword) {
        for (Book b : books) {
            if (b.getJudul().toLowerCase().contains(keyword.toLowerCase())) {
                return b;
            }
        }
        return null;
    }
}
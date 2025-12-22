package controller;

import backend.Book;
import backend.BookRepository;

public class BookController {

    private BookRepository repo = new BookRepository();

    public BookRepository getRepo() {
        return repo;
    }

    public void pinjamBuku(Book b) throws Exception {
        b.pinjam();
    }

    public void kembalikanBuku(Book b) {
        b.kembalikan();
    }
}

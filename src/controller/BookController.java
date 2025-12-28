package controller;

import backend.Book;
import backend.BookRepository;

public class BookController {

    public static void addBook(String code, String title, String author, int stock) {
        Book book = new Book(code, title, author, stock);
        BookRepository.addBook(book);
    }

    public static void borrowBook(String name, Book book) throws Exception {
        BookRepository.borrowBook(name, book);
    }

    public static void returnBook(String name, Book book) {
        BookRepository.returnBook(name, book);
    }

    // Toggle availability tidak diperlukan lagi, tapi kalau mau bisa dipakai untuk reset stok
    public static void toggleAvailability(Book book) {
        if (book.getStock() > 0) {
            book.setStock(0); // paksa habis
        } else {
            book.setStock(1); // minimal 1 supaya tersedia
        }
    }
}
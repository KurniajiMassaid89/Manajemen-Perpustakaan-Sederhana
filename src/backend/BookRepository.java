package backend;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public static List<Book> books = new ArrayList<>();
    public static List<String> loanHistory = new ArrayList<>();

    static {
        // 10 buku default
        books.add(new Book("B001", "Algoritma Dasar", "Joko", 3));
        books.add(new Book("B002", "Java Swing", "Sari", 5));
        books.add(new Book("B003", "Struktur Data", "Budi", 4));
        books.add(new Book("B004", "Basis Data", "Ani", 2));
        books.add(new Book("B005", "Pemrograman Web", "Rina", 6));
        books.add(new Book("B006", "Jaringan Komputer", "Andi", 3));
        books.add(new Book("B007", "Sistem Operasi", "Dewi", 2));
        books.add(new Book("B008", "Kecerdasan Buatan", "Agus", 5));
        books.add(new Book("B009", "Machine Learning", "Tono", 4));
        books.add(new Book("B010", "Pemrograman Mobile", "Lina", 3));
    }

    public static void addBook(Book book) {
        books.add(book);
    }

    public static void borrowBook(String name, Book book) throws Exception {
        if (!book.isAvailable() || book.getStock() <= 0) {
            throw new Exception("Buku tidak tersedia untuk dipinjam");
        }
        book.setStock(book.getStock() - 1);
    }

    public static void returnBook(String name, Book book) {
        book.setStock(book.getStock() + 1);
    }
}
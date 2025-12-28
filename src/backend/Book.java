package backend;

public class Book {
    private String code;
    private String title;
    private String author;
    private int stock;

    public Book(String code, String title, String author, int stock) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    // Getter
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }

    // Setter stok
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Status tersedia otomatis tergantung stok
    public boolean isAvailable() {
        return stock > 0;
    }

    // Status dalam bentuk teks (Tersedia / Tidak Tersedia)
    public String getStatusText() {
        return isAvailable() ? "Tersedia" : "Tidak Tersedia";
    }
}
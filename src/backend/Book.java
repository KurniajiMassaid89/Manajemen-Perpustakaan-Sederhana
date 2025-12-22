package backend;

public class Book {
    private String kode;
    private String judul;
    private int stok;

    public Book(String kode, String judul, int stok) {
        this.kode = kode;
        this.judul = judul;
        this.stok = stok;
    }

    public String getKode() {
        return kode;
    }

    public String getJudul() {
        return judul;
    }

    public int getStok() {
        return stok;
    }

    public void pinjam() throws Exception {
        if (stok <= 0) {
            throw new Exception("Stok buku kosong!");
        }
        stok--;
    }

    public void kembalikan() {
        stok++;
    }
}
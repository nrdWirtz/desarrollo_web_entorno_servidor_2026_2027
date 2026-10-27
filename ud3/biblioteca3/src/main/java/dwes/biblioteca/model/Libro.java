package dwes.biblioteca.model;

public class Libro {

    private long id;
    private String titulo;
    private String autor;
    private int paginas;
    private boolean prestado;

    public Libro(long id, String titulo, String autor, int paginas, boolean prestado) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.prestado = prestado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
}

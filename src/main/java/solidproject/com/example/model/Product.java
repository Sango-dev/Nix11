package solidproject.com.example.model;

import lombok.Data;

@Data
public abstract class Product {
    protected long id;
    protected boolean available;
    protected String title;
    protected double price;

    @Override
    public String toString() {
        return "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price;
    }
}
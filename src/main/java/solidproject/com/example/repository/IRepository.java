package solidproject.com.example.repository;

import solidproject.com.example.model.Product;

import java.util.List;

public interface IRepository<T extends Product> {
    T save(T product);
    List<T> getAll();
}

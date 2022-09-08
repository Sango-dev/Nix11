package ua.com.alevel.hw2.dao.invoicedao;

import ua.com.alevel.hw2.model.invoice.Invoice;

import java.util.List;
import java.util.Optional;

public interface IInvoiceDao {
    void save(Invoice invoice);

    Optional<Invoice> findById(String id);

    List<Invoice> getAll();
}

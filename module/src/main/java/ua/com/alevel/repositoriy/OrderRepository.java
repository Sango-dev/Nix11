package ua.com.alevel.repositoriy;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.model.Invoice;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);
    private final List<Invoice> orders;

    private static OrderRepository instance;

    private OrderRepository() {
        orders = new ArrayList<>();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public static OrderRepository newInstance() {
        instance = null;
        return getInstance();
    }

    public void save(Invoice invoice) {
        if (invoice == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null invoice");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            orders.add(invoice);
            LOGGER.info("[{}] [{}] [{}]", invoice.getDateOfCreation(), invoice.getCustomer(), invoice.getProducts());
        }
    }

}

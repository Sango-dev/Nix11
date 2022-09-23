package ua.com.alevel.hw2.dao.invoicedao.jdbc;

import lombok.SneakyThrows;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.dao.invoicedao.IInvoiceDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class InvoiceDao implements IInvoiceDao {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static InvoiceDao instance;

    private InvoiceDao() {
    }

    public static InvoiceDao getInstance() {
        if (instance == null) {
            instance = new InvoiceDao();
        }
        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        String sql = "INSERT INTO \"public\".\"Invoice\" (id, sum, created) VALUES (?, ?, ?);";

        Map<Class<?>, String> queries = new HashMap<>();
        queries.put(Phone.class, "UPDATE \"public\".\"Phone\" SET invoice_id = ? WHERE id = ? AND invoice_id IS NULL;");
        queries.put(Mouse.class, "UPDATE \"public\".\"Mouse\" SET invoice_id = ? WHERE id = ? AND invoice_id IS NULL;");
        queries.put(WashingMachine.class, "UPDATE \"public\".\"Washing_Machine\" SET invoice_id = ? WHERE id = ? AND invoice_id IS NULL;");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setDouble(2, invoice.getSum());
            preparedStatement.setDate(3, new Date(invoice.getDate().getTime()));
            preparedStatement.execute();

            invoice.getProducts().forEach(product -> alterFieldInvoiceId(queries.get(product.getClass()), product, invoice));

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException sqle) {
            try {
                CONNECTION.rollback();
            } finally {
                throw new RuntimeException(sqle);
            }
        }
    }

    @SneakyThrows
    private void alterFieldInvoiceId(String sql, TechProduct product, Invoice invoice) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setString(2, product.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public Optional<Invoice> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Invoice\" WHERE id = ?;";

        Map<Class<?>, String> queries = new HashMap<>();
        queries.put(Phone.class, "SELECT * FROM \"public\".\"Phone\" WHERE invoice_id = ?;");
        queries.put(Mouse.class, "SELECT * FROM \"public\".\"Mouse\" WHERE invoice_id = ?;");
        queries.put(WashingMachine.class, "SELECT * FROM \"public\".\"Washing_Machine\" WHERE invoice_id = ?;");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Invoice invoice = restoreInvoiceFromResultSet(rs);
                invoice.setProducts(restoreProductsFromResultSet(queries, invoice));
                return Optional.of(invoice);
            }
            return Optional.empty();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @SneakyThrows
    private Invoice restoreInvoiceFromResultSet(ResultSet resultSet) {
        String id = resultSet.getString("id");
        double sum = resultSet.getDouble("sum");
        Date created = resultSet.getDate("created");
        return new Invoice(sum, null, created);
    }

    private List<TechProduct> restoreProductsFromResultSet(Map<Class<?>, String> queries, Invoice invoice) {
        List<TechProduct> products = new ArrayList<>();

        queries.forEach((key, value) -> {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(value)) {
                preparedStatement.setString(1, invoice.getId());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (key == Phone.class) {
                        products.add(restorePhoneFromResultSet(resultSet));
                    }
                    if (key == Mouse.class) {
                        products.add(restoreMouseFromResultSet(resultSet));
                    }
                    if (key == WashingMachine.class) {
                        products.add(restoreWMFromResultSet(resultSet));
                    }
                }
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        });

        return products;
    }

    @SneakyThrows
    private Phone restorePhoneFromResultSet(ResultSet resultSet) {
        String id = resultSet.getString("id");
        String model = resultSet.getString("model");
        Manufacturer manufacturer = Manufacturer.valueOf(resultSet.getString("manufacturer"));
        int count = resultSet.getInt("count");
        double price = resultSet.getDouble("price");
        int coreNumbers = resultSet.getInt("core_numbers");
        int batteryPower = resultSet.getInt("battery_power");

        return new Phone(id, model, manufacturer, count, price, coreNumbers, batteryPower);
    }

    @SneakyThrows
    private Mouse restoreMouseFromResultSet(ResultSet resultSet) {
        String id = resultSet.getString("id");
        String model = resultSet.getString("model");
        Manufacturer manufacturer = Manufacturer.valueOf(resultSet.getString("manufacturer"));
        int count = resultSet.getInt("count");
        double price = resultSet.getDouble("price");
        ConnectionType connectionType = ConnectionType.valueOf(resultSet.getString("connection_type"));
        int dpiAmount = resultSet.getInt("dpi_amount");
        return new Mouse(id, model, manufacturer, count, price, connectionType, dpiAmount);
    }

    @SneakyThrows
    private WashingMachine restoreWMFromResultSet(ResultSet resultSet) {
        String id = resultSet.getString("id");
        String model = resultSet.getString("model");
        Manufacturer manufacturer = Manufacturer.valueOf(resultSet.getString("manufacturer"));
        int count = resultSet.getInt("count");
        double price = resultSet.getDouble("price");
        int turnsNumber = resultSet.getInt("turns_number");
        return new WashingMachine(id, model, manufacturer, count, price, turnsNumber);
    }

    @Override
    public List<Invoice> getAll() {
        String sql = "SELECT * FROM \"public\".\"Invoice\"";

        Map<Class<?>, String> queries = new HashMap<>();
        queries.put(Phone.class, "SELECT * FROM \"public\".\"Phone\" WHERE invoice_id = ?;");
        queries.put(Mouse.class, "SELECT * FROM \"public\".\"Mouse\" WHERE invoice_id = ?;");
        queries.put(WashingMachine.class, "SELECT * FROM \"public\".\"Washing_Machine\" WHERE invoice_id = ?;");

        try (Statement statement = CONNECTION.createStatement()) {
            List<Invoice> invoices = new ArrayList<>();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Invoice invoice = restoreInvoiceFromResultSet(rs);
                invoice.setProducts(restoreProductsFromResultSet(queries, invoice));
                invoices.add(invoice);
            }
            return (!invoices.isEmpty()) ? invoices : Collections.emptyList();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public int getInvoiceAmount() {
        String sql = "SELECT count(*) AS amount FROM \"public\".\"Invoice\";";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("amount");
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Invoice> getInvoicesWhereSumMoreThanPrice(double price) {
        String sql = "SELECT * FROM \"public\".\"Invoice\" WHERE sum > ?;";

        Map<Class<?>, String> queries = new HashMap<>();
        queries.put(Phone.class, "SELECT * FROM \"public\".\"Phone\" WHERE invoice_id = ?;");
        queries.put(Mouse.class, "SELECT * FROM \"public\".\"Mouse\" WHERE invoice_id = ?;");
        queries.put(WashingMachine.class, "SELECT * FROM \"public\".\"Washing_Machine\" WHERE invoice_id = ?;");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            List<Invoice> invoices = new ArrayList<>();

            preparedStatement.setDouble(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = restoreInvoiceFromResultSet(resultSet);
                invoice.setProducts(restoreProductsFromResultSet(queries, invoice));
                invoices.add(invoice);
            }

            return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDate(String id, java.util.Date date) {
        String sql = "UPDATE \"public\".\"Invoice\" SET created = ? WHERE id = ?;";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setDate(1, new Date(date.getTime()));
            preparedStatement.setString(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Double, Integer> groupingBySum() {
        String sql = "SELECT sum, count(id) AS amount FROM \"public\".\"Invoice\" GROUP BY sum;";

        try (Statement statement = CONNECTION.createStatement()) {
            Map<Double, Integer> rez = new HashMap<>();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                rez.put(rs.getDouble("sum"), rs.getInt("amount"));
            }
            return (rez.isEmpty()) ? Collections.emptyMap() : rez;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

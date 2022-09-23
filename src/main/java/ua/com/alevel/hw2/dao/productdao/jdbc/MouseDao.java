package ua.com.alevel.hw2.dao.productdao.jdbc;

import lombok.SneakyThrows;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.product.ConnectionType;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.Mouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MouseDao implements IProductDao<Mouse> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static MouseDao instance;

    private MouseDao() {
    }

    public static MouseDao getInstance() {
        if (instance == null) {
            instance = new MouseDao();
        }
        return instance;
    }

    @Override
    public void save(Mouse product) {
        String sql = """
                INSERT INTO \"public\".\"Mouse\" 
                (id, model, manufacturer, count, price, connection_type, dpi_amount) 
                VALUES (?,?,?,?,?,?,?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(preparedStatement, product, false);
            preparedStatement.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Mouse mouse, boolean flag) {
        if (!flag) {
            statement.setString(1, mouse.getId());
            statement.setString(2, mouse.getModel());
            statement.setString(3, mouse.getManufacturer().name());
            statement.setInt(4, mouse.getCount());
            statement.setDouble(5, mouse.getPrice());
            statement.setString(6, mouse.getConnectionType().name());
            statement.setInt(7, mouse.getDpiAmount());
        } else {
            statement.setString(7, mouse.getId());
            statement.setString(1, mouse.getModel());
            statement.setString(2, mouse.getManufacturer().name());
            statement.setInt(3, mouse.getCount());
            statement.setDouble(4, mouse.getPrice());
            statement.setString(5, mouse.getConnectionType().name());
            statement.setInt(6, mouse.getDpiAmount());
        }
    }

    @Override
    public void saveAll(List<Mouse> products) {
        String sql = """
                INSERT INTO \"public\".\"Mouse\" 
                (id, model, manufacturer, count, price, connection_type, dpi_amount) 
                VALUES (?,?,?,?,?,?,?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Mouse mouse : products) {
                setObjectFields(preparedStatement, mouse, false);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public void update(Mouse product) {
        String sql = """
                UPDATE \"public\".\"Mouse\" 
                SET model = ?, manufacturer = ?, count = ?, price = ?, connection_type = ?, dpi_amount = ? 
                WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(preparedStatement, product, true);
            preparedStatement.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public void delete(String id) {
        String sql = """
                DELETE FROM \"public\".\"Mouse\" WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Mouse> findById(String id) {
        String sql = """
                SELECT * FROM  \"public\".\"Mouse\" WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next()) ? Optional.of(setFieldsToObject(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private Mouse setFieldsToObject(ResultSet resultSet) {
        return new Mouse(
                resultSet.getString("id"),
                resultSet.getString("model"),
                Manufacturer.valueOf(resultSet.getString("manufacturer")),
                resultSet.getInt("count"),
                resultSet.getDouble("price"),
                ConnectionType.valueOf(resultSet.getString("connection_type")),
                resultSet.getInt("dpi_amount"));
    }

    @Override
    public List<Mouse> getAll() {
        String sql = """
                SELECT * FROM \"public\".\"Mouse\";
                """;
        List<Mouse> mice = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                mice.add(setFieldsToObject(rs));
            }
            return (!mice.isEmpty()) ? mice : Collections.emptyList();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        String sql = """
                SELECT * FROM \"public\".\"Mouse\" WHERE id = ? AND invoice_id IS NULL;
                """;
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next()) ? true : false;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
}

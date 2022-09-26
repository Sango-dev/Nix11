package ua.com.alevel.hw2.dao.productdao.jdbc;

import lombok.SneakyThrows;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.WashingMachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WMDao implements IProductDao<WashingMachine> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static WMDao instance;

    private WMDao() {
    }

    public static WMDao getInstance() {
        if (instance == null) {
            instance = new WMDao();
        }
        return instance;
    }

    @Override
    public void save(WashingMachine product) {
        String sql = """
                INSERT INTO \"public\".\"Washing_Machine\" 
                (id, model, manufacturer, count, price, turns_number) 
                VALUES (?,?,?,?,?,?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(preparedStatement, product, false);
            preparedStatement.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final WashingMachine machine, boolean flag) {
        if (!flag) {
            statement.setString(1, machine.getId());
            statement.setString(2, machine.getModel());
            statement.setString(3, machine.getManufacturer().name());
            statement.setInt(4, machine.getCount());
            statement.setDouble(5, machine.getPrice());
            statement.setInt(6, machine.getTurnsNumber());
        } else {
            statement.setString(6, machine.getId());
            statement.setString(1, machine.getModel());
            statement.setString(2, machine.getManufacturer().name());
            statement.setInt(3, machine.getCount());
            statement.setDouble(4, machine.getPrice());
            statement.setInt(5, machine.getTurnsNumber());
        }
    }

    @Override
    public void saveAll(List<WashingMachine> products) {
        String sql = """
                INSERT INTO \"public\".\"Washing_Machine\" 
                (id, model, manufacturer, count, price, turns_number) 
                VALUES (?,?,?,?,?,?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (WashingMachine machine : products) {
                setObjectFields(preparedStatement, machine, false);
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
    public void update(WashingMachine product) {
        String sql = """
                UPDATE \"public\".\"Washing_Machine\" 
                SET model = ?, manufacturer = ?, count = ?, price = ?, turns_number = ? 
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
                DELETE FROM \"public\".\"Washing_Machine\" WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<WashingMachine> findById(String id) {
        String sql = """
                SELECT * FROM  \"public\".\"Washing_Machine\" WHERE id = ?;
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
    private WashingMachine setFieldsToObject(ResultSet resultSet) {
        return new WashingMachine(
                resultSet.getString("id"),
                resultSet.getString("model"),
                Manufacturer.valueOf(resultSet.getString("manufacturer")),
                resultSet.getInt("count"),
                resultSet.getDouble("price"),
                resultSet.getInt("turns_number"));
    }

    @Override
    public List<WashingMachine> getAll() {
        String sql = """
                SELECT * FROM \"public\".\"Washing_Machine\";
                """;
        List<WashingMachine> machines = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                machines.add(setFieldsToObject(rs));
            }
            return (!machines.isEmpty()) ? machines : Collections.emptyList();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        String sql = """
                SELECT * FROM \"public\".\"Washing_Machine\" WHERE id = ? AND invoice_id IS NULL;
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

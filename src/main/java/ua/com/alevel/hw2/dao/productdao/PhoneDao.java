package ua.com.alevel.hw2.dao.productdao;

import lombok.SneakyThrows;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PhoneDao implements IProductDao<Phone> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static PhoneDao instance;

    private PhoneDao() {
    }

    public static PhoneDao getInstance() {
        if (instance == null) {
            instance = new PhoneDao();
        }
        return instance;
    }

    @Override
    public void save(Phone product) {
        String sql = """
                INSERT INTO \"public\".\"Phone\" 
                (id, model, manufacturer, count, price, core_numbers, battery_power) 
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
    private void setObjectFields(final PreparedStatement statement, final Phone phone, boolean flag) {
        if (!flag) {
            statement.setString(1, phone.getId());
            statement.setString(2, phone.getModel());
            statement.setString(3, phone.getManufacturer().name());
            statement.setInt(4, phone.getCount());
            statement.setDouble(5, phone.getPrice());
            statement.setInt(6, phone.getCoreNumbers());
            statement.setInt(7, phone.getBatteryPower());
        } else {
            statement.setString(7, phone.getId());
            statement.setString(1, phone.getModel());
            statement.setString(2, phone.getManufacturer().name());
            statement.setInt(3, phone.getCount());
            statement.setDouble(4, phone.getPrice());
            statement.setInt(5, phone.getCoreNumbers());
            statement.setInt(6, phone.getBatteryPower());
        }
    }

    @Override
    public void saveAll(List<Phone> products) {
        String sql = """
                INSERT INTO \"public\".\"Phone\" 
                (id, model, manufacturer, count, price, core_numbers, battery_power) 
                VALUES (?,?,?,?,?,?,?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Phone phone : products) {
                setObjectFields(preparedStatement, phone, false);
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
    public boolean update(Phone product) {
        String sql = """
                UPDATE \"public\".\"Phone\" 
                SET model = ?, manufacturer = ?, count = ?, price = ?, core_numbers = ?, battery_power = ? 
                WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(preparedStatement, product, true);
            return preparedStatement.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = """
                DELETE FROM \"public\".\"Phone\" WHERE id = ?;
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Phone> findById(String id) {
        String sql = """
                SELECT * FROM  \"public\".\"Phone\" WHERE id = ?;
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
    private Phone setFieldsToObject(ResultSet resultSet) {
        return new Phone(
                resultSet.getString("id"),
                resultSet.getString("model"),
                Manufacturer.valueOf(resultSet.getString("manufacturer")),
                resultSet.getInt("count"),
                resultSet.getDouble("price"),
                resultSet.getInt("core_numbers"),
                resultSet.getInt("battery_power"));
    }


    @Override
    public List<Phone> getAll() {
        String sql = """
                SELECT * FROM \"public\".\"Phone\";
                """;
        List<Phone> phones = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                phones.add(setFieldsToObject(rs));
            }
            return (!phones.isEmpty()) ? phones : Collections.emptyList();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        String sql = """
                SELECT * FROM \"public\".\"Phone\" WHERE id = ? AND invoice_id IS NULL;
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

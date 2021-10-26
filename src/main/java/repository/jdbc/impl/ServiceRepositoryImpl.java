package repository.jdbc.impl;

import entity.jdbc.Service;
import repository.jdbc.ServiceRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static properties.columnNames.Names.*;
import static properties.queries.ServiceQ.*;

public class ServiceRepositoryImpl implements ServiceRepository {

    DataSource dataSource;

    public ServiceRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Service create(Service service) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_SERVICE, Statement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, service.getName());
            stm.setDouble(2, service.getPrice());
            stm.executeUpdate();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return readById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Service readById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(SELECT_BY_ID)) {

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                return (new Service(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getDouble(PRICE_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Service> readAll() {
        List<Service> services = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()) {

            ResultSet resultSet = stm.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                services.add(new Service(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getDouble(PRICE_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return services;
    }

    @Override
    public int updateById(int id, Service service) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_BY_ID)) {

            stm.setString(1, service.getName());
            stm.setDouble(2, service.getPrice());
            stm.setInt(3, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(int id) {

        new RecordRepositoryImpl(dataSource).deleteByServiceId(id);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_BY_ID)) {

            stm.setInt(1, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}

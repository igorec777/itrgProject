package repository.impl;

import entity.Record;
import repository.RecordRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static properties.columnNames.Names.*;
import static properties.queries.RecordQ.*;


public class RecordRepositoryImpl implements RecordRepository {

    private final DataSource dataSource;

    public RecordRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Record> readAll() {
        List<Record> records = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()) {

            ResultSet resultSet = stm.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                records.add(new Record(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getDate(DATE_COLUMN),
                        resultSet.getTime(START_TIME_COLUMN),
                        resultSet.getTime(END_TIME_COLUMN),
                        new PersonRepositoryImpl(dataSource).readById(resultSet.getInt(CLIENT_ID_COLUMN)),
                        new PersonRepositoryImpl(dataSource).readById(resultSet.getInt(WORKER_ID_COLUMN)),
                        new ServiceRepositoryImpl(dataSource).readById(resultSet.getInt(SERVICE_ID_COLUMN))
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return records;
    }

    @Override
    public Record readById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(SELECT_BY_ID)) {

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                return (new Record(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getDate(DATE_COLUMN),
                        resultSet.getTime(START_TIME_COLUMN),
                        resultSet.getTime(END_TIME_COLUMN),
                        new PersonRepositoryImpl(dataSource).readById(resultSet.getInt(CLIENT_ID_COLUMN)),
                        new PersonRepositoryImpl(dataSource).readById(resultSet.getInt(WORKER_ID_COLUMN)),
                        new ServiceRepositoryImpl(dataSource).readById(resultSet.getInt(SERVICE_ID_COLUMN))
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(int id, Record record) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_BY_ID)) {

            fillStm(stm, record);
            stm.setInt(7, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Record create(Record record) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_RECORD, Statement.RETURN_GENERATED_KEYS)) {

            fillStm(stm, record);
            stm.executeUpdate();

            ResultSet generatedKeys = stm.getGeneratedKeys();

            if (generatedKeys.next()) {
                return readById(generatedKeys.getInt(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int deleteById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_BY_ID)) {

            stm.setInt(1, id);
            return stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public void deleteByPersonId(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_BY_PERSON_ID)) {

            stm.setInt(1, id);
            stm.setInt(2, id);
            stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteByServiceId(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_BY_SERVICE_ID)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fillStm(PreparedStatement stm, Record record) throws SQLException {
        stm.setDate(1, record.getDate());
        stm.setTime(2, record.getStartTime());
        stm.setTime(3, record.getEndTime());
        stm.setInt(4, record.getClient().getId());
        stm.setInt(5, record.getWorker().getId());
        stm.setInt(6, record.getService().getId());
    }
}

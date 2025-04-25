package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;


    public SqlStorage(String dbUul, String dbUser, String dbPass) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUul, dbUser, dbPass));
    }

    @Override
    public void clear() {
    sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = sqlHelper.connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Resume with uuid " + uuid + " not found");
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), null);
        }
    }


    @Override
    public void update(Resume r) {
        try (Connection conn = sqlHelper.connectionFactory.getConnection()) {
            get(r.getUuid());
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), null);
        }
    }

//    @Override
//    public void save(Resume r) {
//        try (Connection conn = sqlHelper.connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new StorageException(e.getMessage(), null);
//        }
//    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                stmt -> {
                        stmt.setString(1, r.getUuid());
                        stmt.setString(2, r.getFullName());
                        stmt.executeUpdate();
                        return null;
                    }
                );
    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = sqlHelper.connectionFactory.getConnection()) {
            get(uuid);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), null);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (Connection conn = sqlHelper.connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM resume ORDER BY full_name, uuid")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new StorageException("Failed to get all sorted resumes", null);
        }
        return resumes;
    }

    @Override
    public int size() {
        try (Connection conn = sqlHelper.connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e.getMessage(), null);
        }
    }
}

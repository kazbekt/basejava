package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                " LEFT JOIN text_section ts " +
                "        ON r.uuid = ts_resume_uuid " +
                " LEFT JOIN list_section ls " +
                "        ON r.uuid = ls_resume_uuid " +
                "     WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(rs, r);
                addTextSection(rs, r);
                addListSection(rs, r);
            } while (rs.next());

            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact  WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM text_section  WHERE ts_resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM list_section  WHERE ls_resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }

            insertContact(r, conn);
            insertSections(r, conn);
            return null;
        });

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

//    @Override
//    public void save(Resume r) {
//        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.executeUpdate();
//            return null;
//        });
//        for (Map.Entry<Resume.ContactType, String> entry : r.getContacts().entrySet()) {
//            sqlHelper.execute("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", ps -> {
//                ps.setString(1, r.getUuid());
//                ps.setString(2, entry.getKey().name());
//                ps.setString(3, entry.getValue());
//                ps.executeUpdate();
//                return null;
//            });
//        }
//    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.executeUpdate();
            }
            insertContact(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        return sqlHelper.execute("SELECT * FROM resume r ORDER BY full_name,uuid", ps -> {
//            ResultSet rs = ps.executeQuery();
//            List<Resume> resumes = new ArrayList<>();
//            while (rs.next()) {
//                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
//            }
//            return resumes;
//        });
//    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                "   SELECT * FROM resume r\n" +
                "LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                "LEFT JOIN text_section ts ON r.uuid = ts_resume_uuid\n" +
                "LEFT JOIN list_section ls ON r.uuid = ls_resume_uuid\n" +
                "ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                if (!resumeMap.containsKey(uuid)) {
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    resumeMap.put(uuid, r);
                }
                addContact(rs, resumeMap.get(uuid));
                addTextSection(rs, resumeMap.get(uuid));
                addListSection(rs, resumeMap.get(uuid));
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private static void insertContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<Resume.ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume r, Connection conn) throws SQLException {
        for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
            Section section = entry.getValue();
            if (section instanceof TextSection) {
                insertTextSection(r, entry.getKey(), (TextSection) section, conn);
            } else if (section instanceof ListSection) {
                insertListSection(r, entry.getKey(), (ListSection) section, conn);
            }
        }
    }

    private static void insertTextSection(Resume r, SectionType type, TextSection section, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO text_section (ts_resume_uuid, ts_type, ts_value) VALUES (?, ?, ?)")) {
            {
                ps.setString(1, r.getUuid());
                ps.setString(2, type.name());
                ps.setString(3, section.getContent());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static void insertListSection(Resume r, SectionType type, ListSection section, Connection conn) throws SQLException {
        String joinedItems = String.join("\n", section.getItems());
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO list_section (ls_resume_uuid, ls_type, ls_value) VALUES (?, ?, ?)")) {
            {
                ps.setString(1, r.getUuid());
                ps.setString(2, type.name());
                ps.setString(3, joinedItems);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        Resume.ContactType type = Resume.ContactType.valueOf(rs.getString("type"));
        r.addContact(type, value);
    }

    private static void addTextSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("ts_value");
        SectionType type = SectionType.valueOf(rs.getString("ts_type"));
        r.addSection(type, new TextSection(value));
    }

    private static void addListSection(ResultSet rs, Resume r) throws SQLException {
        String joinedItems = rs.getString("ls_value");
        SectionType type = SectionType.valueOf(rs.getString("ls_type"));
        List<String> items = Arrays.asList(joinedItems.split("\n"));
        r.addSection(type, new ListSection(items));
    }
}
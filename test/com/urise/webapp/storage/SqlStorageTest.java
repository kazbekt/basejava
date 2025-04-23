package com.urise.webapp.storage;

import com.urise.webapp.Config;
import org.junit.BeforeClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword()
        ));
    }

    @BeforeClass
    public static void initDb() {
        try (Connection conn = DriverManager.getConnection(
                Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword());
             Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS contact");
            st.execute("DROP TABLE IF EXISTS resume");

            String sql = new String(Files.readAllBytes(Paths.get("config/init_db.sql")));
            for (String s : sql.split(";")) {
                if (!s.trim().isEmpty()) {
                    st.execute(s.trim());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при инициализации базы данных", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void get() {
        super.get();
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void save() {
        super.save();
    }

    @Override
    public void getAllSorted() {
        super.getAllSorted();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void size() {
        super.size();
    }

    @Override
    public void setUp() {
        super.setUp();
    }
}
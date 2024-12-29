package com.strr.shardingsphere;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
class ShardingsphereApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    void test() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select id, test_key, value from my_demo where id = 11")) {
                while (rs.next()) {
                    System.out.printf("id: %d, test_key: %s, value: %s\n", rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

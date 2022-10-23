package com.jetbrains.marco;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.java.Log;

import javax.sql.DataSource;
import java.sql.*;

@Log
public class JdbcTutorial {

    private static DataSource createDataSource() {
        // Hikari CP
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql'");
        return ds;
    }

    public static void main(String[] args) {

        log.info("Started   ,....");

        PreparedStatement ps;
        ResultSet rs;

        DataSource dataSource = createDataSource();

        try {

            Connection connection = dataSource.getConnection();

            log.info("Connection is = " + connection.isValid(0));

            // CRUD
            // select
            ps = connection.prepareStatement("select * from users where name = ?");
            ps.setString(1, "Marco");
            rs = ps.executeQuery();
            while (rs.next()) {
                log.info(rs.getInt("id") + " " + rs.getString("name"));
            }

            // insert
            ps = connection.prepareStatement("insert into users (name) values (?)");
            ps.setString(1, "Paul");
            int insertCount = ps.executeUpdate();
            ps = connection.prepareStatement("select * from users");
            rs = ps.executeQuery();
            while (rs.next()) {
                log.info(rs.getInt("id") + " " + rs.getString("name"));
            }

            // update
            ps = connection.prepareStatement("Update users set name = ? where name = ?");
            ps.setString(1, "Bill");
            ps.setString(2, "Paul");
            int updateCount = ps.executeUpdate();

            ps = connection.prepareStatement("select * from users");
            rs = ps.executeQuery();
            while (rs.next()) {
                log.info(rs.getInt("id") + " " + rs.getString("name"));
            }

            // delete
            ps = connection.prepareStatement("delete from users where name = ?");
            ps.setString(1, "Bill");
            int deleteCount = ps.executeUpdate();
            ps = connection.prepareStatement("select * from users");
            rs = ps.executeQuery();
            while (rs.next()) {
                log.info(rs.getInt("id") + " " + rs.getString("name"));
            }

            log.info("Inserted " + insertCount);
            log.info("Updated  " + updateCount);
            log.info("Deleted  " + deleteCount);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        log.info("Ended....");

    }
}

package com.example.sa_vendingmachine_app.Model.DAO;

import com.example.sa_vendingmachine_app.Model.Entity.User;
import com.example.sa_vendingmachine_app.Model.JDBC.ExecuteSQL;
import com.example.sa_vendingmachine_app.Model.JDBC.SQLExecuteTypeEnum;

import java.sql.ResultSet;

public class UserDAO {

    private ExecuteSQL executeSQL = new ExecuteSQL();

    public UserDAO() {}

    public ResultSet getUserByToken(String token) {
        String sql = String.format(
                "SELECT * FROM vending_machine.user WHERE id_token = '%s';", token
        );

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.QUERY);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }

    // TODO: 主題 Easy Buy

    public ResultSet saveUser(User user) {
        String sql = String.format(
                "INSERT INTO vending_machine.user (id_token, permission) VALUES ('%s', %s);",
                user.getToken(), user.getPermission()
        );

        executeSQL.setSql(sql);
        executeSQL.setType(SQLExecuteTypeEnum.UPDATE);
        executeSQL.execute();

        return executeSQL.getResultSet();
    }


}

package Utils;

import Manager.UserManager;
import data.ConfigVault;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import structure.user.User;

import java.sql.*;
import java.util.UUID;

public class MySQL {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    String host = ConfigVault.HOST;
    String userName = ConfigVault.USERNAME;
    String password = ConfigVault.PASSWORD;
    String port = ConfigVault.PORT;
    String database = ConfigVault.DATABASE;

    public void connectToBase() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, userName, password);
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connect.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void createTable() {
        try {
            preparedStatement = connect.prepareStatement("CREATE TABLE");
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void getPlots() {
        try {
            preparedStatement = connect.prepareStatement("");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultSet.getString("");
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void getUsers() {
        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM USERS" +
                    ";");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nick");

                Player player = Bukkit.getPlayer(name);

                UserManager userManager = new UserManager();
                assert player != null;
                userManager.CreateUsers(player.getUniqueId(), name, id);
            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void createPlot() {
        try {
            MySQL mySQL = new MySQL();
            mySQL.connectToBase();

            preparedStatement = connect.prepareStatement("INSERT INTO plots(id_owner,center_x,center_z,members,flags) VALUES (?,?,?,?,?)");

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void createUser(UUID uuid, String nickname) {
        try {
            preparedStatement = connect.prepareStatement("INSERT INTO USERS(UUID,NICK) VALUES ('" + uuid + "','" + nickname + "';");
            preparedStatement.execute();


            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


}

package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersTable extends DataTable implements ResultFromTable{
    public UsersTable (Statement stmt, DatabaseHandler mdbc) {
        super(stmt, mdbc);
    }

    public ResultSet getResultFromTable(String table) {
        ResultSet rs = null;
        try
        {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public String checkUserInDB(String username)
    {
        String var7 = "success";
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        try {
            while (rs.next()) {
                String Username = rs.getString(Const.USERS_USERNAME);
                if (Username.equals(username))
                {
                    var7 = "fail";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return var7;
    }

    public String isBlocked(String username)
    {
        String isbl = "no";
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        try {
            while (rs.next()){
                String usn = rs.getString(Const.USERS_USERNAME);
                if (usn.equals(username))
                {
                    String ib = rs.getString(Const.USERS_IB);
                    if (ib.equals("yes")) isbl = "yes";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isbl;
    }

    public String CheckLoginClient(String username, String password)
    {
        String var7 = "";
        int i = 0;
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);

        try {
            while (rs.next())
            {
                String tableLogin = rs.getString(Const.USERS_USERNAME);
                String tablePassword = rs.getString(Const.USERS_PASSWORD);

                if(tableLogin.equals(username)&&tablePassword.equals(password))
                {
                    int id = getIdUser(username);
                    ResultSet resultSet = this.getResultFromTable(Const.CLIENT_TABLE);
                    while (resultSet.next())
                    {
                        int Cid = resultSet.getInt(Const.CLIENT_ID);
                        if (Cid == id)
                        {
                            var7 = "successClient";
                            i++;
                        }
                    }
                }
            }if(i == 0)var7 = "fail";
        }catch (Exception var12)
        {
        } finally {
            this.mdbc.close(rs);
        }
        return var7;
    }

    public String CheckLoginAdmin(String username, String password)
    {
        String var7 = "";
        int i = 0;
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);

        try {
            while (rs.next())
            {
                String tableLogin = rs.getString(Const.USERS_USERNAME);
                String tablePassword = rs.getString(Const.USERS_PASSWORD);

                if(tableLogin.equals(username)&&tablePassword.equals(password))
                {
                    int id = getIdUser(username);
                    ResultSet resultSet = this.getResultFromTable(Const.ADMIN_TABLE);
                    while (resultSet.next())
                    {
                        int Cid = resultSet.getInt(Const.ADMIN_ID);
                        if (Cid == id)
                        {
                            var7 = "successAdmin";
                            i++;
                        }
                    }
                }
            }if(i == 0)var7 = "fail";
        }catch (Exception var12)
        {
        } finally {
            this.mdbc.close(rs);
        }
        return var7;

    }

    public void AddClient(String name, String surname, String sex, String country, String username, String password)
    {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" +
                "VALUES (" + this.quotate(username) + "," + this.quotate(password) + ")";

        try {
            this.stmt.executeUpdate(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int id = getIdUser(username);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.CLIENT_TABLE + "(" + Const.CLIENT_ID + "," +
                    Const.CLIENT_NAME + "," + Const.CLIENT_SURNAME + "," +
                    Const.CLIENT_COUNTRY + "," + Const.CLIENT_SEX + ")" +
                    "VALUES (" + id + "," + this.quotate(name) + "," + this.quotate(surname) + "," + this.quotate(country) + "," + this.quotate(sex) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getIdUser(String username)
    {
        ResultSet resultSet;
        int id = -1;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " LIKE '" + username + "';");
            while (resultSet.next())
                id = resultSet.getInt(Const.USERS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }

    public boolean addAdmin (String username, String password)
    {
        try {
            ResultSet resultSet;
            int id = -1;
            resultSet = stmt.executeQuery("SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_USERNAME + " = '" + username + "');");
            while (resultSet.next()) {
                id = resultSet.getInt(Const.USERS_ID);
                break;
            }

            if(id != -1)
                return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            stmt.executeUpdate("INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES (" + this.quotate(username) + "," + this.quotate(password) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String idu = String.valueOf(getIdUser(username));
        try {
            stmt.executeUpdate("INSERT INTO " + Const.ADMIN_TABLE + "(" + Const.ADMIN_ID + "," + Const.ADMIN_STATUS + ")" + "VALUES (" + this.quotate(idu) + "," + this.quotate("admin") + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteAdmin (String username)
    {
        String id = String.valueOf(getIdUser(username));
        if(id.equals("-1"))
            return false;
        try {
            stmt.executeUpdate("DELETE FROM " + Const.ADMIN_TABLE + " WHERE (" + Const.ADMIN_ID + " LIKE '" + id + "');");
            stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_ID + " LIKE '" + id + "');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean blockUser (String username)
    {
        String bl = "yes";
        String id = String.valueOf(getIdUser(username));
        if(id.equals("-1"))
            return false;
        try {
            stmt.executeUpdate("UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_IB + " = '" + bl + "' WHERE (" + Const.USERS_USERNAME + " = '" + username + "');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package Server;

import java.sql.Statement;

public abstract class DataTable {
    protected Statement stmt;
    protected DatabaseHandler mdbc;

    public DataTable(Statement stmt, DatabaseHandler mdbc) {
        this.stmt = stmt;
        this.mdbc = mdbc;
    }

    public String quotate(String content) {
        return "'" + content + "'";
    }
}

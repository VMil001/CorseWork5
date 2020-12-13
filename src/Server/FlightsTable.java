package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class FlightsTable extends DataTable implements ResultFromTable{
    public FlightsTable (Statement stmt, DatabaseHandler mdbc) {
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

    public void saveTicketToFile(String airport, String date, String outtime, String intime, String fullprice)
    {
        String text = "Аэропорт прибытия: " + airport + " \r\n";
        save(false, text);
        String text1 = "Дата вылета: " + date + " \r\n";
        save(true, text1);
        String text2 = "Время вылета:" + outtime + " \r\n";
        save(true, text2);
        String text3 = "Время прибытия:" + intime + " \r\n";
        save(true, text3);
        String text4 = "Цена:" + fullprice;
        save(true, text4);
    }

    public void save (Boolean bool, String text)
    {
        try(FileOutputStream fos=new FileOutputStream("ticket.txt", bool))
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteUserData(String idud)
    {
        int IDUD = Integer.parseInt(idud);
        try {
            stmt.executeUpdate("DELETE FROM " + Const.USERDATA_TABLE + " WHERE (" + Const.USERDATA_IDUD + " = '" + IDUD + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteFl(String idmd)
    {
        try {
            int id = -1;
            ResultSet resultSet;
            resultSet = stmt.executeQuery("SELECT " + Const.MAINDATA_IDMD+ " FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_IDMD + " = '" + idmd + "');");
            while (resultSet.next()) {
                id = resultSet.getInt(Const.MAINDATA_IDMD);
                break;
            }

            if(id == -1)
                return false;

            stmt.executeUpdate("DELETE FROM " + Const.FLIGHT_TABLE + " WHERE (" + Const.FL_IDMD + " LIKE '" + idmd + "');");
            stmt.executeUpdate("DELETE FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_IDMD + " = '" + idmd + "');");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addInfoToUserData(String age, String laggage, String userName)
    {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.USERDATA_TABLE + "(" + Const.USERDATA_IDU + "," + Const.USERDATA_A + "," + Const.USERDATA_l + ")" + "VALUES (" + getIdUser(userName) + "," + this.quotate(age) + "," + this.quotate(laggage) + ");" );
            String lprice = addLaPrice(laggage);
            stmt.executeUpdate("INSERT INTO " + Const.LA_TABLE + "(" + Const.LA_IDUD + "," + Const.LA_PRICE + ")" + "VALUES (" + getIDUD(getIdUser(userName)) + "," + this.quotate(lprice) + ");" );
            String asale = addSale(age);
            System.out.println("Debug2 " + asale);
            stmt.executeUpdate("INSERT INTO " + Const.A_TABLE + "(" + Const.A_IDUD + "," + Const.A_SALE + ")" + "VALUES (" + getIDUD(getIdUser(userName)) + "," + this.quotate(asale) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBilet(String airport, String date, String timeOut, String timeIn, String name, String surname)
    {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.BILET_TABLE + "(" + Const.BILET_AIRPORT + "," + Const.BILET_DATE + "," + Const.BILET_TIMEOUT + "," + Const.BILET_TIMEIN + "," + Const.BILET_NAME + "," + Const.BILET_SURNAME +")" + "VALUES (" + this.quotate(airport) + "," + this.quotate(date) + "," + this.quotate(timeOut) + "," + this.quotate(timeIn) + "," + this.quotate(name) + "," + this.quotate(surname) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFl(String airport, String date, String outtime, String intime, String price, String seatsamount, String outairport)
    {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.MAINDATA_TABLE + "(" + Const.MAINDATA_AIRPORT + "," + Const.MAINDATA_DATE + "," + Const.MAINDATA_OUTTIME + "," + Const.MAINDATA_INTIME +  "," + Const.outairport + ")" + "VALUES (" + this.quotate(airport) + "," + this.quotate(date) + "," + this.quotate(outtime) + "," + this.quotate(intime) + "," + this.quotate(outairport) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String idmd = getidmd(airport, date, outtime);
        System.out.println(idmd);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.FLIGHT_TABLE + "(" + Const.FL_PRICE+ "," + Const.FL_IDMD + "," + Const.FL_SEATS + ")" + "VALUES (" + this.quotate(price) + "," + this.quotate(idmd) + "," + this.quotate(seatsamount) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getidmd (String airport, String date, String outtime)
    {
        ResultSet resultSet;
        String idmd = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.MAINDATA_IDMD + " FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime + "');");
            while (resultSet.next())
                idmd = resultSet.getString(Const.MAINDATA_IDMD);
        } catch (SQLException e) {
            e.printStackTrace();
        }return idmd;
    }

    public void addInfoToTicket(String idud, String fullPrice)
    {
        String idu = getIdU(idud);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.TICKET_TABLE + "(" + Const.TICKET_IDUS + "," + Const.TICKET_FULLPRICE + ")" + "VALUES (" + this.quotate(idu) + "," + this.quotate(fullPrice) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getClientName(String idud)
    {
        String idu = getIdU(idud);
        String name = getClName(idu);
        String surname = getClSurname(idu);
        String ns = name + "," + surname;
        return ns;
    }

    public String getClientNameByLogin(String login)
    {
        String idu = Integer.toString(getIdUser(login));
        String name = getClName(idu);
        String surname = getClSurname(idu);
        String ns = name + " " + surname;
        return ns;
    }


    public String getClName(String idu)
    {
        ResultSet resultSet;
        String name = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.CLIENT_NAME + " FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENT_ID + " LIKE '" + idu + "';");
            while (resultSet.next())
                name = resultSet.getString(Const.CLIENT_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }return name;
    }

    public String getClSurname(String idu)
    {
        ResultSet resultSet;
        String surname = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.CLIENT_SURNAME + " FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENT_ID + " LIKE '" + idu + "';");
            while (resultSet.next())
                surname = resultSet.getString(Const.CLIENT_SURNAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }return surname;
    }

    public String getIdU(String idud)
    {
        ResultSet resultSet;
        String id = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDU + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next())
                id = resultSet.getString(Const.USERDATA_IDU);
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }

    public void addIDFtoUD(String id, String username)
    {
        int idu = getIdUser(username);
        int idf = getIDF(id);
        try {
            stmt.executeUpdate("UPDATE " + Const.USERDATA_TABLE + " SET " + Const.USERDATA_IDF + " = '" + idf + "' WHERE (" + Const.USERDATA_IDU + " = '" + idu + "');");}
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addSale (String age)
    {
        String sale = "0";
        if (age.equals("Взpослые(16+ лет)")) sale = "0";
        else if (age.equals("Подpостки(12-15 лет)")) sale = "20";
        else if (age.equals("Дети(2-14 лет)")) sale = "50";
        else if (age.equals("Младенцы(0-2 года)")) sale = "100";
        return sale;
    }

    public String addLaPrice (String laggage)
    {
        String price = "0";
        if (laggage.equals("Pучная кладь")) price = "0";
        else if (laggage.equals("Дополнительная pучная кладь")) price = "10";
        else if (laggage.equals("Основной багаж")) price = "20";
        else if (laggage.equals("Дополнительный основной багаж")) price = "30";
        else if (laggage.equals("Кpупногабаритный багаж")) price = "40";
        return price;
    }

    public int getIdUser(String username)
    {
        ResultSet resultSet;
        int id = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " LIKE '" + username + "';");
            while (resultSet.next())
                id = resultSet.getInt(Const.USERS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }

    public String getIDUserD (String ids)
    {
        ResultSet resultSet;
        String idU = "";
        int idf = getIDF(ids);

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDUD + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDF + " = '" + idf + "';");
            while (resultSet.next())
                idU = resultSet.getString(Const.USERDATA_IDUD);
        } catch (SQLException e) {
            e.printStackTrace();
        }return idU;
    }

    public int getIDUD (int idu)
    {
        ResultSet resultSet;
        int idU = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDUD + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDU + " = '" + idu + "';");
            while (resultSet.next())
                idU = resultSet.getInt(Const.USERDATA_IDUD);
        } catch (SQLException e) {
            e.printStackTrace();
        }return idU;
    }

    public String sendData (String data){
        return data;
    }

    public String senddata (String data, String data1, String data2)
    {
        String fin = data + "," + data1 + "," + data2;
        return fin;
    }

    public void delBilet(String id)
    {
        int IDUD = Integer.parseInt(id);
        try {
            stmt.executeUpdate("DELETE FROM " + Const.BILET_TABLE + " WHERE (" + Const.BILET_ID + " = '" + IDUD + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getBilet(String name)
    {
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        int id = -1;
        String pName = "", pSruname = "";
        String result = "";
        try {
            while (rs.next()) {
                if(name.equals(rs.getString(Const.USERS_USERNAME))) {
                    id = rs.getInt(Const.USERS_ID);
                    break;
                }
            }

            rs = this.getResultFromTable(Const.CLIENT_TABLE);

            while (rs.next()) {
                if(id == rs.getInt(Const.CLIENT_ID)) {
                    pName = rs.getString(Const.CLIENT_NAME);
                    pSruname = rs.getString(Const.CLIENT_SURNAME);
                    break;
                }
            }

            rs = this.getResultFromTable(Const.BILET_TABLE);

            while (rs.next()) {
                if(pName.equals(rs.getString(Const.BILET_NAME)) && pSruname.equals(rs.getString(Const.BILET_SURNAME))){
                    result += rs.getString(Const.BILET_ID) + "," +rs.getString(Const.BILET_AIRPORT) + "," + rs.getString(Const.BILET_DATE) + "," + rs.getString(Const.BILET_TIMEOUT) + "," + rs.getString(Const.BILET_TIMEIN) + ";";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getIDmd (String idf)
    {
        ResultSet resultSet;
        int idmd = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_IDMD + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " LIKE '" + idf + "';");
            while (resultSet.next())
                idmd = resultSet.getInt(Const.FL_IDMD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("idf =" + idf);
        System.out.println("idmd =" + idmd);
        return idmd;
    }

    public int getIDF (String idMD)
    {
        ResultSet resultSet;
        int idf = -1;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_ID + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_IDMD + " LIKE '" + idMD + "';");
            while (resultSet.next())
                idf = resultSet.getInt(Const.FL_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idf;
    }

    public int getLaggagePrice (String idud)
    {
        ResultSet resultSet;
        int lprice = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.LA_PRICE + " FROM " + Const.LA_TABLE + " WHERE " + Const.LA_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next())
                lprice = resultSet.getInt(Const.LA_PRICE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("lprice = " + lprice);
        return lprice;
    }

    public int getSale (String idud)
    {
        ResultSet resultSet;
        int sale = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.A_SALE + " FROM " + Const.A_TABLE + " WHERE " + Const.A_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next())
                sale = resultSet.getInt(Const.A_SALE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("sale = " + sale);
        return sale;
    }

    public int getFlPrice (String idf)
    {
        ResultSet resultSet;
        int price = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_PRICE + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_IDMD + " LIKE '" + idf + "';");
            while (resultSet.next())
                price = resultSet.getInt(Const.FL_PRICE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("flprice = " + price);
        return price;
    }

    public String CalculateFullPrice (String idud, String price, int all)
    {
        String fullprice = "";
        if(all != 1) {
            int laPrice = getLaggagePrice(idud);
            int flPrice = Integer.parseInt(price);
            int sale = getSale(idud) == 0 ? 1 : getSale(idud);
            int full = flPrice - flPrice * sale / 100 + laPrice;
            System.out.println("full price = " + full);
            fullprice = String.valueOf(full);
        }
        else
            fullprice = price;

        return fullprice;
    }

    public String getTicketInfo (String idf)
    {
        String idmd = String.valueOf(idf);
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        try {
            while (rs.next()) {
                String idMD = rs.getString(Const.MAINDATA_IDMD);
                if (idmd.equals(idMD)) {
                    airport = rs.getString(Const.MAINDATA_AIRPORT);
                    date = rs.getString(Const.MAINDATA_DATE);
                    outtime = rs.getString(Const.MAINDATA_OUTTIME);
                    intime = rs.getString(Const.MAINDATA_INTIME);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String tickInfo = airport + "," + date + "," + outtime + "," + intime;
        System.out.println(tickInfo);
        return tickInfo;
    }

    public String getAvFl(String id, String count, int all)
    {
        String[] messParts = id.split(";");
        for (int j = 0; j < Integer.parseInt(count); j++)
        {
            System.out.println("ID полета" + messParts[j]);
        }
        String AvFlData = "";
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        int price = 0;
        String seats = null;
        String transfer = null;
        for (int i = 0; i < Integer.parseInt(count); i++) {
            transfer = "-";
            ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
            String[] ids = null;
            ids = messParts[i].split("-");
            try {
                while (rs.next()) {
                    String idmd = rs.getString(Const.MAINDATA_IDMD);

                    if (idmd.equals(ids[0])) {
                        airport = rs.getString(Const.MAINDATA_AIRPORT);
                        date = rs.getString(Const.MAINDATA_DATE);
                        if(ids.length == 1)
                            outtime = rs.getString(Const.MAINDATA_OUTTIME);


                        intime = rs.getString(Const.MAINDATA_INTIME);
                        if(all == 1)
                            transfer = rs.getString(Const.outairport);
                    }
                    else if(ids.length > 1 && idmd.equals(ids[1])) {
                        outtime = rs.getString(Const.MAINDATA_OUTTIME);
                        transfer = rs.getString(Const.MAINDATA_AIRPORT);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet rs1 = this.getResultFromTable(Const.FLIGHT_TABLE);
            try {
                while (rs1.next()) {
                    String idMD = rs1.getString(Const.FL_IDMD);
                    if (idMD.equals(ids[0])) {
                        price += Integer.parseInt(rs1.getString(Const.FL_PRICE));
                        seats = rs1.getString(Const.FL_SEATS);
                    }
                    else if(ids.length > 1 && idMD.equals(ids[1]))
                    {
                        price += Integer.parseInt(rs1.getString(Const.FL_PRICE));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            AvFlData +=ids[0] + "," + airport + "," + date + "," + outtime + "," + intime + "," + seats + "," + price + "," + transfer + ";";
        }
        System.out.println("На функции GetFlights" + AvFlData);
        return AvFlData;
    }

    public int getSeatsAmount(String id)
    {
        ResultSet resultSet;
        int seats = 0;
        int ID = Integer.parseInt(id);

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_SEATS + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_IDMD + " = '" + ID + "';");
            while (resultSet.next())
                seats = resultSet.getInt(Const.FL_SEATS);
        } catch (SQLException e) {
            e.printStackTrace();
        }return seats;
    }

    public void CountFreeSeatsAmount(String id)
    {
        int seats = getSeatsAmount(id) - 1;
        System.out.println("Ta " + id);
        try {
            stmt.executeUpdate("UPDATE " + Const.FLIGHT_TABLE + " SET " + Const.FL_SEATS + " = '" + seats + "' WHERE (" + Const.FL_IDMD + " LIKE '" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String сheckAvFl(String airport, String date)
    {
        String hasAvFl = "fail";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int i = 0;
        try{
            while (rs.next())
            {
                String Airport = rs.getString(Const.MAINDATA_AIRPORT);
                String Date = rs.getString(Const.MAINDATA_DATE);

                System.out.println(Airport);

                if (Airport.equals(airport)&&Date.equals(date))
                {
                    i++;
                }
            }
            if(i>0) hasAvFl = "success";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Считывание данных с checkFl " + hasAvFl);
        return hasAvFl;
    }

    public String getAllFlID ()
    {
        String mesToCl = "";
        String mesToCl1 = "";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int count = 0;
        try {
            while (rs.next())
            {
                String mID = rs.getString(Const.MAINDATA_IDMD);
                mesToCl += mID + ";";
                count ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count==0)
            mesToCl1 = "fail";
        else
            mesToCl1 = String.valueOf(count) + "," + mesToCl;
        return mesToCl1;
    }

    public String getAvFlID (String airport, String date, String outairport)
    {
        String mesToCl = "";
        String mesToCl1 = "";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int count = 0;
        try {
            while (rs.next())
            {
                String mID = rs.getString(Const.MAINDATA_IDMD);
                String Airport = rs.getString(Const.MAINDATA_AIRPORT);
                String Date = rs.getString(Const.MAINDATA_DATE);
                String Outairport = rs.getString(Const.outairport);
                String outtime = rs.getString(Const.MAINDATA_OUTTIME);

                if(Airport.equals(airport)&&Date.equals(date)&&Outairport.equals(outairport))
                {
                    mesToCl += mID + ";";
                    count ++;
                }
                else if(Airport.equals(airport)&&Date.equals(date))
                {
                    Statement nSt = stmt.getConnection().createStatement();
                    ResultSet rSet = nSt.executeQuery("SELECT * FROM " + Const.MAINDATA_TABLE);
                    while(rSet.next())
                    {
                        String newAirport = rSet.getString(Const.MAINDATA_AIRPORT);
                        String newDate = rSet.getString(Const.MAINDATA_DATE);
                        String newOutairport = rSet.getString(Const.outairport);
                        String intime = rSet.getString(Const.MAINDATA_INTIME);
                        if(newAirport.equals(Outairport)&&newDate.equals(date)&&newOutairport.equals(outairport))
                        {
                            String inTime[] = intime.split(":");
                            String outTime[] = outtime.split(":");

                            if((inTime[0].charAt(0) == 0? Integer.parseInt(inTime[0].substring(1)) : Integer.parseInt(inTime[0])) < (outTime[0].charAt(0) == 0? Integer.parseInt(outTime[0].substring(1)) : Integer.parseInt(outTime[0]))){
                                String ID = rSet.getString(Const.MAINDATA_IDMD);
                                mesToCl += mID+ "-"+ ID + ";";
                                count ++;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count==0)
            mesToCl1 = "fail";
        else
            mesToCl1 = String.valueOf(count) + "," + mesToCl;
        System.out.println("ID полетов " + mesToCl1);
        return mesToCl1;
    }

    public String getDataForPie()
    {
        String data = "";
        ResultSet resultSet = this.getResultFromTable(Const.MAINDATA_TABLE);
        int countmorn = 0;
        int countday = 0;
        int counteve = 0;
        int countni = 0;
        try {
            while (resultSet.next())
            {
                String outtime = resultSet.getString(Const.MAINDATA_DATE);
                String[] time = outtime.split("\\.");
                System.out.println(outtime + " " + time[0] + " " + time[1]);
                int outTime = Integer.parseInt(time[1]);
                if (outTime >= 3 && outTime < 6) countni++; // Весна
                else if (outTime >= 6 && outTime < 9) countmorn++; // Лето
                else if (outTime >= 9 && outTime < 12) countday++; // Осень
                else if (outTime == 12 || (outTime >= 1 && outTime < 3)) counteve++; // Зима
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String CM = String.valueOf(countmorn);
        String CD = String.valueOf(countday);
        String CE  = String.valueOf(counteve);
        String CN = String.valueOf(countni);
        data = CN + "," + CM + "," + CD + "," + CE;
        return data;
    }
}

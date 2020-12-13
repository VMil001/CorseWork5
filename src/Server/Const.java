package Server;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String USERS_ID = "idUsers";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_IB = "isBlocked";

    public static final String ADMIN_TABLE = "admin";
    public static final String ADMIN_ID = "idadmin";
    public static final String ADMIN_STATUS = "status";

    public static final String CLIENT_TABLE = "client";
    public static final String CLIENT_ID = "idclient";
    public static final String CLIENT_NAME = "name";
    public static final String CLIENT_SURNAME = "surname";
    public static final String CLIENT_SEX = "sex";
    public static final String CLIENT_COUNTRY = "country";

    public static final String FLIGHT_TABLE = "flight";
    public static final String FL_ID = "idF";
    public static final String FL_IDMD = "idMD";
    public static final String FL_PRICE = "price";
    public static final String FL_SEATS = "seatsAmount";

    public static final String USERDATA_TABLE = "userdata";
    public static final String USERDATA_IDU = "idu";
    public static final String USERDATA_l = "laggage";
    public static final String USERDATA_A = "age";
    public static final String USERDATA_IDUD = "idUD";
    public static final String USERDATA_IDF = "idf";

    public static final String MAINDATA_TABLE = "maindata";
    public static final String MAINDATA_AIRPORT = "airport";
    public static final String MAINDATA_DATE = "date";
    public static final String MAINDATA_IDMD = "idmd";
    public static final String MAINDATA_OUTTIME = "outtime";
    public static final String MAINDATA_INTIME = "intime";
    public static final String outairport = "outairport";

    public static final String BILET_TABLE = "bilet";
    public static final String BILET_ID = "id";
    public static final String BILET_AIRPORT = "airport";
    public static final String BILET_DATE = "date";
    public static final String BILET_TIMEOUT = "timeOut";
    public static final String BILET_TIMEIN = "timeIn";
    public static final String BILET_NAME = "name";
    public static final String BILET_SURNAME = "username";

    public static final String LA_TABLE = "laggage";
    public static final String LA_IDUD = "iduserdata";
    public static final String LA_PRICE = "lprice";

    public static final String A_TABLE = "agesales";
    public static final String A_IDUD = "idud";
    public static final String A_SALE = "sale";

    public static final String TICKET_TABLE = "ticket";
    public static final String TICKET_IDUS = "idus";
    public static final String TICKET_FULLPRICE = "generalPrice";
}

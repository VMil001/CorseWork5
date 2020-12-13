package Client;

public class Cabinet {
    private int id;
    private String Airport;
    private String Date;
    private String timeOut;
    private String timeIn;

    public Cabinet(int id, String airport, String date, String timeOut, String timeIn) {
        this.id = id;
        Airport = airport;
        Date = date;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirport() {
        return Airport;
    }

    public void setAirport(String airport) {
        Airport = airport;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }
}

package Client;

public class History {

    private static History instance;

    private String previousPath;

    private History(){}

    public static History getInstance(){
        if(instance==null){
            instance = new History();
        }
        return instance;
    }

    public void setPreviousPath(String path){
        this.previousPath = path;
    }

    public String getPreviousPath(){
        return this.previousPath;
    }
}

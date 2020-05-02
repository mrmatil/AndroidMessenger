package aib.projektZaliczeniowy.messenger.messagesutils;


public class messagesClass {
    private String author;
    private String message;
    private Long date;
    private String longitude;
    private String latitude;


    public messagesClass(String author, String message, Long date, String longitude, String latitude){
        this.author=author;
        this.message=message;
        this.date=date;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public Long getDate() {
        return date;
    }

    public String getLognitude() { return longitude; }

    public String getLangitude() { return latitude; }
}

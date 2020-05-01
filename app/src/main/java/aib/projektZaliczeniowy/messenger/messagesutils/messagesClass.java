package aib.projektZaliczeniowy.messenger.messagesutils;


public class messagesClass {
    private String author;
    private String message;
    private Long date;
    private Float longitude;
    private Float latitude;


    public messagesClass(String author, String message, Long date, Float longitude, Float latitude){
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

    public Float getLognitude() { return longitude; }

    public Float getLangitude() { return latitude; }
}

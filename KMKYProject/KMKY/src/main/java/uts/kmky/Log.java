package uts.kmky;

/**
 * Created by FrederikKastrup on 21/09/13.
 */
public class Log {


    private String phonenumber;
    private String type;
    private long date;
    private int incoming;
    private int outgoing;
    private long id;

    //Constructor for incoming sms or call
    public Log( String Phonenumber, String Type, long Date, int Incoming, int Outgoing)
    {

        this.phonenumber = Phonenumber;
        this.type = Type;
        this.date = Date;
        this.incoming = Incoming;
        this.outgoing = Outgoing;
    }


    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumer(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public int getIncoming()
    {
        return incoming;
    }

    public void setIncoming(int incoming)
    {

        this.incoming = incoming;

    }

    public int getOutgoing()
    {
        return outgoing;
    }

    public void setOutgoing (int outgoing)
    {

        this.outgoing = outgoing;
    }
}

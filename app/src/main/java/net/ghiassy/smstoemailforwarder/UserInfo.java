package net.ghiassy.smstoemailforwarder;

public class UserInfo
{
    public static final String TAG = "UserInfo";
    public String ReceiverEmail;

    public UserInfo(String receiverEmail)
    {

        this.ReceiverEmail = receiverEmail;

    }
    public String getReceiverEmail() {
        return ReceiverEmail;
    }
    public void setReceiverEmail(String receiverEmail) {
        ReceiverEmail = receiverEmail;
    }




}

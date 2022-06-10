package Networking.Messages;

import java.nio.ByteBuffer;

public class Message {
    protected byte[] rawMessage;
    protected int header;
    protected int senderID;

    public Message(byte[] rawMessage){
        this.rawMessage = rawMessage;
        this.parse();
    }

    public Message(int header, int senderID){
        this.header = header;
        this.senderID = senderID;
    }

    protected void parse(){
        //decrypt();
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
    }

    public byte[] getRawMessage() {
        return rawMessage;
    }

    public int getHeader() {
        return header;
    }

    public int getSenderID() {
        return senderID;
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(12);
        //length
        buff.putInt(0,12);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        rawMessage = buff.array();
        return rawMessage;
    }

    public void encrypt(){
        for (int i = 0; i < rawMessage.length; i++) {
            rawMessage[i] = (byte) (rawMessage[i] ^ Messages.KEY[i % Messages.KEY.length]);
        }
    }

    public void decrypt(){
        for (int i = 0; i < rawMessage.length; i++) {
            rawMessage[i] = (byte) (rawMessage[i] ^ Messages.KEY[i % Messages.KEY.length]);
        }
    }
}

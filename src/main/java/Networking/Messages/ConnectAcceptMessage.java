package Networking.Messages;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ConnectAcceptMessage extends ParseableMessage implements SendableMessage{
    private int destination;
    private int newUserID;
    private int senderID;
    public ConnectAcceptMessage(int destination, int newUserID, int senderID){
        super(new byte[28]);

        this.swarmID = Messages.NO_SWARM;
        this.userID = Messages.UNAUTHENTICATED;
        this.header = MessageHeader.NEW_CONNECTION_RESPONSE;
        this.destination = destination;
        this.newUserID = newUserID;
        this.senderID = senderID;
    }

    public ConnectAcceptMessage(byte[] rawMessage){
        super(rawMessage);
        this.parse();
    }

    private void parse(){
        System.out.println(Arrays.toString(this.rawMessage));
        swarmID = Messages.getIntFromByteArray(rawMessage,4);
        System.out.println(swarmID);
        userID  = Messages.getIntFromByteArray(rawMessage,8);
        System.out.println(userID);
        header  = Messages.getIntFromByteArray(rawMessage,12);
        System.out.println(header);
        destination = Messages.getIntFromByteArray(rawMessage,16);
        System.out.println(destination);
        newUserID = Messages.getIntFromByteArray(rawMessage,20);
        System.out.println(newUserID);
        senderID = Messages.getIntFromByteArray(rawMessage, 24);
        System.out.println(senderID);
    }

    @Override
    public byte[] getRawMessage() {
        return super.getRawMessage();
    }

    public int getDestination() {
        return destination;
    }

    public int getNewUserID() {
        return newUserID;
    }

    public int getSenderID() {return senderID;}

    @Override
    public byte[] toPacket() {
        ByteBuffer buf = ByteBuffer.allocate(28);
        buf.putInt(0,  28); //length 16
        buf.putInt(4,  this.swarmID);
        buf.putInt(8,  this.userID);
        buf.putInt(12, this.header);
        buf.putInt(16, this.destination);
        buf.putInt(20, this.newUserID);
        buf.putInt(24, this.senderID);
        rawMessage = buf.array();
        return rawMessage;
    }
}

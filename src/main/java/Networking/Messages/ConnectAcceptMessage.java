package Networking.Messages;

import java.nio.ByteBuffer;

public class ConnectAcceptMessage extends ParseableMessage implements SendableMessage{
    private int destination;
    private int newUserID;
    public ConnectAcceptMessage(int destination, int newUserID){
        super(new byte[24]);

        this.swarmID = Messages.NO_SWARM;
        this.userID = Messages.UNAUTHENTICATED;
        this.header = MessageHeader.NEW_CONNECTION_RESPONSE;
        this.destination = destination;
        this.newUserID = newUserID;
    }

    public ConnectAcceptMessage(byte[] rawMessage){
        super(rawMessage);
        parse();
    }

    private void parse(){
        swarmID = Messages.getIntFromByteArray(rawMessage,4);
        userID  = Messages.getIntFromByteArray(rawMessage,8);
        header  = Messages.getIntFromByteArray(rawMessage,12);
        destination = Messages.getIntFromByteArray(rawMessage,16);
        newUserID = Messages.getIntFromByteArray(rawMessage,20);
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

    @Override
    public byte[] toPacket() {
        ByteBuffer buf = ByteBuffer.allocate(24);
        buf.putInt(0,  24); //length 16
        buf.putInt(4,  this.swarmID);
        buf.putInt(8,  this.userID);
        buf.putInt(12, this.header);
        buf.putInt(16, this.destination);
        buf.putInt(20, this.newUserID);
        rawMessage = buf.array();
        return rawMessage;
    }
}

package Networking.Messages;

import java.nio.ByteBuffer;

public class ConnectAcceptMessage extends ParseableMessage implements SendableMessage{
    int destination;
    int newUserID;
    public ConnectAcceptMessage(int destination, int newUserID){
        super(new byte[24]);

        this.swarmID = Messages.NO_SWARM;
        this.userID = Messages.UNAUTHENTICATED;
        this.header = MessageHeader.NEW_CONNECTION_RESPONSE;
        this.destination = destination;
        this.newUserID = newUserID;
    }
    @Override
    public byte[] toPacket() {
        ByteBuffer arr = ByteBuffer.allocate(24);
        arr.putInt(0,  24); //length 16
        arr.putInt(4,  this.swarmID);
        arr.putInt(8,  this.userID);
        arr.putInt(12, this.header);
        arr.putInt(16, this.destination);
        arr.putInt(20, this.newUserID);
        rawMessage = arr.array();
        return rawMessage;
    }
}

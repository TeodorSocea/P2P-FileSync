package Networking.Messages;

import java.nio.ByteBuffer;

public class ConnectMessage extends ParseableMessage implements SendableMessage{
    private int destination;

    public ConnectMessage(byte[] rawMessage){
        super(rawMessage.clone());
        this.parse();
    }
    public ConnectMessage(int destination){
        super(new byte[20]);
        this.destination = destination;
        this.swarmID = Messages.NO_SWARM;
        this.userID = Messages.UNAUTHENTICATED;
        this.header = MessageHeader.NEW_CONNECTION_REQUEST;
    }

    private void parse(){
        swarmID = Messages.getIntFromByteArray(rawMessage,4);
        userID  = Messages.getIntFromByteArray(rawMessage,8);
        header  = Messages.getIntFromByteArray(rawMessage,12);
        destination = Messages.getIntFromByteArray(rawMessage,16);
    }


    @Override
    public byte[] toPacket() {
        ByteBuffer arr = ByteBuffer.allocate(20);
        arr.putInt(0,  20); //length 16
        arr.putInt(4,  Messages.NO_SWARM);
        arr.putInt(8,  Messages.UNAUTHENTICATED);
        arr.putInt(12, MessageHeader.NEW_CONNECTION_REQUEST);
        arr.putInt(16, destination);
        rawMessage = arr.array();
        return rawMessage;
    }

    public int getDestination() {
        return destination;
    }
}

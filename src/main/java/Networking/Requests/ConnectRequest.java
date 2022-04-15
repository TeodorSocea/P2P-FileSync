package Networking.Requests;

import Networking.Messages.MessageHeader;
import Networking.Messages.Messages;

import java.nio.ByteBuffer;

public class ConnectRequest extends AbstractRequest {
    int swarmID;
    public ConnectRequest(Integer swarmID){
        this.swarmID = swarmID;
    }

    @Override
    public byte[] toPacket() {
        ByteBuffer arr = ByteBuffer.allocate(16);
        arr.putInt(0,  16); //length 16
        arr.putInt(4,  Messages.NO_SWARM);
        arr.putInt(8,  Messages.UNAUTHENTICATED);
        arr.putInt(12, MessageHeader.NEW_CONNECTION_REQUEST);
        return arr.array();
    }
}

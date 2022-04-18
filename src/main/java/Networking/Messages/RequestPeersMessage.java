package Networking.Messages;

import java.nio.ByteBuffer;

public class RequestPeersMessage extends ParseableMessage implements SendableMessage{

    public RequestPeersMessage(int swarmID, int userID){
        super(new byte[16]);
        this.swarmID = swarmID;
        this.userID = userID;
        this.header = MessageHeader.PEER_REQUEST;
    }

    public RequestPeersMessage(byte[] rawMessage) {
        super(rawMessage);
        this.parse();
    }


    private void parse(){
        swarmID = Messages.getIntFromByteArray(rawMessage,4);
        userID  = Messages.getIntFromByteArray(rawMessage,8);
        header  = Messages.getIntFromByteArray(rawMessage,12);
    }


    @Override
    public byte[] toPacket() {
        ByteBuffer arr = ByteBuffer.allocate(16);
        arr.putInt(0,  16); //length 16
        arr.putInt(4, this.swarmID);
        arr.putInt(8,  this.userID);
        arr.putInt(12, this.header);
        rawMessage = arr.array();
        return rawMessage;
    }

}

package Networking.Messages;

import java.nio.ByteBuffer;

public class NewPeerMessage extends Message{

    private int swarmID;

    public NewPeerMessage(byte[] rawMessage) {
        super(rawMessage);
    }

    public NewPeerMessage(int header, int senderID, int swarmID) {
        super(header, senderID);
        this.swarmID = swarmID;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        swarmID = Messages.getIntFromByteArray(rawMessage, 12);
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(16);
        //length
        buff.putInt(0,16);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //swarmID
        buff.putInt(12, this.swarmID);
        rawMessage = buff.array();
        encrypt();
        return rawMessage;
    }
}

package Networking.Messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataRequestMessage extends Message{
    private int swarmID;
    private String path;

    public DataRequestMessage(byte[] rawMessage) {
        super(rawMessage);
        parse();
    }

    public DataRequestMessage(int header, int senderID, int swarmID, String path) {
        super(header, senderID);
        this.swarmID = swarmID;
        this.path = path;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public String getPath() {
        return path;
    }

    public void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        swarmID = Messages.getIntFromByteArray(rawMessage, 12);
        path = new String(Arrays.copyOfRange(rawMessage, 16, rawMessage.length), StandardCharsets.UTF_8);
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(16 + path.length());
        //length
        buff.putInt(0,16 + path.length());
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //swarmID
        buff.putInt(12, this.swarmID);
        //path
        buff.put(16, this.path.getBytes(StandardCharsets.UTF_8));
        rawMessage = buff.array();
        //encrypt();
        return rawMessage;
    }
}

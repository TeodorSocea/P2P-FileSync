package Networking.Messages;

import java.nio.ByteBuffer;

public class DataMessage extends Message{

    private int swarmID;
    private int chunkID;
    private byte[] data;

    public DataMessage(byte[] rawMessage) {
        super(rawMessage);
        parse();
    }

    public DataMessage(int header, int senderID, int swarmID, int chunkID, byte[] data) {
        super(header, senderID);
        this.swarmID = swarmID;
        this.chunkID = chunkID;
        this.data = data;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public int getChunkID() {
        return chunkID;
    }

    public byte[] getData() {
        return data;
    }

    public void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        swarmID = Messages.getIntFromByteArray(rawMessage, 12);
        chunkID = Messages.getIntFromByteArray(rawMessage, 16);
        data = new byte[1024];
        for(int i = 0; i < 1024; i++){
            data[i] = Messages.getByteFromByteArray(rawMessage, 20 + i);
        }
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(1044);
        //length
        buff.putInt(0,1044);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //swarmID
        buff.putInt(12, this.swarmID);
        //chunkID
        buff.putInt(16, this.chunkID);
        //data
        for(int i = 0; i < data.length; i++){
            buff.put(20 + i, this.data[i]);
        }
        rawMessage = buff.array();
        //encrypt();
        return rawMessage;
    }
}

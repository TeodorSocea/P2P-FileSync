package Networking.Messages;

import java.nio.ByteBuffer;

public class DataMessageRequest extends Message{
    private int fileID;
    private int chunkID;
    private int swarmID;

    public DataMessageRequest(byte[] rawMessage){
        super(rawMessage);
        this.parse();
    }

    public DataMessageRequest(int senderID, int swarmID, int fileID, int chunkID){
        super(MessageHeader.CHUNK_REQUEST,senderID);
        this.fileID  = fileID;
        this.chunkID = chunkID;
        this.swarmID = swarmID;
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

    public int getSwarmID() {
        return swarmID;
    }

    public int getFileID() {
        return fileID;
    }

    public int getChunkID() {
        return chunkID;
    }

    protected void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);

    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(24);
        //length
        buff.putInt(0,24);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        buff.putInt(12, this.swarmID);
        buff.putInt(16, this.fileID);
        buff.putInt(20, this.chunkID);
        rawMessage = buff.array();
        return rawMessage;
    }
}

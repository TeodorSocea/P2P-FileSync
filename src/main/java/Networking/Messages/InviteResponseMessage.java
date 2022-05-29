package Networking.Messages;

import java.nio.ByteBuffer;

public class InviteResponseMessage extends Message{

    private int swarmID;
    private byte response;

    public InviteResponseMessage(byte[] rawMessage) {
        super(rawMessage);
        parse();
    }

    public InviteResponseMessage(int header, int senderID, int swarmID, boolean response){
        super(header, senderID);

        this.swarmID = swarmID;

        if(response == true){
            this.response = 1;
        }
       else{
           this.response = 0;
        }
    }

    public byte getResponse() {
        return response;
    }

    public int getSwarmID() {
        return swarmID;
    }

    protected void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        swarmID = Messages.getIntFromByteArray(rawMessage, 12);
        response = Messages.getByteFromByteArray(rawMessage, 16);
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(20);
        //length
        buff.putInt(0,20);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //swarmID
        buff.putInt(12, this.swarmID);
        //response
        buff.put(16, this.response);
        rawMessage = buff.array();
        encrypt();
        return rawMessage;
    }

}

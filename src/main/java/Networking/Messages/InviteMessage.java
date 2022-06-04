package Networking.Messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class InviteMessage extends Message{

    private int inviteeID;
    private int swarmID;
    private String swarmName;

    public InviteMessage(int header, int senderID, int inviteeID, int swarmID, String swarmName){
        super(header, senderID);
        this.inviteeID = inviteeID;
        this.swarmID = swarmID;
        this.swarmName = swarmName;
    }

    protected void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        inviteeID = Messages.getIntFromByteArray(rawMessage, 12);
        swarmID = Messages.getIntFromByteArray(rawMessage, 16);
        swarmName = new String(Arrays.copyOfRange(rawMessage, 20, rawMessage.length));
    }

    public InviteMessage(byte[] rawMessage) {
        super(rawMessage);
        parse();
    }

    public int getInviteeID() {
        return inviteeID;
    }

    public int getSwarmID() {
        return swarmID;
    }

    public String getSwarmName() {
        return swarmName;
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(20 + swarmName.length());
        //length
        buff.putInt(0,20 + swarmName.length());
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //inviteeID
        buff.putInt(12, this.inviteeID);
        //swarmID
        buff.putInt(16, this.swarmID);
        //swarmName
        buff.put(20, swarmName.getBytes(StandardCharsets.UTF_8));
        rawMessage = buff.array();
        encrypt();
        return rawMessage;
    }
}

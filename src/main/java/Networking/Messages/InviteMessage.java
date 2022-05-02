package Networking.Messages;

import java.nio.ByteBuffer;

public class InviteMessage extends Message{

    private int inviteeID;
    private int swarmID;

    public InviteMessage(int header, int senderID, int inviteeID, int swarmID){
        super(header, senderID);
        this.inviteeID = inviteeID;
        this.swarmID = swarmID;
    }

    protected void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        inviteeID = Messages.getIntFromByteArray(rawMessage, 12);
        swarmID = Messages.getIntFromByteArray(rawMessage, 16);
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

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(20);
        //length
        buff.putInt(0,20);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //inviteeID
        buff.putInt(12, this.inviteeID);
        //swarmID
        buff.putInt(16, this.swarmID);
        rawMessage = buff.array();
        return rawMessage;
    }
}

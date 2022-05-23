package Networking.Messages;

import Networking.Peer.Peer;

import java.nio.ByteBuffer;

public class SwarmDataMessage extends Message{

    private int swarmID;
    private int peerID;
    private byte[] peerIP;

    public SwarmDataMessage(byte[] rawMessage) {
        super(rawMessage);
        parse();
    }

    public SwarmDataMessage(int header, int senderID, int swarmID, Peer peer) {
        super(header, senderID);
        this.swarmID = swarmID;
        peerID = peer.getPeerID();
        peerIP = peer.getPeerSocket().getInetAddress().getAddress();
    }

    public int getSwarmID() {
        return swarmID;
    }

    public int getPeerID() {
        return peerID;
    }

    public byte[] getPeerIP() {
        return peerIP;
    }

    public void parse(){
        header  = Messages.getIntFromByteArray(rawMessage,4);
        senderID  = Messages.getIntFromByteArray(rawMessage,8);
        swarmID = Messages.getIntFromByteArray(rawMessage, 12);
        peerID = Messages.getIntFromByteArray(rawMessage, 16);
        peerIP = new byte[4];
        peerIP[0] = Messages.getByteFromByteArray(rawMessage,20);
        peerIP[1] = Messages.getByteFromByteArray(rawMessage,21);
        peerIP[2] = Messages.getByteFromByteArray(rawMessage,22);
        peerIP[3] = Messages.getByteFromByteArray(rawMessage,23);
    }

    public byte[] toPacket(){
        ByteBuffer buff = ByteBuffer.allocate(24);
        //length
        buff.putInt(0,24);
        //header
        buff.putInt(4, this.header);
        //senderID
        buff.putInt(8, this.senderID);
        //swarmID
        buff.putInt(12, this.swarmID);
        //peerID
        buff.putInt(16, this.peerID);
        //peerIP
        buff.put(20, peerIP[0]);
        buff.put(21, peerIP[1]);
        buff.put(22, peerIP[2]);
        buff.put(23, peerIP[3]);
        rawMessage = buff.array();
        return rawMessage;
    }
}

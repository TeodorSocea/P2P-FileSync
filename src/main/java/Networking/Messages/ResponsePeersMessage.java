package Networking.Messages;

import Networking.Peer.Peer;

import javax.swing.*;
import java.nio.ByteBuffer;
import java.util.Map;

public class ResponsePeersMessage extends ParseableMessage implements SendableMessage{

    private byte[][] ipsAsArray;
    private int ipsNumber;
    private int[] peerIDs;

    public ResponsePeersMessage(int swarmID, int userID, Map<Integer, Peer> peers){
        super(new byte[16]);

        this.swarmID = swarmID;
        this.userID = userID;
        this.header = MessageHeader.PEER_REQUEST;
        this.ipsNumber = peers.size();
        ipsAsArray = new byte[ipsNumber][4];
        peerIDs = new int[ipsNumber];
        int i = 0;
        for(Map.Entry<Integer, Peer> peer : peers.entrySet()){
            peerIDs[i] = peer.getKey();
            ipsAsArray[i] = peer.getValue().getPeerSocket().getInetAddress().getAddress().clone();
            i++;
        }
    }

    public ResponsePeersMessage(byte[] rawMessage) {
        super(rawMessage);
        this.parse();
    }


    private void parse(){
        swarmID = Messages.getIntFromByteArray(rawMessage,4);
        userID  = Messages.getIntFromByteArray(rawMessage,8);
        header  = Messages.getIntFromByteArray(rawMessage,12);
        ipsNumber = Messages.getIntFromByteArray(rawMessage, 16);

        ipsAsArray = new byte[ipsNumber][4];
        peerIDs = new int[ipsNumber];

        for(int i = 0; i < ipsNumber; i++){
            peerIDs[i] = Messages.getIntFromByteArray(rawMessage, 20+8*i);
            ipsAsArray[i][0] = Messages.getByteFromByteArray(rawMessage, 20 + 8*i + 4);
            ipsAsArray[i][1] = Messages.getByteFromByteArray(rawMessage, 20 + 8*i + 5);
            ipsAsArray[i][2] = Messages.getByteFromByteArray(rawMessage, 20 + 8*i + 6);
            ipsAsArray[i][3] = Messages.getByteFromByteArray(rawMessage, 20 + 8*i + 7);
        }
    }


    @Override
    public byte[] toPacket() {
        ByteBuffer arr = ByteBuffer.allocate(20 + 8 * ipsNumber);
        arr.putInt(0,  20 + 8 * ipsNumber);
        arr.putInt(4, this.swarmID);
        arr.putInt(8,  this.userID);
        arr.putInt(12, this.header);
        arr.putInt(16, this.ipsNumber);

        for(int i = 0; i < ipsNumber; i++){
            arr.putInt(20 + 8*i, peerIDs[i]);
            arr.put(20 + 8*i + 4, ipsAsArray[i][0]);
            arr.put(20 + 8*i + 5, ipsAsArray[i][1]);
            arr.put(20 + 8*i + 6, ipsAsArray[i][2]);
            arr.put(20 + 8*i + 7, ipsAsArray[i][3]);
        }

        rawMessage = arr.array();

        return rawMessage;
    }

    public byte[][] getIpsAsArray() {
        return ipsAsArray;
    }

    public int getIpsNumber() {
        return ipsNumber;
    }

    public int[] getPeerIDs() {
        return peerIDs;
    }
}

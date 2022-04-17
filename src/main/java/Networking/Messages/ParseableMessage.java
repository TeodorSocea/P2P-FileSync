package Networking.Messages;

public class ParseableMessage implements SendableMessage {
    byte[] rawMessage;
    int swarmID;
    int userID;
    int header;

    public ParseableMessage(byte[] rawMessage){
        this.rawMessage = rawMessage.clone();
        this.parse();
    }

    private void parse(){
        swarmID = Messages.getIntFromByteArray(rawMessage,4);
        userID  = Messages.getIntFromByteArray(rawMessage,8);
        header  = Messages.getIntFromByteArray(rawMessage,12);
    }

    public byte[] getRawMessage() {
        return rawMessage;
    }

    public int getHeader() {
        return header;
    }

    public int getUserID() {
        return userID;
    }

    public int getSwarmID() {
        return swarmID;
    }

    @Override
    public byte[] toPacket() {
        return new byte[0];
    }
}

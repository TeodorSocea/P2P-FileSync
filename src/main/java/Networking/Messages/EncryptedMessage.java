package Networking.Messages;

public class EncryptedMessage extends Message{

    private static byte[] key = {108,109,97,111,32,103,97,121};

    public EncryptedMessage(byte[] rawMessage) {
        super(rawMessage);
    }

    public static void setKey(byte[] newKey){
        if(newKey != null)
            key = newKey;
    }

    public static EncryptedMessage encrypt(Message message){
        byte[] msg = message.toPacket();

        for (int i = 0; i < msg.length; i++) {
            msg[i] = (byte) (msg[i] ^ key[i % key.length]);
        }

        return new EncryptedMessage(msg);
    }

    public static Message decrypt(EncryptedMessage message){
        byte[] msg = message.toPacket();

        for (int i = 0; i < msg.length; i++) {
            msg[i] = (byte) (msg[i] ^ key[i % key.length]);
        }

        return new Message(msg);
    }
}

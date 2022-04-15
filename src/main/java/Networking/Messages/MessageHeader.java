package Networking.Messages;

public class MessageHeader {
    public static final int PING = 0;
    public static final int NEW_CONNECTION_REQUEST = 1;         // + swarmID
    public static final int NEW_CONNECTION_RESPONSE = 2;        // + swarmID + newUserID
    public static final int EXISTING_CONNECTION_REQUEST = 3;    // + swarmID + userID
    public static final int EXISTING_CONNECTION_RESPONSE = 4;   // + swarmID + accept/decline
    public static final int PEER_REQUEST = 5;                   // + swarmID
    public static final int PEER_RESPONSE = 6;                  // + swarmID + List<Pair<peerID,peerIP>>
    public static final int FILENAMES_REQUEST = 7;              // + swarmID
    public static final int FILENAME_RESPONSE = 8;              // + swarmID + fileID + <file path>
    public static final int DATA_REQUEST = 9;                   // + fileID + chunkNumber
    public static final int DATA_RESPONSE = 10;                 // + fileID + chunkNumber + <data>
}
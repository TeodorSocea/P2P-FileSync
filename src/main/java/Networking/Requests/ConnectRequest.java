package Networking.Requests;

import Networking.Requests.AbstractRequest;

public class ConnectRequest extends AbstractRequest {
    @Override
    public String toPacket() {
        return "PLS LET ME CONNECT";
    }
}

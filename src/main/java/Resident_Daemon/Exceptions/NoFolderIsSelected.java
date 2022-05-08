package Resident_Daemon.Exceptions;

import java.io.IOException;

public class NoFolderIsSelected extends IOException {
    public NoFolderIsSelected(String errorMsg) {
        super(errorMsg);
    }
}

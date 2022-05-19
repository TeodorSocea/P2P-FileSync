package Resident_Daemon.Exceptions;

import java.io.IOException;

public class NoFolderIsSelected extends Exception {
    public NoFolderIsSelected(String errorMsg) {
        super(errorMsg);
    }
}

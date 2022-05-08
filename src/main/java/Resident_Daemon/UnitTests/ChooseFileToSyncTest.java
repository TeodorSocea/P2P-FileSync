package Resident_Daemon.UnitTests;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.Console.ChooseFileToSync;
import Resident_Daemon.CommandsPack.Commands.Console.ChooseFolder;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Exceptions.NoFolderIsSelected;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.*;

class ChooseFileToSyncTest {
    Singleton singleton = Singleton.getSingletonObject();
    CommandExecutor commandExecutor = new CommandExecutor();

    @Test
    void NoFolderIsSelected() {

        ChooseFileToSync command = new ChooseFileToSync();
        commandExecutor.ExecuteOperation(command);

        Exception exception = command.getException();
        assertInstanceOf(NoFolderIsSelected.class, exception);
//        assertTrue(exception instanceof NoFolderIsSelected);
    }

    @Test
    void InvalidFileRelativePath() {

//        Put a valid path using, "ChooseFolder" command
        System.setIn(new ByteArrayInputStream("src/main/java/Resident_Daemon/UnitTests/Media".getBytes()));
        commandExecutor.ExecuteOperation(new ChooseFolder());

        ChooseFileToSync command = new ChooseFileToSync();

//        Input invalid path "3" as wrong relative file path, "ChooseFileToSync" command
        System.setIn(new ByteArrayInputStream("3".getBytes()));
        commandExecutor.ExecuteOperation(command);

        Exception exception = command.getException();
        assertInstanceOf(InvalidPathException.class, exception);
    }


    @Test
    void InvalidSwarmID() {
//        Put a valid path using, "ChooseFolder" command
        System.setIn(new ByteArrayInputStream("src/main/java/Resident_Daemon/UnitTests/Media".getBytes()));
        commandExecutor.ExecuteOperation(new ChooseFolder());

        ChooseFileToSync command = new ChooseFileToSync();

//        Input valid path "ceva.txt" as wrong relative file path, "ChooseFileToSync" command
//        Input for swarmID a char 'a'
        System.setIn(new ByteArrayInputStream("ceva.txt\na".getBytes()));
        commandExecutor.ExecuteOperation(command);

        Exception exception = command.getException();
        assertInstanceOf(NumberFormatException.class, exception);
    }

    @Test
    void ValidFileRelativePath_ValidSwarmID_NetworkFail() {

//        Put a valid path using, "ChooseFolder" command
        System.setIn(new ByteArrayInputStream("src/main/java/Resident_Daemon/UnitTests/Media".getBytes()));
        commandExecutor.ExecuteOperation(new ChooseFolder());

        ChooseFileToSync command = new ChooseFileToSync();

//        Input valid path "ceva.txt" as wrong relative file path, "ChooseFileToSync" command
//        Input for swarmID a char 'a'
        System.setIn(new ByteArrayInputStream("ceva.txt\n32".getBytes()));


        Exception exception = command.getException();

        assertThrows(NullPointerException.class,
                () -> {
                    commandExecutor.ExecuteOperation(command);
                });

//        Daca au rezolvat cei de la Networking(comanda de jos trebuie sa nu fie comentata)
//        assertInstanceOf(IOException.class, exception);
    }

}
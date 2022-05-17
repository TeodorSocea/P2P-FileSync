//package Resident_Daemon.UnitTests;
//
//import Resident_Daemon.CommandsPack.Commands.Command;
//import Resident_Daemon.CommandsPack.Commands.Console.ChooseFileToSync;
//import Resident_Daemon.CommandsPack.Commands.Console.ChooseFolder;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.nio.file.InvalidPathException;
//import Resident_Daemon.CommandsPack.CommandExecutor;
//
//import static org.junit.jupiter.api.Assertions.assertInstanceOf;


package Resident_Daemon._UnitTests.Tests;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.Console.ChooseFolder;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.Input;
import org.junit.jupiter.api.Test;

import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.*;


public class ChooseFolderTest {

    Exception runChooseFolderCommand(String folderPath) {
        Input.setIn(folderPath);
        CommandExecutor commandExecutor = new CommandExecutor();
        ChooseFolder command = new ChooseFolder();
        commandExecutor.ExecuteOperation(command);

        return command.getException();
    }

    @Test
    void CheckIfValidPathWasSet() {

        String validFolderPath = "src/main/java/Resident_Daemon/UnitTests/Media";

        Exception exception = runChooseFolderCommand(validFolderPath);

//        Check if there is no problem
        assertEquals(exception, null);

        String path = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        assertEquals(path, validFolderPath);
    }

    @Test
    void InvalidFolderRelativePath() {

//        Input invalid path "3" as wrong relative folder path
        Exception exception = runChooseFolderCommand("3");

        assertInstanceOf(InvalidPathException.class, exception);
    }

}

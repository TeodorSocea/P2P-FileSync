package Resident_Daemon._UnitTests.Tests;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ChooseFolder;
import Resident_Daemon.Core.Singleton;
import org.junit.jupiter.api.Test;

import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.*;

class ChooseFolderTest {

    @Test
    public void Test(){
        String folderPath = "GetFo";
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        ChooseFolder chooseFolder = new ChooseFolder(folderPath);

        commandExecutor.ExecuteOperation(chooseFolder);

        Exception exception = chooseFolder.getException();

        assertInstanceOf(InvalidPathException.class, exception);

    }

}
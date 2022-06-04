package Resident_Daemon._UnitTests.Tests;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ChooseFolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChooseFolderTest {
    String folderPath;
    CommandExecutor commandExecutor=new CommandExecutor();
    @Test
    void ChooseValidPath()
    {
        ChooseFolder chooseFolder=new ChooseFolder("src/main/java/Resident_Daemon/_UnitTests/Tests");
        assertTrue(commandExecutor.ExecuteOperation(chooseFolder));
    }
    @Test
    void ChooseInvalidPath()
    {
        ChooseFolder chooseFolder=new ChooseFolder("src/main/java/Resident_Daemon/_UnitTests/Tes");
        assertFalse( commandExecutor.ExecuteOperation(chooseFolder));
    }

}
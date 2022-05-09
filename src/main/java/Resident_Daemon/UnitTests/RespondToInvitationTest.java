package Resident_Daemon.UnitTests;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.Console.ChooseFolder;
import Resident_Daemon.CommandsPack.Commands.Console.RespondToInvitation;
import Resident_Daemon.Input;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class RespondToInvitationTest {

    Exception runRespondToInvitationCommand(String index, String response) {
        Input.setIn(index + "\n" + response);
        CommandExecutor commandExecutor = new CommandExecutor();
        RespondToInvitation command = new RespondToInvitation();
        commandExecutor.ExecuteOperation(command);

        return command.getException();
    }
    @Test
    void InvalidIndex() {

        Input.confScanner();
        Exception exception = runRespondToInvitationCommand("abc", "...");

        assertInstanceOf(NumberFormatException.class, exception);
    }

    @Test
    void IndexOutOfBoundsTest() {

        Input.confScanner();
        Exception exception = runRespondToInvitationCommand("11", "true");

        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }
}

package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.Version_Control_Component;

public class CreateSwarm extends ExceptionModule implements Command {
    private String swarmName;

    public CreateSwarm(String swarmName) {
        this.swarmName = swarmName;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        networkingComponent.createNewSwarmName(swarmName);
        BasicFileUtils.CreateSwarmFolder(swarmName);

        Singleton.getSingletonObject().getUserData().setConnected(true, networkingComponent.getSwarms());

        return true;
    }
}

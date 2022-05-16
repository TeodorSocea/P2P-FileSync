package Resident_Daemon.Core;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Console.GetTextFiles;
import Resident_Daemon.Utils.BasicFileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ThreadToSend implements Runnable{

    private byte[] auxFunc(Path relPath, byte[] fileData){


        String fileContent = new String(fileData, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder(relPath + "!" + fileContent);

        byte[] toSend = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

        return toSend;
    }

    @Override
    public void run() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        while(true){
            for (int i = 0; i < 100; i++);
            var updateInfo = networkingComponent.updateDefinitiveSwarms();
            //
            if(updateInfo == null){
                for (int i = 0; i < 100; i++);
            }else{
                for(var map : updateInfo){
                    for(var entry : map.entrySet()){

                        var mapData = GetTextFiles.getTextFiles(Singleton.getSingletonObject().getFolderToSyncPath());
                        for(var peerID : entry.getValue()){
                            for(var dataEntry : mapData.entrySet()){
                                boolean ok = false;
                                if(!ok){
                                    byte[] toSend = auxFunc(dataEntry.getKey(), dataEntry.getValue());
                                    try {
                                        networkingComponent.sentDataToPeer(toSend,entry.getKey(),peerID);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ok = true;
                                }

                            }
                        }
                    }
                }
            }

            //
        }
    }
}

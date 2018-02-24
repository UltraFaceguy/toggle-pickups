package me.joshuaemq;

import java.io.File;

public class PlayerDataConfig{

	public PlayerDataConfig(TogglePickupsPlugin plugin) {
        System.out.println("This is a message from the data config class!");
        onLoad();
	}
		
	public void onLoad() {
		System.out.println("ON LOAD TEST");
		final String workingDir = System.getProperty("user.dir");
		String dir = workingDir + "\\TogglePickups\\playerPreferences";
		
		File directory = new File(dir.toString());
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            System.out.println(file.getName());
        }
	}
}

package challenge.gui;
import java.io.File;

public class FileNameLoader {
	String directory;

	public FileNameLoader(String dir)
	{
		this.directory =dir;
	}
	public File[] getNames()
	{
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}
}



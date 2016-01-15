package red.exporttype;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileInLocal extends ExportType {
	private String pathToExport;
	private FileOutputStream out;

	@Override
	public OutputStream export() {
		try {
			this.out = new FileOutputStream(this.pathToExport+".xlsx");
			//return out;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this.out;
		
	}
	public void changeState(String paramToChange) {
		this.pathToExport = paramToChange;
	}
}
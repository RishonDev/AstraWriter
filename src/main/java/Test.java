import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    static void main() {
        FileChooser fileChooser = new FileChooser();
        System.out.println(fileChooser.getDisk());
    }
}
class FileChooser{
    JFileChooser chooser = new JFileChooser();
    FileSystemView fileSystemView;
    public String getDisk(){
        String os =  System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            FileSystemView fsv = createDriveOnlyFSV(getDisksWindows());
            chooser = new JFileChooser(fsv);
            chooser.setAcceptAllFileFilterUsed(false);
        }
        else if (os.contains("mac")) chooser.setCurrentDirectory(new File("/Volumes/"));
        else if (os.contains("linux"))chooser.setCurrentDirectory(new File("/media/" + System.getProperty("user.name") + "/"));

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Select a Volume");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        if (chooser.showOpenDialog(null) == 0)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }
    private File[] getDisksWindows() {
        ArrayList<File> drives = new ArrayList<>(26);

        for (char c = 'A'; c <= 'Z'; c++) {
            File drive = new File(c + ":\\");
            if (drive.exists()) {
                drives.add(drive);
            }
        }

        return drives.toArray(new File[0]);
    }
    private FileSystemView createDriveOnlyFSV(File[] drives) {
        return new FileSystemView() {

            @Override
            public File[] getRoots() {
                return drives; // Show ONLY these drives
            }

            @Override
            public boolean isRoot(File f) {
                for (File d : drives) {
                    if (d.equals(f)) return true;
                }
                return false;
            }

            @Override
            public File createNewFolder(File containingDir) throws IOException {
                return null;
            }

            @Override
            public File getHomeDirectory() {
                return drives.length > 0 ? drives[0] : new File("C:\\");
            }

            @Override
            public File getDefaultDirectory() {
                return getHomeDirectory();
            }
        };
    }


}
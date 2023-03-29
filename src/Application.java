import java.nio.file.Path;

public class Application {
    public static void main(String[] args) {
        Path storageDir = Path.of(System.getenv("MESSENGER_FILE_STORAGE_DIR"));
    }
}

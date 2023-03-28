package RepositoryProperties;

import java.nio.file.Path;
import java.util.Objects;

public class RepositoryProperties {
    private final Path storageDir;

    public RepositoryProperties(Path storageDir) {
        this.storageDir = Objects.requireNonNull(storageDir);

    }

    public Path getStorageDir() {
        return storageDir;
    }
}

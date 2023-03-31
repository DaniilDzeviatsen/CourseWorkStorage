package RepositoryProperties;

import java.nio.file.Path;
import java.util.Currency;
import java.util.Objects;

public class RepositoryProperties {
    private final Path storageDir;
    private final Currency localCurrency;

    public RepositoryProperties(Path storageDir, Currency localCurrency) {
        this.storageDir = Objects.requireNonNull(storageDir);
        this.localCurrency = localCurrency;
    }

    public Currency getLocalCurrency() {
        return localCurrency;
    }

    public Path getStorageDir() {
        return storageDir;
    }
}

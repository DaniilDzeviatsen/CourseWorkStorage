package Repository;

import java.nio.file.Path;

public class RatesFileRepository implements FileRepository{
    private static final Path FILE_NAME= Path.of("./data/exchange_rate/");

}

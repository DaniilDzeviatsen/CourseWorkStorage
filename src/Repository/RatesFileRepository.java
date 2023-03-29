package Repository;

import RepositoryProperties.RepositoryProperties;
import model.CurrencyRate;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RatesFileRepository implements FileRepository{
    private LocalDate requestedDate;
    private String fileName;
    private Path FILE_NAME= Path.of(this.requestedDate.toString());
    private final int CURRENCY_CODE_COLUMN=0;
    private final int SELL_RATE_COLUMN=1;
    private final int BUY_RATE_COLUMN=2;

    private final RepositoryProperties props;
    public RatesFileRepository(RepositoryProperties props){
        this.props= Objects.requireNonNull(props);
        try {
            Files.createDirectories(props.getStorageDir());
            Path filePath = props.getStorageDir().resolve(FILE_NAME);
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void putCurrencyRate(CurrencyRate currencyRate) {
        try{
            String csvLine = String.join(
                    ",",
                    currencyRate.,
                    currencyRate.getBuyRate().toString(),
                    currencyRate.getSellRate().toString(),
            ) + System.lineSeparator();
            Files.writeString(
                    props.getStorageDir().resolve(requestedDate.toString()),
                    csvLine,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCurrencyRate(LocalDate requestedDate, String currencyCode) {
        System.out.println("not  ready yet");
    }
    @Override
    public List <CurrencyRate> listCurrencyRates(LocalDate requestedDate){
        try {
            List<String> csvLines = Files.readAllLines(
                    props.getStorageDir().resolve(FILE_NAME),
                    StandardCharsets.UTF_8
            );
            List<CurrencyRate> currencyRates = new ArrayList<>();
            for (String csvLine : csvLines) {
                CurrencyRate currencyRate = parseMessage(csvLine);
                currencyRates.add(currencyRate);
            }
            return currencyRates;

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private CurrencyRate parseCurrencyRate(String csvLine) {
        String[] parts = csvLine.split(",");
        String currencyCode = parts[CURRENCY_CODE_COLUMN];
        BigDecimal buyRate = BigDecimal.parse(parts[BUY_RATE_COLUMN]);
        BigDecimal sellRate=parts[SELL_RATE_COLUMN];
        return new CurrencyRate(currencyCode, );
    }
}

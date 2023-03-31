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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RatesFileRepository {
    private final int CURRENCY_CODE_COLUMN = 0;
    private final int SELL_RATE_COLUMN = 1;
    private final int BUY_RATE_COLUMN = 2;

    private final RepositoryProperties props;

    public RatesFileRepository(RepositoryProperties props) {

        this.props = props;

        try {
            if (Files.notExists(props.getStorageDir())) {
                Files.createDirectories(props.getStorageDir());
                System.out.println("OK");
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }


    public void saveCurrencyRate(CurrencyRate currencyRate, LocalDate requestedDate) {

        try {
            Path filePath = props.getStorageDir().resolve(requestedDate + ".csv");
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
            String csvLine = String.join(
                    ",",
                    currencyRate.getCurrencyCode(),
                    currencyRate.getBuyRate().toString(),
                    currencyRate.getSellRate().toString()
            ) + System.lineSeparator();
            Files.writeString(
                    props.getStorageDir().resolve(requestedDate + ".csv"),
                    csvLine,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void removeCurrencyRate(LocalDate requestedDate, String currencyCode) {
        System.out.println("not  ready yet");
    }

    public List<CurrencyRate> listCurrencyRates(LocalDate requestedDate) {
        List<CurrencyRate> currencyRates = new ArrayList<>();
        Path filePath = props.getStorageDir().resolve(requestedDate + ".csv");
        try {
            if (Files.notExists(filePath)) {
                return currencyRates;
            }

            List<String> csvLines = Files.readAllLines(
                    props.getStorageDir().resolve(requestedDate + ".csv"),
                    StandardCharsets.UTF_8
            );
            for (String csvLine : csvLines) {
                CurrencyRate currencyRate = parseCurrencyRate(csvLine);
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
        BigDecimal buyRate = BigDecimal.valueOf(Double.parseDouble(parts[BUY_RATE_COLUMN]));
        BigDecimal sellRate = BigDecimal.valueOf(Double.parseDouble(parts[SELL_RATE_COLUMN]));
        return new CurrencyRate(currencyCode, buyRate, sellRate);
    }
}

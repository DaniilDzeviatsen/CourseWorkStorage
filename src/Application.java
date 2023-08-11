import controller.RatesController;
import repository.RatesFileRepository;
import repositoryProperties.RepositoryProperties;
import service.ExchangeServiceImpl;

import java.nio.file.Path;
import java.util.Currency;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Path storageDir = Path.of(System.getenv("EXCHANGER_FILE_STORAGE_DIR"));
        Currency localCurrency = Currency.getInstance(System.getenv("LOCAL_CURRENCY_CODE"));
        RepositoryProperties rateProps = new RepositoryProperties(storageDir, localCurrency);

        RatesFileRepository ratesFileRepository = new RatesFileRepository(rateProps);
        ExchangeServiceImpl service = new ExchangeServiceImpl(ratesFileRepository, localCurrency);
        RatesController ratesController = new RatesController(service);
        if (args.length > 0) {
            String command = args[0];
            List<String> options = List.of(args).subList(1, args.length);
            ratesController.handle(command, options);
        } else {
            ratesController.handle("", List.of());
        }
    }
}

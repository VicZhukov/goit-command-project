package banksUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import serviceClasses.Bank;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BanksUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private final Gson GSON = new Gson();

    private final String PRIVAT_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
    static Type typePrivat = new TypeToken<List<PrivatBank>>() {
    }.getType();

    public Bank getPrivatAPI() throws IOException, InterruptedException {
        final List<PrivatBank> datePrivatBank = sendGetBank(URI.create(PRIVAT_URL),typePrivat);
        for (PrivatBank currency : datePrivatBank) {
            if (currency.getCcy().equals("USD")) {
                datePrivatBank.add(currency);
                break;
            }
        }
        return getPrivat(datePrivatBank);
    }

    public <T> List<T> sendGetBank(URI uri,Type typeBank) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), typeBank);
    }

    public Bank getPrivat(List<PrivatBank> date) {
        Bank bank = new Bank();
        for (PrivatBank currency : date) {
            switch (currency.getCcy()) {
                case "USD":
                    bank.setUSD_buy(currency.getBuy());
                    bank.setUSD_sell(currency.getSale());
                    break;
                case "EUR":
                    bank.setEUR_buy(currency.getBuy());
                    bank.setEUR_sell(currency.getSale());
                    break;
            }
        }
        return bank;
    }
}
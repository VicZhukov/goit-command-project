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

    static Type typePrivat = new TypeToken<List<PrivatBank>>() {
    }.getType();
    static Type typeMono = new TypeToken<List<Monobank>>() {
    }.getType();
    static Type typeNBU = new TypeToken<List<NbuBank>>() {
    }.getType();

    public Bank getPrivatAPI() throws IOException, InterruptedException {
        String PRIVAT_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        final List<PrivatBank> datePrivatBank = sendGetBank(URI.create(PRIVAT_URL),typePrivat);
        return getPrivat(datePrivatBank);
    }

    public Bank getMonoAPI() throws IOException, InterruptedException {
        String MONOBANK_URL = "https://api.monobank.ua/bank/currency";
        final List<Monobank> dateMono = sendGetBank(URI.create(MONOBANK_URL),typeMono);
        return getMonobank(dateMono);
    }

    public Bank getNBUAPI() throws IOException, InterruptedException {
        String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?json";
        final List<NbuBank> dateNBU = sendGetBank(URI.create(NBU_URL),typeNBU);
        return getNbu(dateNBU);
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

    public Bank getMonobank(List<Monobank> monobankList) {
        Bank bank = new Bank();
        for (Monobank currency : monobankList) {
            if (currency.getCurrencyCodeA() == 840 && currency.getCurrencyCodeB() == 980) {
                bank.setUSD_buy(currency.getRateBuy());
                bank.setUSD_sell(currency.getRateSell());
            }else if(currency.getCurrencyCodeA() == 978 && currency.getCurrencyCodeB() == 980) {
                bank.setEUR_buy(currency.getRateBuy());
                bank.setEUR_sell(currency.getRateSell());
            }
        }
        return bank;
    }

    public Bank getNbu(List<NbuBank> listCurr) {
        Bank bank = new Bank();
        for (NbuBank currency : listCurr) {
            switch (currency.getCc()) {
                case "USD":
                    bank.setUSD_buy(currency.getRate());
                    bank.setUSD_sell(currency.getRate());
                    break;
                case "EUR":
                    bank.setEUR_buy(currency.getRate());
                    bank.setEUR_sell(currency.getRate());
                    break;
            }
        }
        return bank;
    }
}
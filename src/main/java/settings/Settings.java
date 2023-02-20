package settings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import serviceClasses.Bank;
import serviceClasses.CurrencyDataBase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class Settings {

    private CurrencyDataBase currencyDataBase;
    public Map<Long, Setting> settingsAllUsers = new HashMap<>();
    private final Gson settingGson = new Gson();
    private final Object monitor = new Object();

    public Settings(CurrencyDataBase currencyDataBase) {
        this.currencyDataBase = currencyDataBase;
    }

    public String getInfo(Long chatId) {
        StringBuilder messageToUser = new StringBuilder();
        Setting userSetting = settingsAllUsers.get(chatId);

        String bankName = "";

        if (userSetting.getSelectedLanguage().equals(Language.UA)){
            bankName = userSetting.getSelectedBank().getBankNameUA();
        } else {
            bankName = userSetting.getSelectedBank().getBankNameEN();
        }

        messageToUser.append(bankName).append("\n");
        int numberDecPlaces = userSetting.getNumberOfDecimalPlaces();
        List<Currency> currencies = userSetting.getSelectedCurrency();
        Bank bankInfo = currencyDataBase.getCurrentInfo(userSetting.getSelectedBank());
        for (Currency currency : currencies) {
            messageToUser.append(Language.translate("Курс купівлі ", userSetting.getSelectedLanguage()))
                    .append(currency.getCurrencyName())
                    .append(" - ")
                    .append(bankInfo.getBuyRate(currency) == 0 ?
                            Language.translate("немає купівлі", userSetting.getSelectedLanguage())
                            : format("%." + numberDecPlaces + "f", bankInfo.getBuyRate(currency)) + " UAH"+"\n");
            messageToUser.append(Language.translate("Курс продажу ",userSetting.getSelectedLanguage()))
                    .append(currency.getCurrencyName())
                    .append(" - ")
                    .append(bankInfo.getSellRate(currency) == 0 ?
                            Language.translate("немає продажу", userSetting.getSelectedLanguage())
                            : format("%." + numberDecPlaces + "f", bankInfo.getSellRate(currency)) + " UAH"+"\n");
        }
        return messageToUser.toString();
    }

    public File fileSettingsGsonCheck() {
        String SETTING_GSON_PATH = "src/main/resources/settings.json";
        File settingGsonFile = new File(SETTING_GSON_PATH);
        if (!settingGsonFile.exists()) {
            System.out.println("Create Path for Gson file Settings - " + settingGsonFile.getParentFile().mkdirs());
            try {
                System.out.println("Create new Gson file Settings - " + settingGsonFile.createNewFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(settingGsonFile.length());
        }
        return settingGsonFile;
    }


    public void load() {
        IntermediateSettings intermediateSettings = new IntermediateSettings();
        synchronized (monitor) {

            File file = fileSettingsGsonCheck();
            if (file.length() != 0) {
                try {
                    intermediateSettings.intermediateSettings = new ObjectMapper().readValue(file, new TypeReference<Map<Long, IntermediateSetting>>() {
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                converter(intermediateSettings);
            }
        }
    }

    public void save() {
        synchronized (monitor) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSettingsGsonCheck()))) {
                writer.write(settingGson.toJson(settingsAllUsers));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void converter(IntermediateSettings intermediateSettings) {
        synchronized (monitor) {
            Map<Long, IntermediateSetting> inputMap = intermediateSettings.intermediateSettings;
            Map<Long, Setting> outputMap = settingsAllUsers;
            inputMap.forEach((k, v) -> {
                Setting outputSetting = new Setting();

                outputSetting.setChatId(v.getChatId());
                outputSetting.setNumberOfDecimalPlaces(parseNumOfDecPlaces(v.getNumberOfDecimalPlaces()));
                outputSetting.setSelectedBank(parseSelectedBank(v.getSelectedBank()));
                outputSetting.setSelectedCurrency(parseCurrency(v.getSelectedCurrency()));
                outputSetting.setNotificationTime(parseNotificationTime(v.getNotificationTime()));
                outputMap.put(v.getChatId(), outputSetting);
            });
        }
    }

    private NumberOfDecimalPlaces parseNumOfDecPlaces(String inputStrNumOfDec) {
        for (NumberOfDecimalPlaces value : NumberOfDecimalPlaces.values()) {
            if (inputStrNumOfDec.equals(value.name())) {
                return value;
            }
        }
        return null;
    }

    private Banks parseSelectedBank(String inputStrBank) {
        for (Banks value : Banks.values()) {
            if (inputStrBank.equals(value.name())) {
                return value;
            }
        }
        return null;
    }

    private List<Currency> parseCurrency(List<String> inputListStrCurrency) {
        List<Currency> result = new ArrayList<>();
        for (Currency value : Currency.values()) {
            for (String oneCurrency : inputListStrCurrency) {
                if (oneCurrency.equals(value.name())) {
                    result.add(value);
                }
            }

        }
        return result;
    }

    private NotificationTime parseNotificationTime(String inputStrNotificationTime) {
        for (NotificationTime value : NotificationTime.values()) {
            if (inputStrNotificationTime.equals(value.name())) {
                return value;
            }
        }
        return null;
    }

}

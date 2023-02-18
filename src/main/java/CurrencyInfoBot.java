import keyboards.Menu;
import keyboards.MenuEN;
import keyboards.MenuUA;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrencyInfoBot extends TelegramLongPollingBot {
    private static CurrencyInfoBot instance;
    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    private final Settings settings;
    private final String value;
    private Menu menu;
    private final static Object monitor = new Object();


    private CurrencyInfoBot(String value, Settings settings) {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.menu = new MenuUA(settings);
        this.value = value;
        this.settings = settings;
    }

    public static CurrencyInfoBot getInstance(String value, Settings settings) {
        if (instance == null) {
            instance = new CurrencyInfoBot(value, settings);
        }
        return instance;
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        if (update.hasCallbackQuery()) {
            try {
                handleQuery(update.getCallbackQuery());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleMessage(Message message) throws TelegramApiException {
        Setting userSettings;
        long chatId = message.getChatId();
        synchronized (monitor) {
            userSettings = settings.settingsAllUsers.get(chatId);
        }
        if (userSettings == null) {
            userSettings = new Setting(chatId, NumberOfDecimalPlaces.TWO, Banks.PRIVAT, Currency.getSelectedCurrencyList(), NotificationTime.NINE, ZoneId.UTC_THREE, Language.UA);
            synchronized (monitor) {
                settings.settingsAllUsers.put(chatId, userSettings);
            }
        }
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity;
            commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                if (command.equals(Buttons.START.getNameEN())) {
                    printMessage(chatId, menu.keyboardLanguage(chatId), "Будь ласка оберіть мову. Please select language.\n" + "Proszę wybrać język. Prosím vyberte jazyk.\n" + "Выбери пожалуйста язык.");
                }
            }
        } else {
            printMessage(chatId, Language.translate("Будь ласка впишіть /start або натисніть кнопку.", userSettings.getSelectedLanguage()));
        }
    }

    private void handleQuery(CallbackQuery buttonQuery) throws TelegramApiException {
        Setting userSettings;
        long chatId = buttonQuery.getMessage().getChatId();
        synchronized (monitor) {
            userSettings = settings.settingsAllUsers.get(chatId);
        }
        if (userSettings == null) {
            userSettings = new Setting(chatId, NumberOfDecimalPlaces.TWO, Banks.PRIVAT, Currency.getSelectedCurrencyList(), NotificationTime.NINE, ZoneId.UTC_THREE, Language.UA);
        }
        menu = getMenu(userSettings);

        checkLanguageStartMenu(buttonQuery, userSettings);
        checkMainMenu(buttonQuery, userSettings);
        checkBanksMenu(buttonQuery, userSettings);
        checkDecimalPlacesMenu(buttonQuery, userSettings);
        checkNotificationMenu(buttonQuery, userSettings);
        checkCurrencyMenu(buttonQuery, userSettings);
        checkZoneIdMenu(buttonQuery, userSettings);
        checkLanguageSettingsMenu(buttonQuery, userSettings);
    }

    private void saveSelectCurrency(CallbackQuery buttonQuery, Currency enumData, Setting userSettings) throws TelegramApiException {
        List<Currency> currentCurrencies = userSettings.getSelectedCurrency();
        if (currentCurrencies.contains(enumData)) {
            currentCurrencies.remove(enumData);
        } else {
            currentCurrencies.add(enumData);
        }
        updateMessage(buttonQuery, menu.keyboardCurrency(buttonQuery.getMessage().getChatId()));
    }

    private void saveSelectZoneId(CallbackQuery buttonQuery, ZoneId enumData, Setting userSettings) throws TelegramApiException {
        if (!userSettings.getZoneId().getNameZone().equals(enumData.getNameZone())) {
            userSettings.setZoneId(enumData);
            updateMessage(buttonQuery, menu.keyboardZoneId(buttonQuery.getMessage().getChatId()));
        }
    }

    private void saveSelectNumDecPlaces(CallbackQuery buttonQuery, NumberOfDecimalPlaces enumData, Setting userSettings) throws TelegramApiException {
        if (userSettings.getNumberOfDecimalPlaces() != enumData.getIntNumber()) {
            userSettings.setNumberOfDecimalPlaces(enumData);
            updateMessage(buttonQuery, menu.keyboardNumDecPlaces(buttonQuery.getMessage().getChatId()));
        }
    }

    private void saveSelectNotificationTime(CallbackQuery buttonQuery, NotificationTime enumData, Setting userSettings) throws TelegramApiException {
        if (userSettings.getNotificationTime().getTime() != enumData.getTime()) {
            userSettings.setNotificationTime(enumData);
            updateMessage(buttonQuery, menu.keyboardNotification(buttonQuery.getMessage().getChatId()));
        }
    }

    private void saveSelectBanks(CallbackQuery buttonQuery, Banks enumData, Setting userSettings) throws TelegramApiException {
        if (!userSettings.getSelectedBank().equals(enumData)) {
            userSettings.setSelectedBank(enumData);
            updateMessage(buttonQuery, menu.keyboardBanks(buttonQuery.getMessage().getChatId()));
        }
    }

    private void saveSelectLanguage(CallbackQuery buttonQuery, Language enumData, Setting userSettings) throws TelegramApiException {
        if (!userSettings.getSelectedLanguage().equals(enumData)) {
            userSettings.setSelectedLanguage(enumData);
            menu = getMenu(userSettings);
            updateMessage(buttonQuery, menu.keyboardLanguage(buttonQuery.getMessage().getChatId()));
        }
    }

    private void saveSelectLanguageSet(CallbackQuery buttonQuery, Language enumData, Setting userSettings) throws TelegramApiException {
        if (!userSettings.getSelectedLanguage().equals(enumData)) {
            userSettings.setSelectedLanguage(enumData);
            menu = getMenu(userSettings);
            updateMessage(buttonQuery, menu.keyboardLanguageSet(buttonQuery.getMessage().getChatId()));
        }
    }

    private void printMessage(Long chatID, InlineKeyboardMarkup keyboard, String text) throws TelegramApiException {
        execute(SendMessage.builder().text(text).chatId(chatID).replyMarkup(keyboard).build());
    }

    public void printMessage(Long chatID, String messageText) throws TelegramApiException {
        execute(SendMessage.builder().text(messageText).chatId(chatID).build());
    }

    private void updateMessage(CallbackQuery buttonQuery, InlineKeyboardMarkup keyboard) throws TelegramApiException {
        long chatId = buttonQuery.getMessage().getChatId();
        int messageId = buttonQuery.getMessage().getMessageId();
        execute(EditMessageReplyMarkup.builder().chatId(chatId).messageId(messageId).replyMarkup(keyboard).build());
    }

    public void checkMainMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        long chatId = buttonQuery.getMessage().getChatId();
        Buttons button = Buttons.convertToEnum(buttonQuery.getData());
        if (button != null) {
            switch (button) {
                case START:
                    printMessage(chatId, menu.keyboardStart(), Language.translate("Ласкаво просимо. Цей бот дозволить відслідкувати актуальні курси валют.", userSettings.getSelectedLanguage()));
                    break;
                case GET_INFO:
                    service.execute(new SaveSettings(settings));
                    printMessage(chatId, settings.getInfo(chatId));
                    printMessage(chatId, menu.keyboardStart(), Language.translate("Щоб отримати інфо натисність кнопку", settings.settingsAllUsers.get(chatId).getSelectedLanguage()));
                    break;
                case SETTINGS:
                    printMessage(chatId, menu.keyboardSettings(settings.settingsAllUsers.get(chatId)), Language.translate("Виберіть налаштування", settings.settingsAllUsers.get(chatId).getSelectedLanguage()));
                    break;
                case BACK_TO_START:
                    printMessage(chatId, menu.keyboardStart(), Language.translate("Щоб отримати інфо натисність кнопку", settings.settingsAllUsers.get(chatId).getSelectedLanguage()));
                    break;
                case NUM_DECIMAL_PLACES:
                    updateMessage(buttonQuery, menu.keyboardNumDecPlaces(chatId));
                    break;
                case BANK:
                    updateMessage(buttonQuery, menu.keyboardBanks(chatId));
                    break;
                case CURRENCY:
                    updateMessage(buttonQuery, menu.keyboardCurrency(chatId));
                    break;
                case NOTIFICATION:
                    updateMessage(buttonQuery, menu.keyboardNotification(chatId));
                    break;
                case LANGUAGE:
                    updateMessage(buttonQuery, menu.keyboardLanguageSet(chatId));
                    break;
            }
        }
    }

    public void checkBanksMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        Banks bank = Banks.convertToEnum(buttonQuery.getData());
        if (bank != null) {
            switch (bank) {
                case PRIVAT:
                    saveSelectBanks(buttonQuery, Banks.PRIVAT, userSettings);
                    break;
                case NBU:
                    saveSelectBanks(buttonQuery, Banks.NBU, userSettings);
                    break;
                case MONO:
                    saveSelectBanks(buttonQuery, Banks.MONO, userSettings);
                    break;
            }
        }
    }

    public void checkDecimalPlacesMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        NumberOfDecimalPlaces numberOfDecimalPlace = NumberOfDecimalPlaces.convertToEnum(buttonQuery.getData());
        if (numberOfDecimalPlace != null) {
            switch (numberOfDecimalPlace) {
                case TWO:
                    saveSelectNumDecPlaces(buttonQuery, NumberOfDecimalPlaces.TWO, userSettings);
                    break;
                case THREE:
                    saveSelectNumDecPlaces(buttonQuery, NumberOfDecimalPlaces.THREE, userSettings);
                    break;
                case FOUR:
                    saveSelectNumDecPlaces(buttonQuery, NumberOfDecimalPlaces.FOUR, userSettings);
                    break;
            }
        }
    }

    public void checkNotificationMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        NotificationTime notificationTime = NotificationTime.convertToEnum(buttonQuery.getData());
        if (notificationTime != null) {
            switch (notificationTime) {
                case NINE:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.NINE, userSettings);
                    break;
                case TEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.TEN, userSettings);
                    break;
                case ELEVEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.ELEVEN, userSettings);
                    break;
                case TWELVE:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.TWELVE, userSettings);
                    break;
                case THIRTEEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.THIRTEEN, userSettings);
                    break;
                case FOURTEEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.FOURTEEN, userSettings);
                    break;
                case FIFTEEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.FIFTEEN, userSettings);
                    break;
                case SIXTEEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.SIXTEEN, userSettings);
                    break;
                case SEVENTEEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.SEVENTEEN, userSettings);
                    break;
                case EIGHTEEN:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.EIGHTEEN, userSettings);
                    break;
                case SWICH_OFF:
                    saveSelectNotificationTime(buttonQuery, NotificationTime.SWICH_OFF, userSettings);
                    break;
            }
        }
    }

    public void checkCurrencyMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        Currency currency = Currency.convertToEnum(buttonQuery.getData());
        if (currency != null) {
            switch (currency) {
                case USD:
                    saveSelectCurrency(buttonQuery, Currency.USD, userSettings);
                    break;
                case EUR:
                    saveSelectCurrency(buttonQuery, Currency.EUR, userSettings);
                    break;
            }
        }
    }


    private void checkLanguageStartMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        long chatId = buttonQuery.getMessage().getChatId();
        Language language = Language.convertToEnum(buttonQuery.getData());
        if (language != null) {
            switch (language) {
                case UA:
                    saveSelectLanguage(buttonQuery, Language.UA, userSettings);
                    break;
                case EN:
                    saveSelectLanguage(buttonQuery, Language.EN, userSettings);
                    break;
            }
        }
    }

    private void checkLanguageSettingsMenu(CallbackQuery buttonQuery, Setting userSettings) throws TelegramApiException {
        long chatId = buttonQuery.getMessage().getChatId();
        Language language = Language.convertToEnumSet(buttonQuery.getData());
        if (language != null) {
            switch (language) {
                case UA:
                    saveSelectLanguageSet(buttonQuery, Language.UA, userSettings);
                    break;
                case EN:
                    saveSelectLanguageSet(buttonQuery, Language.EN, userSettings);
                    break;
            }
        }
    }

    private Menu getMenu(Setting userSettings) {
        menu = userSettings.getSelectedLanguage() == Language.EN ? new MenuEN(settings) : userSettings.getSelectedLanguage() == Language.UA ? new MenuUA(settings) : menu;
        return menu;
    }
}

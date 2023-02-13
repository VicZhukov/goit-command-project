package keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {

    private final Settings settings;



    public Menu(Settings settings) {
        this.settings = settings;
    }

    public InlineKeyboardMarkup keyboardStart() {
        List<List<InlineKeyboardButton>> keyboardMenuStart = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSRow2 = new ArrayList<>();
        InlineKeyboardButton buttonGetInfo = InlineKeyboardButton.builder()
                .text(Buttons.GET_INFO.getNameUA())
                .callbackData(Buttons.GET_INFO.getNameEN())
                .build();
        InlineKeyboardButton buttonSettings = InlineKeyboardButton.builder()
                .text(Buttons.SETTINGS.getNameUA())
                .callbackData(Buttons.SETTINGS.getNameEN())
                .build();
        keyboardMSRow1.add(buttonGetInfo);
        keyboardMSRow2.add(buttonSettings);
        keyboardMenuStart.add(keyboardMSRow1);
        keyboardMenuStart.add(keyboardMSRow2);
        return InlineKeyboardMarkup.builder().keyboard(keyboardMenuStart).build();
    }

    public InlineKeyboardMarkup keyboardSettings(Setting setting) {
        String selectedCurr = setting.getSelectedCurrency().stream()
                .map(Currency::getCurrencyName)
                .collect(Collectors.joining(", ", "(", ")"));

        List<List<InlineKeyboardButton>> keyboardMenuSettings = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow5 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow6 = new ArrayList<>();
        InlineKeyboardButton buttonNumOfDecPlaces = InlineKeyboardButton.builder()
                .text(Buttons.NUM_DECIMAL_PLACES.getNameUA() + " (" + setting.getNumberOfDecimalPlaces() + ")")
                .callbackData(Buttons.NUM_DECIMAL_PLACES.getNameEN())
                .build();
        InlineKeyboardButton buttonBank = InlineKeyboardButton.builder()
                .text(Buttons.BANK.getNameUA() + " (" + setting.getSelectedBank().getBankNameUA() + ")")
                .callbackData(Buttons.BANK.getNameEN())
                .build();
        InlineKeyboardButton buttonCurrency = InlineKeyboardButton.builder()
                .text(Buttons.CURRENCY.getNameUA() + selectedCurr)
                .callbackData(Buttons.CURRENCY.getNameEN())
                .build();
        String NotificationTimeSet = setting.getNotificationTime().getTime() == 0 ? "OFF" :
                String.valueOf(setting.getNotificationTime().getTime());
        InlineKeyboardButton buttonNotificationTime = InlineKeyboardButton.builder()
                .text(Buttons.NOTIFICATION.getNameUA() + " (" + NotificationTimeSet + ")")
                .callbackData(Buttons.NOTIFICATION.getNameEN())
                .build();
        InlineKeyboardButton buttonZoneId = InlineKeyboardButton.builder()
                .text(Buttons.ZONEID.getNameUA() + " (" + setting.getZoneId().getNameZone() + ")")
                .callbackData(Buttons.ZONEID.getNameEN())
                .build();
        InlineKeyboardButton buttonBack = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_START.getNameUA())
                .callbackData(Buttons.BACK_TO_START.getNameEN())
                .build();

        keyboardMSetRow1.add(buttonNumOfDecPlaces);
        keyboardMSetRow2.add(buttonBank);
        keyboardMSetRow3.add(buttonCurrency);
        keyboardMSetRow4.add(buttonNotificationTime);
        keyboardMSetRow5.add(buttonZoneId);
        keyboardMSetRow6.add(buttonBack);
        keyboardMenuSettings.add(keyboardMSetRow1);
        keyboardMenuSettings.add(keyboardMSetRow2);
        keyboardMenuSettings.add(keyboardMSetRow3);
        keyboardMenuSettings.add(keyboardMSetRow4);
        keyboardMenuSettings.add(keyboardMSetRow5);
        keyboardMenuSettings.add(keyboardMSetRow6);

        return InlineKeyboardMarkup.builder().keyboard(keyboardMenuSettings).build();
    }

    public InlineKeyboardMarkup keyboardBanks(long chatId) {
        Setting userSetting = settings.settingsAllUsers.get(chatId);
        Banks selectedBank = userSetting.getSelectedBank();
        List<List<InlineKeyboardButton>> keyboardMenuBanks = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow4 = new ArrayList<>();
        InlineKeyboardButton buttonPrivat = InlineKeyboardButton.builder()
                .text(Banks.PRIVAT.getBankNameEN() + getButtonStatus(Banks.PRIVAT, selectedBank))
                .callbackData(Banks.PRIVAT.getBankNameEN())
                .build();
        InlineKeyboardButton buttonNBU = InlineKeyboardButton.builder()
                .text(Banks.NBU.getBankNameEN() + getButtonStatus(Banks.NBU, selectedBank))
                .callbackData(Banks.NBU.getBankNameEN())
                .build();
        InlineKeyboardButton buttonMonobank = InlineKeyboardButton.builder()
                .text(Banks.MONO.getBankNameEN() + getButtonStatus(Banks.MONO, selectedBank))
                .callbackData(Banks.MONO.getBankNameEN())
                .build();
        InlineKeyboardButton buttonHome = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_START.getNameUA())
                .callbackData(Buttons.BACK_TO_START.getNameEN())
                .build();
        InlineKeyboardButton buttonBack = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_SETTINGS.getNameUA())
                .callbackData(Buttons.BACK_TO_SETTINGS.getNameEN())
                .build();
        keyboardMSetRow1.add(buttonPrivat);
        keyboardMSetRow2.add(buttonNBU);
        keyboardMSetRow3.add(buttonMonobank);
        keyboardMSetRow4.add(buttonHome);
        keyboardMSetRow4.add(buttonBack);
        keyboardMenuBanks.add(keyboardMSetRow1);
        keyboardMenuBanks.add(keyboardMSetRow2);
        keyboardMenuBanks.add(keyboardMSetRow3);
        keyboardMenuBanks.add(keyboardMSetRow4);

        return InlineKeyboardMarkup.builder().keyboard(keyboardMenuBanks).build();
    }

    public InlineKeyboardMarkup keyboardCurrency(long chatId) {
        Setting userSetting = settings.settingsAllUsers.get(chatId);
        List<Currency> selectedCurrencies = userSetting.getSelectedCurrency();
        List<List<InlineKeyboardButton>> keyboardMenuCurrency = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMenuCurrency1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMenuCurrency2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMenuCurrency3 = new ArrayList<>();
        InlineKeyboardButton buttonUsd = InlineKeyboardButton.builder()
                .text(Currency.USD.getCurrencyName() + getButtonStatus(Currency.USD, selectedCurrencies))
                .callbackData(Currency.USD.getCurrencyName())
                .build();
        InlineKeyboardButton buttonEur = InlineKeyboardButton.builder()
                .text(Currency.EUR.getCurrencyName() + getButtonStatus(Currency.EUR, selectedCurrencies))
                .callbackData(Currency.EUR.getCurrencyName())
                .build();
        InlineKeyboardButton buttonHome = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_START.getNameUA())
                .callbackData(Buttons.BACK_TO_START.getNameEN())
                .build();
        InlineKeyboardButton buttonBack = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_SETTINGS.getNameUA())
                .callbackData(Buttons.BACK_TO_SETTINGS.getNameEN())
                .build();
        keyboardMenuCurrency1.add(buttonUsd);
        keyboardMenuCurrency2.add(buttonEur);
        keyboardMenuCurrency3.add(buttonHome);
        keyboardMenuCurrency3.add(buttonBack);
        keyboardMenuCurrency.add(keyboardMenuCurrency1);
        keyboardMenuCurrency.add(keyboardMenuCurrency2);
        keyboardMenuCurrency.add(keyboardMenuCurrency3);

        return InlineKeyboardMarkup.builder().keyboard(keyboardMenuCurrency).build();
    }

    public InlineKeyboardMarkup keyboardNotification(long chatId) {
        Setting userSetting = settings.settingsAllUsers.get(chatId);
        NotificationTime selectedNotificationTime = userSetting.getNotificationTime();
        List<List<InlineKeyboardButton>> keyboardMenuNotification = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow4 = new ArrayList<>();
        InlineKeyboardButton buttonNotificationTime9 = InlineKeyboardButton.builder()
                .text(NotificationTime.NINE.getTime() + getButtonStatus(NotificationTime.NINE, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.NINE.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime10 = InlineKeyboardButton.builder()
                .text(NotificationTime.TEN.getTime() + getButtonStatus(NotificationTime.TEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.TEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime11 = InlineKeyboardButton.builder()
                .text(NotificationTime.ELEVEN.getTime() + getButtonStatus(NotificationTime.ELEVEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.ELEVEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime12 = InlineKeyboardButton.builder()
                .text(NotificationTime.TWELVE.getTime() + getButtonStatus(NotificationTime.TWELVE, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.TWELVE.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime13 = InlineKeyboardButton.builder()
                .text(NotificationTime.THIRTEEN.getTime() + getButtonStatus(NotificationTime.THIRTEEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.THIRTEEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime14 = InlineKeyboardButton.builder()
                .text(NotificationTime.FOURTEEN.getTime() + getButtonStatus(NotificationTime.FOURTEEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.FOURTEEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime15 = InlineKeyboardButton.builder()
                .text(NotificationTime.FIFTEEN.getTime() + getButtonStatus(NotificationTime.FIFTEEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.FIFTEEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime16 = InlineKeyboardButton.builder()
                .text(NotificationTime.SIXTEEN.getTime() + getButtonStatus(NotificationTime.SIXTEEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.SIXTEEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime17 = InlineKeyboardButton.builder()
                .text(NotificationTime.SEVENTEEN.getTime() + getButtonStatus(NotificationTime.SEVENTEEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.SEVENTEEN.getTime()))
                .build();
        InlineKeyboardButton buttonNotificationTime18 = InlineKeyboardButton.builder()
                .text(NotificationTime.EIGHTEEN.getTime() + getButtonStatus(NotificationTime.EIGHTEEN, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.EIGHTEEN.getTime()))
                .build();
        InlineKeyboardButton buttonTurnOfNotification = InlineKeyboardButton.builder()
                .text("OFF" + getButtonStatus(NotificationTime.SWICH_OFF, selectedNotificationTime))
                .callbackData(String.valueOf(NotificationTime.SWICH_OFF.getTime()))
                .build();
        InlineKeyboardButton buttonHome = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_START.getNameUA())
                .callbackData(Buttons.BACK_TO_START.getNameEN())
                .build();
        InlineKeyboardButton buttonBack = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_SETTINGS.getNameUA())
                .callbackData(Buttons.BACK_TO_SETTINGS.getNameEN())
                .build();
        keyboardMSetRow1.add(buttonNotificationTime9);
        keyboardMSetRow1.add(buttonNotificationTime10);
        keyboardMSetRow1.add(buttonNotificationTime11);
        keyboardMSetRow1.add(buttonNotificationTime12);
        keyboardMSetRow1.add(buttonNotificationTime13);
        keyboardMSetRow2.add(buttonNotificationTime14);
        keyboardMSetRow2.add(buttonNotificationTime15);
        keyboardMSetRow2.add(buttonNotificationTime16);
        keyboardMSetRow2.add(buttonNotificationTime17);
        keyboardMSetRow2.add(buttonNotificationTime18);
        keyboardMSetRow3.add(buttonTurnOfNotification);
        keyboardMSetRow4.add(buttonHome);
        keyboardMSetRow4.add(buttonBack);
        keyboardMenuNotification.add(keyboardMSetRow1);
        keyboardMenuNotification.add(keyboardMSetRow2);
        keyboardMenuNotification.add(keyboardMSetRow3);
        keyboardMenuNotification.add(keyboardMSetRow4);

        return InlineKeyboardMarkup.builder().keyboard(keyboardMenuNotification).build();
    }

    public InlineKeyboardMarkup keyboardNumDecPlaces(long chatId) {
        Setting userSetting = settings.settingsAllUsers.get(chatId);
        int selectedNumDecPlaces = userSetting.getNumberOfDecimalPlaces();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMSetRow4 = new ArrayList<>();
        InlineKeyboardButton buttonNumberOfDecimalPlaces2 = InlineKeyboardButton.builder()
                .text(NumberOfDecimalPlaces.TWO.getIntNumber() + getButtonStatus(NumberOfDecimalPlaces.TWO.getIntNumber(), selectedNumDecPlaces))
                .callbackData(NumberOfDecimalPlaces.TWO.getNameDecPlaces())
                .build();
        InlineKeyboardButton buttonNumberOfDecimalPlaces3 = InlineKeyboardButton.builder()
                .text(NumberOfDecimalPlaces.THREE.getIntNumber() + getButtonStatus(NumberOfDecimalPlaces.THREE.getIntNumber(), selectedNumDecPlaces))
                .callbackData(NumberOfDecimalPlaces.THREE.getNameDecPlaces())
                .build();
        InlineKeyboardButton buttonNumberOfDecimalPlaces4 = InlineKeyboardButton.builder()
                .text(NumberOfDecimalPlaces.FOUR.getIntNumber() + getButtonStatus(NumberOfDecimalPlaces.FOUR.getIntNumber(), selectedNumDecPlaces))
                .callbackData(NumberOfDecimalPlaces.FOUR.getNameDecPlaces())
                .build();
        InlineKeyboardButton buttonHome = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_START.getNameUA())
                .callbackData(Buttons.BACK_TO_START.getNameEN())
                .build();
        InlineKeyboardButton buttonBackToSetting = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_SETTINGS.getNameUA())
                .callbackData(Buttons.BACK_TO_SETTINGS.getNameEN())
                .build();
        keyboardMSetRow1.add(buttonNumberOfDecimalPlaces2);
        keyboardMSetRow2.add(buttonNumberOfDecimalPlaces3);
        keyboardMSetRow3.add(buttonNumberOfDecimalPlaces4);
        keyboardMSetRow4.add(buttonHome);
        keyboardMSetRow4.add(buttonBackToSetting);
        keyboard.add(keyboardMSetRow1);
        keyboard.add(keyboardMSetRow2);
        keyboard.add(keyboardMSetRow3);
        keyboard.add(keyboardMSetRow4);

        return InlineKeyboardMarkup.builder().keyboard(keyboard).build();
    }

    public InlineKeyboardMarkup keyboardZoneId(long chatId) {
        Setting userSetting = settings.settingsAllUsers.get(chatId);
        ZoneId selectedZoneID = userSetting.getZoneId();
        List<List<InlineKeyboardButton>> keyboardMZoneId = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow5 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow6 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow7 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardMZoneIdRow8 = new ArrayList<>();
        InlineKeyboardButton buttonZoneIdOne = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_ONE.getNameZone() + getButtonStatus(ZoneId.UTC_ONE, selectedZoneID))
                .callbackData(ZoneId.UTC_ONE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdTwo = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_TWO.getNameZone() + getButtonStatus(ZoneId.UTC_TWO, selectedZoneID))
                .callbackData(ZoneId.UTC_TWO.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdThree = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_THREE.getNameZone() + getButtonStatus(ZoneId.UTC_THREE, selectedZoneID))
                .callbackData(ZoneId.UTC_THREE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdFour = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_FOUR.getNameZone() + getButtonStatus(ZoneId.UTC_FOUR, selectedZoneID))
                .callbackData(ZoneId.UTC_FOUR.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdFive = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_FIVE.getNameZone() + getButtonStatus(ZoneId.UTC_FIVE, selectedZoneID))
                .callbackData(ZoneId.UTC_FIVE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdSix = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_SIX.getNameZone() + getButtonStatus(ZoneId.UTC_SIX, selectedZoneID))
                .callbackData(ZoneId.UTC_SIX.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdSeven = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_SEVEN.getNameZone() + getButtonStatus(ZoneId.UTC_SEVEN, selectedZoneID))
                .callbackData(ZoneId.UTC_SEVEN.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdEight = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_EIGHT.getNameZone() + getButtonStatus(ZoneId.UTC_EIGHT, selectedZoneID))
                .callbackData(ZoneId.UTC_EIGHT.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdNine = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_NINE.getNameZone() + getButtonStatus(ZoneId.UTC_NINE, selectedZoneID))
                .callbackData(ZoneId.UTC_NINE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdTen = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_TEN.getNameZone() + getButtonStatus(ZoneId.UTC_TEN, selectedZoneID))
                .callbackData(ZoneId.UTC_TEN.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdEleven = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_ELEVEN.getNameZone() + getButtonStatus(ZoneId.UTC_ELEVEN, selectedZoneID))
                .callbackData(ZoneId.UTC_ELEVEN.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdTwelve = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_TWELVE.getNameZone() + getButtonStatus(ZoneId.UTC_TWELVE, selectedZoneID))
                .callbackData(ZoneId.UTC_TWELVE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMOne = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_ONE.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_ONE, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_ONE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMTwo = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_TWO.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_TWO, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_TWO.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMThree = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_THREE.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_THREE, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_THREE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMFour = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_FOUR.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_FOUR, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_FOUR.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMFive = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_FIVE.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_FIVE, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_FIVE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMSix = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_SIX.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_SIX, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_SIX.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMSeven = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_SEVEN.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_SEVEN, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_SEVEN.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdOMEight = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_EIGHT.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_EIGHT, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_EIGHT.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMNine = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_NINE.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_NINE, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_NINE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMTen = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_TEN.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_TEN, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_TEN.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMEleven = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_ELEVEN.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_ELEVEN, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_ELEVEN.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdMTwelve = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_MINUS_TWELVE.getNameZone() + getButtonStatus(ZoneId.UTC_MINUS_TWELVE, selectedZoneID))
                .callbackData(ZoneId.UTC_MINUS_TWELVE.getNameZone())
                .build();
        InlineKeyboardButton buttonZoneIdZero = InlineKeyboardButton.builder()
                .text(ZoneId.UTC_ZERO.getNameZone() + getButtonStatus(ZoneId.UTC_ZERO, selectedZoneID))
                .callbackData(ZoneId.UTC_ZERO.getNameZone())
                .build();
        InlineKeyboardButton buttonHome = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_START.getNameUA())
                .callbackData(Buttons.BACK_TO_START.getNameEN())
                .build();
        InlineKeyboardButton buttonBackToSetting = InlineKeyboardButton.builder()
                .text(Buttons.BACK_TO_SETTINGS.getNameUA())
                .callbackData(Buttons.BACK_TO_SETTINGS.getNameEN())
                .build();

        keyboardMZoneIdRow1.add(buttonZoneIdOne);
        keyboardMZoneIdRow1.add(buttonZoneIdTwo);
        keyboardMZoneIdRow1.add(buttonZoneIdThree);
        keyboardMZoneIdRow1.add(buttonZoneIdFour);

        keyboardMZoneIdRow2.add(buttonZoneIdFive);
        keyboardMZoneIdRow2.add(buttonZoneIdSix);
        keyboardMZoneIdRow2.add(buttonZoneIdSeven);
        keyboardMZoneIdRow2.add(buttonZoneIdEight);

        keyboardMZoneIdRow3.add(buttonZoneIdNine);
        keyboardMZoneIdRow3.add(buttonZoneIdTen);
        keyboardMZoneIdRow3.add(buttonZoneIdEleven);
        keyboardMZoneIdRow3.add(buttonZoneIdTwelve);

        keyboardMZoneIdRow4.add(buttonZoneIdMOne);
        keyboardMZoneIdRow4.add(buttonZoneIdMTwo);
        keyboardMZoneIdRow4.add(buttonZoneIdMThree);
        keyboardMZoneIdRow4.add(buttonZoneIdMFour);

        keyboardMZoneIdRow5.add(buttonZoneIdMFive);
        keyboardMZoneIdRow5.add(buttonZoneIdMSix);
        keyboardMZoneIdRow5.add(buttonZoneIdMSeven);
        keyboardMZoneIdRow5.add(buttonZoneIdOMEight);

        keyboardMZoneIdRow6.add(buttonZoneIdMNine);
        keyboardMZoneIdRow6.add(buttonZoneIdMTen);
        keyboardMZoneIdRow6.add(buttonZoneIdMEleven);
        keyboardMZoneIdRow6.add(buttonZoneIdMTwelve);

        keyboardMZoneIdRow7.add(buttonZoneIdZero);

        keyboardMZoneIdRow8.add(buttonHome);
        keyboardMZoneIdRow8.add(buttonBackToSetting);


        keyboardMZoneId.add(keyboardMZoneIdRow1);
        keyboardMZoneId.add(keyboardMZoneIdRow2);
        keyboardMZoneId.add(keyboardMZoneIdRow3);
        keyboardMZoneId.add(keyboardMZoneIdRow4);
        keyboardMZoneId.add(keyboardMZoneIdRow5);
        keyboardMZoneId.add(keyboardMZoneIdRow6);
        keyboardMZoneId.add(keyboardMZoneIdRow7);
        keyboardMZoneId.add(keyboardMZoneIdRow8);

        return InlineKeyboardMarkup.builder().keyboard(keyboardMZoneId).build();
    }


    private String getButtonStatus(Banks current, Banks selected) {
        if (current == selected) {
            return "✅";
        }
        return "";
    }

    private String getButtonStatus(Currency current, List<Currency> selected) {
        if (selected.contains(current)) {
            return "✅";
        }
        return "";
    }

    private String getButtonStatus(NotificationTime current, NotificationTime selected) {
        if (current == selected) {
            return "✅";
        }
        return "";
    }

    private String getButtonStatus(int current, int selected) {
        if (current == selected) {
            return "✅";
        }
        return "";
    }

    private String getButtonStatus(ZoneId current, ZoneId selected) {
        if (current == selected) {
            return "✅";
        }
        return "";
    }

}


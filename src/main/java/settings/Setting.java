package settings;

import java.util.List;

public class Setting {
    private Long chatId;
    private NumberOfDecimalPlaces numberOfDecimalPlaces;
    private Banks selectedBank;
    private List<Currency> selectedCurrency;
    private NotificationTime notificationTime;
    private Language language;

    public Setting() {
    }

    public Setting(Long chatId, NumberOfDecimalPlaces numberOfDecimalPlaces, Banks selectedBank,
                   List<Currency> selectedCurrency, NotificationTime notificationTime, Language language) {
        this.chatId = chatId;
        this.numberOfDecimalPlaces = numberOfDecimalPlaces;
        this.selectedBank = selectedBank;
        this.selectedCurrency = selectedCurrency;
        this.notificationTime = notificationTime;
        this.language = language;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public int getNumberOfDecimalPlaces() {
        return numberOfDecimalPlaces.getIntNumber();
    }

    public void setNumberOfDecimalPlaces(NumberOfDecimalPlaces numberOfDecimalPlaces) {
        this.numberOfDecimalPlaces = numberOfDecimalPlaces;
    }

    public Banks getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(Banks selectedBank) {
        this.selectedBank = selectedBank;
    }

    public List<Currency> getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(List<Currency> selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public NotificationTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(NotificationTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public void setSelectedLanguage(Language language) {
        this.language = language;
    }

    public Language getSelectedLanguage() {
        return language;
    }
}

package settings;

public enum Buttons {
    START("Старт", "/start"),
    GET_INFO("Отримати інфо", "Get info"),
    SETTINGS("Налаштування", "Settings"),
    NUM_DECIMAL_PLACES("Кількість знаків після коми", "Number of decimal places"),
    BANK("Банк", "Bank"),
    CURRENCY("Валюта", "Currency"),
    NOTIFICATION("Час сповіщення", "Notification time"),
    LANGUAGE("Мова", "Language"),
    BACK_TO_SETTINGS("↩️", "Settings"),
    BACK_TO_START("🏠️", "BACK_TO_START");

    private String buttonsNameUA;
    private String buttonsNameEN;

    Buttons(String buttonsNameUA, String buttonsNameEN) {
        this.buttonsNameUA = buttonsNameUA;
        this.buttonsNameEN = buttonsNameEN;
    }

    public String getNameUA() {
        return buttonsNameUA;
    }

    public String getNameEN() {
        return buttonsNameEN;
    }

    public static Buttons convertToEnum(String text) {
        for (Buttons button : Buttons.values()) {
            if (button.getNameEN().equals(text)) {
                return button;
            }
        }
        return null;
    }
}

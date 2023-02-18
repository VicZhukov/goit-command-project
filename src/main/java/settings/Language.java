package settings;

public enum Language {
    EN("English", "EnglishSet", "English \uD83C\uDDFA\uD83C\uDDF8"),
    UA("Ukrainian", "UkrainianSet", "Українська \uD83C\uDDFA\uD83C\uDDE6");

    String langName;
    String langNameSet;
    String langFlag;

    Language(String langName, String langNameSet, String langFlag) {
        this.langName = langName;
        this.langFlag = langFlag;
        this.langNameSet = langNameSet;
    }

    public String getLangName() {
        return langName;
    }

    public String getLangNameSet() {
        return langNameSet;
    }

    public String getLangFlag() {
        return langFlag;
    }

    public static Language convertToEnum(String text) {
        for (Language lang : Language.values()) {
            if (lang.getLangName().equals(text)) {
                return lang;
            }
        }
        return null;
    }

    public static Language convertToEnumSet(String text) {
        for (Language lang : Language.values()) {
            if (lang.getLangNameSet().equals(text)) {
                return lang;
            }
        }
        return null;
    }

    public static String translate(String text, Language language) {
        switch (text) {
            case "Курс купівлі ":
                switch (language) {
                    case EN:
                        return "Purchase fx rate ";
                    default:
                        return text;
                }
            case "Курс продажу ":
                switch (language) {
                    case EN:
                        return "Sales fx rate ";
                    default:
                        return text;
                }
            case "немає купівлі":
                switch (language) {
                    case EN:
                        return "no purchase";
                    default:
                        return text;
                }
            case "немає продажу":
                switch (language) {
                    case EN:
                        return "no sale";
                    default:
                        return text;
                }
            case "Будь ласка впишіть /start або натисніть кнопку.":
                switch (language) {
                    case EN:
                        return "Please type /start or press the button.";
                    default:
                        return text;
                }
            case "Щоб отримати інфо натисність кнопку":
                switch (language) {
                    case EN:
                        return "For get info please press the button";
                    default:
                        return text;
                }
            case "Виберіть налаштування":
                switch (language) {
                    case EN:
                        return "Please choose options";
                    default:
                        return text;
                }
            case "Ласкаво просимо. Цей бот дозволить відслідкувати актуальні курси валют.":
                switch (language) {
                    case EN:
                        return "Welcome. This bot will help you to follow up current fx rates.";
                    default:
                        return text;
                }
            case "Підтвердити":
                switch (language) {
                    case EN:
                        return "Confirm";
                    default:
                        return text;
                }
        }
        return "";
    }
}

package settings;

public enum Banks {
    PRIVAT("ПриватБанк", "PrivatBank", false),
    MONO("МоноБанк", "Monobank", false),
    NBU("НБУ", "NBU", false);

    private String bankNameUA;
    private String bankNameEN;
    private boolean select;

    Banks(String bankNameUA, String bankNameEN, boolean select) {
        this.bankNameUA = bankNameUA;
        this.bankNameEN = bankNameEN;
        this.select = select;
    }

    public String getBankNameEN() {
        return bankNameEN;
    }


    public String getBankNameUA() {
        return bankNameUA;
    }




    public static Banks convertToEnum (String text){
        for (Banks bank: Banks.values()) {
            if (bank.getBankNameEN().equals(text)) {
                return bank;
            }
        }
        return null;
    }



}

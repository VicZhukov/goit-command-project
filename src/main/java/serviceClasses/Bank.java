package serviceClasses;

import settings.Currency;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bank {

    private Float USD_buy = 0.0f;
    private Float USD_sell = 0.0f;
    private Float EUR_buy = 0.0f;
    private Float EUR_sell = 0.0f;
    private LocalDateTime time;


    public void setUSD_buy(Float USD_buy) {
        this.USD_buy = USD_buy;
    }

    public void setUSD_sell(Float USD_sell) {
        this.USD_sell = USD_sell;
    }

    public void setEUR_buy(Float EUR_buy) {
        this.EUR_buy = EUR_buy;
    }

    public void setEUR_sell(Float EUR_sell) {
        this.EUR_sell = EUR_sell;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(USD_buy, bank.USD_buy)
                && Objects.equals(USD_sell, bank.USD_sell)
                && Objects.equals(EUR_buy, bank.EUR_buy)
                && Objects.equals(EUR_sell, bank.EUR_sell)
                && Objects.equals(time, bank.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(USD_buy, USD_sell, EUR_buy, EUR_sell, time);
    }

    @Override
    public String toString() {
        return "Bank{" +
                ", USD_buy=" + USD_buy +
                ", USD_sell=" + USD_sell +
                ", EUR_buy=" + EUR_buy +
                ", EUR_sell=" + EUR_sell +
                ", time=" + time +
                '}';
    }

    public Float getBuyRate (Currency currency){
        switch (currency){
            case EUR:
                return this.EUR_buy;
            case USD:
                return this.USD_buy;
        }
        return null;
    }

    public Float getSellRate (Currency currency){
        switch (currency){
            case EUR:
                return this.EUR_sell;
            case USD:
                return this.USD_sell;
        }
        return null;
    }
}

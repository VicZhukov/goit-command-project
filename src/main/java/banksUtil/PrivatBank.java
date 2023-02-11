package banksUtil;

import java.util.Objects;

public class PrivatBank {
    private String ccy;
    private String base_ccy;
    private float buy;
    private float sale;

    public String getCcy() {
        return ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public float getSale() {
        return sale;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivatBank)) return false;
        PrivatBank privatBank = (PrivatBank) o;
        return Objects.equals(ccy, privatBank.ccy) && Objects.equals(base_ccy, privatBank.base_ccy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccy, base_ccy);
    }

    @Override
    public String toString() {
        return "PrivatBank{" +
                "ccy='" + ccy + '\'' +
                ", base_ccy='" + base_ccy + '\'' +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
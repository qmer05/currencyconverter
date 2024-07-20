package app.currencyconverter;

public class CurrencyConverter {

    public static double USDtoEUR(double USD){
        return USD * 0.92;
    }

    public static double EURtoUSD(double EUR){
        return EUR * 1.09;
    }

    public static double EURtoGBP(double EUR){
        return EUR * 0.84;
    }

    public static double GBPtoEUR(double GBP){
        return GBP * 1.19;
    }

    public static double GBPtoUSD(double GBP){
        return GBP * 1.3;
    }

    public static double USDtoGBP(double USD){
        return USD * 0.77;
    }

}

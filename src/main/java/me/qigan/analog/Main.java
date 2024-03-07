package me.qigan.analog;

public class Main {

    public static final MainWind wind = new MainWind();

    public static void main(String[] args) {
        String nstr = "[101] NewtonsBinominal \u00CA (Healer XVIII)";
        char c = nstr.substring(nstr.indexOf("(") + 1).toCharArray()[0];
        System.out.println(c);
        wind.setVisible(true);
    }
}

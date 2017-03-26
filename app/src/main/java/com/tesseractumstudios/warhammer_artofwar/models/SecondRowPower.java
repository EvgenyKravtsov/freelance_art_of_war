package com.tesseractumstudios.warhammer_artofwar.models;

public class SecondRowPower extends PsykerPower {
    private String rowTitle;
    private String secondRowTitle;
    private String range2;
    private String s2;
    private String ap2;
    private String type2;

    public SecondRowPower(String description, String range, String title, String type,
                          boolean isPrimaris, String ap, String s, String rowTitle,
                          String secondRowTitle, String range2, String s2, String ap2,
                          String type2) {
        super(description, range, title, type, isPrimaris, ap, s);
        this.rowTitle = rowTitle;
        this.secondRowTitle = secondRowTitle;
        this.range2 = range2;
        this.s2 = s2;
        this.ap2 = ap2;
        this.type2 = type2;
    }

    public String getRowTitle() {
        return rowTitle;
    }

    public String getSecondRowTitle() {
        return secondRowTitle;
    }

    public String getRange2() {
        return range2;
    }

    public String getS2() {
        return s2;
    }

    public String getAp2() {
        return ap2;
    }

    public String getType2() {
        return type2;
    }
}

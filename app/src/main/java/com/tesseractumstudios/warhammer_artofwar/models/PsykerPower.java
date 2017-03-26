package com.tesseractumstudios.warhammer_artofwar.models;

public class PsykerPower {
    private String ap;
    private String description;
    private String range;
    private String s;
    private String title;
    private String type;
    private boolean haveTable;
    private boolean isPrimaris;

    public PsykerPower(String description, String title, boolean isPrimaris) {
        this.description = description;
        this.title = title;
        this.haveTable = false;
        this.isPrimaris = isPrimaris;
    }

    public PsykerPower(String description, String range, String title, String type,
                       boolean isPrimaris, String ap, String s) {
        this.description = description;
        this.range = range;
        this.title = title;
        this.type = type;
        this.haveTable = true;
        this.isPrimaris = isPrimaris;
        this.ap = ap;
        this.s = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHaveTable() {
        return haveTable;
    }

    public void setHaveTable(boolean haveTable) {
        this.haveTable = haveTable;
    }

    public boolean isPrimaris() {
        return isPrimaris;
    }

    public void setIsPrimaris(boolean isPrimaris) {
        this.isPrimaris = isPrimaris;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PsykerPower)) return false;

        PsykerPower power = (PsykerPower) o;

        if (!description.equals(power.description)) return false;

        return title.equals(power.title);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
}

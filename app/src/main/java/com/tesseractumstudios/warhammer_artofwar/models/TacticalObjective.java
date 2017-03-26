package com.tesseractumstudios.warhammer_artofwar.models;

public class TacticalObjective {
    private String title;
    private String type;
    private String description;
    private boolean oneVp;
    private boolean d3;
    private boolean d3Plus3;

    public TacticalObjective(String title, String type, String description,
                             boolean oneVp, boolean d3, boolean d3Plus3) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.oneVp = oneVp;
        this.d3 = d3;
        this.d3Plus3 = d3Plus3;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOneVp() {
        return oneVp;
    }

    public boolean isD3() {
        return d3;
    }

    public boolean isD3Plus3() {
        return d3Plus3;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof TacticalObjective) ) return false;

        TacticalObjective that = (TacticalObjective) o;

        return !(title != null ? !title.equals(that.title) : that.title != null);

    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}

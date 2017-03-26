package com.tesseractumstudios.warhammer_artofwar.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Psyker {
    private Map<String, ArrayList<PsykerPower>>     powers;
    private String                                  name;
    private int                                     masteryLvl;
    private Map<String, Integer>                    powerCounters;

    public Psyker() {
    }

    public Psyker(String name, int masteryLvl, Map<String, ArrayList<PsykerPower>> powers) {
        this.masteryLvl = masteryLvl;
        this.name       = name;
        this.powers     = powers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMasteryLvl() {
        return masteryLvl;
    }

    public void setMasteryLvl(int masteryLvl) {
        this.masteryLvl = masteryLvl;
    }

    public Map<String, ArrayList<PsykerPower>> getPowers() {
        return powers;
    }

    public void setPowers(Map<String, ArrayList<PsykerPower>> powers) {
        this.powers = powers;
    }

    public Map<String, Integer> getPowerCounters() {
        return powerCounters;
    }

    public void setPowerCounters(Map<String, Integer> powerCounters) {
        this.powerCounters = powerCounters;
    }

    public ArrayList<PsykerPower> getAllPowers() {
        ArrayList<PsykerPower> powersList = new ArrayList<>();

        for ( String school : powers.keySet() ) {
            powersList.addAll(powers.get(school));
        }

        return powersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Psyker)) return false;

        Psyker psyker = (Psyker) o;

        if (masteryLvl != psyker.masteryLvl) return false;
        return name.equals(psyker.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + masteryLvl;
        return result;
    }
}

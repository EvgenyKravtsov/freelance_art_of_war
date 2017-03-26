package com.tesseractumstudios.warhammer_artofwar.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialPsyker extends Psyker {
    private ArrayList<PsykerPower>  knownPowers;
    private ArrayList<String>       knownSchools;
    private ArrayList<String>       generateFrom;
    private boolean                 canRerollGeneratedPowers;
    private boolean                 mustChooseKnownOrGenerated;

    public SpecialPsyker() {
    }

    public SpecialPsyker(String name, int masteryLvl, Map<String, ArrayList<PsykerPower>> powers,
                         ArrayList<PsykerPower> knownPowers, ArrayList<String> knownSchools,
                         ArrayList<String> generateFrom, boolean canRerollGeneratedPowers,
                         boolean mustChooseKnownOrGenerated) {
        super(name, masteryLvl, powers);
        this.knownPowers = knownPowers;
        this.knownSchools = knownSchools;
        this.generateFrom = generateFrom;
        this.canRerollGeneratedPowers = canRerollGeneratedPowers;
        this.mustChooseKnownOrGenerated = mustChooseKnownOrGenerated;
    }

    public ArrayList<PsykerPower> getKnownPowers() {
        return knownPowers;
    }

    public void setKnownPowers(ArrayList<PsykerPower> knownPowers) {
        this.knownPowers = knownPowers;
    }

    public ArrayList<String> getGenerateFrom() {
        return generateFrom;
    }

    public void setGenerateFrom(ArrayList<String> generateFrom) {
        this.generateFrom = generateFrom;
    }

    public boolean isCanRerollGeneratedPowers() {
        return canRerollGeneratedPowers;
    }

    public void setCanRerollGeneratedPowers(boolean canRerollGeneratedPowers) {
        this.canRerollGeneratedPowers = canRerollGeneratedPowers;
    }

    public boolean isMustChooseKnownOrGenerated() {
        return mustChooseKnownOrGenerated;
    }

    public void setMustChooseKnownOrGenerated(boolean mustChooseKnownOrGenerated) {
        this.mustChooseKnownOrGenerated = mustChooseKnownOrGenerated;
    }

    public ArrayList<String> getKnownSchools() {
        return knownSchools;
    }

    public void setKnownSchools(ArrayList<String> knownSchools) {
        this.knownSchools = knownSchools;
    }
}

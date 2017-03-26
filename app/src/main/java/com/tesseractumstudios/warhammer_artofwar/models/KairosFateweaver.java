package com.tesseractumstudios.warhammer_artofwar.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KairosFateweaver extends SpecialPsyker {
    public KairosFateweaver(String name, int masteryLvl, Map<String, ArrayList<PsykerPower>> powers,
                            ArrayList<PsykerPower> knownPowers, ArrayList<String> knownSchools,
                            ArrayList<String> generateFrom, boolean canRerollGeneratedPowers,
                            boolean mustChooseKnownOrGenerated) {
        super(name, masteryLvl, powers, knownPowers, knownSchools, generateFrom,
                canRerollGeneratedPowers, mustChooseKnownOrGenerated);
    }

    public void preparePowerCounters() {
        Map<String, Integer> powerCounters = new HashMap<>();
        List<String> generationSchoolsList = this.getGenerateFrom();

        for ( String school : generationSchoolsList ) {
            powerCounters.put(school, 1);
        }

        this.setPowerCounters(powerCounters);
    }
}

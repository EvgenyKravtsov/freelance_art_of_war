package com.tesseractumstudios.warhammer_artofwar.models;

import java.util.ArrayList;
import java.util.Map;

public class Coteaz extends SpecialPsyker {
    public Coteaz(String name, int masteryLvl, Map<String, ArrayList<PsykerPower>> powers,
                  ArrayList<PsykerPower> knownPowers, ArrayList<String> knownSchools,
                  ArrayList<String> generateFrom, boolean canRerollGeneratedPowers,
                  boolean mustChooseKnownOrGenerated) {
        super(name, masteryLvl, powers, knownPowers, knownSchools, generateFrom,
                canRerollGeneratedPowers, mustChooseKnownOrGenerated);
    }
}

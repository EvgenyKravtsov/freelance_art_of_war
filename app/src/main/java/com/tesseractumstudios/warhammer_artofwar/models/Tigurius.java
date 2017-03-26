package com.tesseractumstudios.warhammer_artofwar.models;

import com.tesseractumstudios.warhammer_artofwar.tools.PowersGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tigurius extends SpecialPsyker {
    boolean isRerolled;

    public Tigurius(String name, int masteryLvl, Map<String, ArrayList<PsykerPower>> powers,
                    ArrayList<PsykerPower> knownPowers, ArrayList<String> knownSchools,
                    ArrayList<String> generateFrom, boolean canRerollGeneratedPowers,
                    boolean mustChooseKnownOrGenerated) {
        super(name, masteryLvl, powers, knownPowers, knownSchools, generateFrom,
                canRerollGeneratedPowers, mustChooseKnownOrGenerated);

        this.isRerolled = false;
    }

    public boolean isRelolled() {
        return isRerolled;
    }

    public void rerollPowers(ArrayList<PsykerPower> selectedPowers) {
        for ( PsykerPower power : selectedPowers ) {
            findAndRerolPower(power);
        }

        isRerolled = true;
    }

    private void findAndRerolPower(PsykerPower power) {
        Map<String, ArrayList<PsykerPower>> psykerPowerMap = getPowers();

        for ( Map.Entry<String, ArrayList<PsykerPower>> entry : psykerPowerMap.entrySet() ) {
            if (entry.getValue().contains(power)) {
                rerollOnePower(entry, power);
            }
        }
    }

    private void rerollOnePower(Map.Entry<String, ArrayList<PsykerPower>> entry,
                                PsykerPower oldPower) {
        Map<String, Integer>    powerCounters   = new HashMap<>();
        PsykerPower             generatedPower;
        String                  powerSchool     = entry.getKey();
        ArrayList<PsykerPower>  powersInSchool  = entry.getValue();
        int                     indexPosition   = powersInSchool.indexOf(oldPower);

        powerCounters.put(powerSchool, 1);
        generatedPower = PowersGenerator.getInstance().generatePowers(2, powerCounters)
                .get(powerSchool).get(0);

        for ( ; powersInSchool.contains(generatedPower) && !generatedPower.equals(oldPower); ) {
            generatedPower = PowersGenerator.getInstance().generatePowers(2, powerCounters)
                    .get(powerSchool).get(0);
        }

        powersInSchool.remove(oldPower);
        powersInSchool.add(indexPosition, generatedPower);
    }

    private boolean isPowerContains(PsykerPower power) {
        return getAllPowers().contains(power);
    }
}

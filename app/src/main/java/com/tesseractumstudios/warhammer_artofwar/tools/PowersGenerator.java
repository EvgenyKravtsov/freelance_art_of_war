package com.tesseractumstudios.warhammer_artofwar.tools;

import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PowersGenerator {
    private static volatile PowersGenerator instance;

    private Map<String, ArrayList<PsykerPower>> allPowers;

    public static PowersGenerator getInstance() {
        PowersGenerator localInstance = instance;

        if ( localInstance == null ) {
            synchronized ( PowersGenerator.class ) {
                localInstance = instance;
                if ( localInstance == null ) {
                    instance = localInstance = new PowersGenerator();
                }
            }
        }

        return localInstance;
    }

    public PowersGenerator() {
        this.allPowers = fillAllPowers();
    }

    public Map<String, ArrayList<PsykerPower>> generatePowers(Integer masteryLvl,
            Map<String, Integer> powerCounters ) {
        Map<String, ArrayList<PsykerPower>> powers = new HashMap<>();

        for ( Map.Entry<String, Integer> entry : powerCounters.entrySet() ) {
            int                     counter             = entry.getValue();

            if ( counter > 0 ) {
                ArrayList<PsykerPower>  powerArrayList      = allPowers.get(entry.getKey());
                ArrayList<Integer>      addedPowersIndexes  = new ArrayList<>();
                ArrayList<PsykerPower>  addedPowers         = new ArrayList<>();
                int                     powersSize          = powerArrayList.size();

                if ( counter == masteryLvl ) {
                    for (int i = 0; i < powersSize; i++ ) {
                        PsykerPower selectedPower = powerArrayList.get(i);

                        if ( selectedPower.isPrimaris() ) {
                            addedPowers.add(selectedPower);
                            addedPowersIndexes.add(i);
                            break;
                        }
                    }
                }

                for ( int i = 0; i < counter; i++ ) {
                    int         randomNumber  = getRandomNumber(powersSize);
                    PsykerPower selectPower   = powerArrayList.get(randomNumber);

                    for ( ; addedPowersIndexes.contains(randomNumber)
                            || selectPower.isPrimaris(); ) {
                        randomNumber    = getRandomNumber(powersSize);
                        selectPower     = powerArrayList.get(randomNumber);
                    }

                    addedPowers.add(powerArrayList.get(randomNumber));
                    addedPowersIndexes.add(randomNumber);
                }

                if ( addedPowers.size() > 0 ) {
                    powers.put(entry.getKey(), addedPowers);
                }
            }
        }

        return powers;
    }

    public Map<String, ArrayList<PsykerPower>> generateFromPowerList(Integer masteryLvl,
                                                        ArrayList<String> powerScools) {
        Map<String, Integer>    powerCounters       = new HashMap<>();

        for ( String powerSchool : powerScools ) {
            powerCounters.put(powerSchool, 0);
        }

        for ( int scoreLeft = masteryLvl; scoreLeft > 0; ) {
            String  powerSchool = powerScools.get(getRandomNumber(powerScools.size()));
            int     score       = getRandomNumber(
                                    getMin(allPowers.get(powerSchool).size(), scoreLeft + 1));

            powerCounters.put(powerSchool, powerCounters.get(powerSchool) + score);

            scoreLeft -= score;
        }

        return generatePowers(masteryLvl, powerCounters);
    }

    public Map<String, ArrayList<PsykerPower>> getAllPowers() {
        return allPowers;
    }

    private int getRandomNumber(int limit) {
        return ((Double) (Math.random() * limit)).intValue();
    }

    private Map<String, ArrayList<PsykerPower>> fillAllPowers() {
        Map<String, ArrayList<PsykerPower>> powers;

        try {
            JSONObject powersData = new JSONObject(readJsonStringFromAssets());

            powers = JsonWorker.getPowersMapFromJson(powersData);
        } catch (JSONException e) {
            e.printStackTrace();
            powers = new HashMap<>();
        }

        return powers;
    }

    private String readJsonStringFromAssets() {
        String      jsonString;
        InputStream is          = null;

        try {
            int     size;
            byte[]  buffer;

            is      =  WarhammerArtofwar.getAppContext().getAssets()
                            .open("app_data/randomizer/powers.json");
            size    = is.available();
            buffer  = new byte[size];

            is.read(buffer);
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();

            return "{}";
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonString;
    }

    private int getMin(int a, int b) {
        if ( a < b ) {
            return a;
        }

        return b;
    }
}

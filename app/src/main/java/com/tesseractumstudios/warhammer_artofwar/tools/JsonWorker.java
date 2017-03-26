package com.tesseractumstudios.warhammer_artofwar.tools;

import android.util.Log;

import com.tesseractumstudios.warhammer_artofwar.models.Champion;
import com.tesseractumstudios.warhammer_artofwar.models.Coteaz;
import com.tesseractumstudios.warhammer_artofwar.models.KairosFateweaver;
import com.tesseractumstudios.warhammer_artofwar.models.Psyker;
import com.tesseractumstudios.warhammer_artofwar.models.PsykerPower;
import com.tesseractumstudios.warhammer_artofwar.models.SecondRowPower;
import com.tesseractumstudios.warhammer_artofwar.models.SpecialPsyker;
import com.tesseractumstudios.warhammer_artofwar.models.SpectablePower;
import com.tesseractumstudios.warhammer_artofwar.models.TacticalObjective;
import com.tesseractumstudios.warhammer_artofwar.models.Tigurius;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonWorker {
    private static final String TAG = "JsonWorker";

    public static ArrayList<TacticalObjective> getObjectivesListFromJson(JSONArray list) {
        ArrayList<TacticalObjective>    objectiveList   = new ArrayList<>();
        int                             size            = list.length();

        for ( int i = 0; i < size; i++ ) {
            try {
                objectiveList.add(getObjectiveFromJson(list.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return objectiveList;
    }

    public static JSONArray getJsonArrayObjectives(ArrayList<TacticalObjective> arrayList) {
        JSONArray   jsonArray   = new JSONArray();
        int         size        = arrayList.size();

        for ( int i = 0; i < size; i++ ) {
            try {
                jsonArray.put(getJsonTacticalObjective(arrayList.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public static ArrayList<Champion> getChampionsListFromJson(JSONArray jsonArray) {
        ArrayList<Champion> championsList = new ArrayList<>();
        int                 size            = jsonArray.length();

        for ( int i = 0; i < size; i++ ) {
            try {
                championsList.add(getChampionFromJson(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return championsList;
    }

    public static JSONArray getJsonArrayChampions(ArrayList<Champion> arrayList) {
        JSONArray   jsonArray   = new JSONArray();
        int         size        = arrayList.size();

        for ( int i = 0; i < size; i ++ ) {
            try {
                jsonArray.put(getJsonChampion(arrayList.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public static Map<String, ArrayList<PsykerPower>> getPowersMapFromJson(JSONObject data) {
        Map<String, ArrayList<PsykerPower>> map         = new HashMap<>();

        for ( Iterator<String> iterator = data.keys(); iterator.hasNext(); ) {
            try {
                String school = capitalizeString(iterator.next());

                map.put(school, getArrayListOfPower(data.getJSONArray(school.toLowerCase())));
            } catch (JSONException e) {
                e.printStackTrace();

                map = new HashMap<>();
            }
        }

        return map;
    }

    public static JSONArray getJsonArrayPsykers(ArrayList<Psyker> psykerArrayList) {
        JSONArray   jsonArray   = new JSONArray();
        int         length      = psykerArrayList.size();

        for ( int i = 0; i < length; i++ ) {
            try {
                jsonArray.put(getJsonPsyker(psykerArrayList.get(i)));
            } catch (JSONException e) {
                Log.e(TAG, "Can't save psyker " + psykerArrayList.get(i).getName());
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    public static ArrayList<Psyker> getPsykersFromJson(JSONArray jsonPsykers) {
        ArrayList<Psyker>   psykers = new ArrayList<>();
        int                 length  = jsonPsykers.length();

        for ( int i = 0; i < length; i++ ) {
            try {
                psykers.add(getPsykerFromJson(jsonPsykers.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return psykers;
    }

    public static ArrayList<SpecialPsyker> getSpecialPsykersFromJson(JSONObject jsonSpecisalPsykers) {
        ArrayList<SpecialPsyker> specialPsykers = new ArrayList<>();

        for ( Iterator<String> iterator = jsonSpecisalPsykers.keys(); iterator.hasNext(); ) {
            try {
                JSONObject jsonPsyker = jsonSpecisalPsykers.getJSONObject(iterator.next());
                SpecialPsyker psyker = getSpecialPsykerFromJson(jsonPsyker);

                specialPsykers.add(psyker);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return specialPsykers;
    }

    private static SpecialPsyker getSpecialPsykerFromJson(JSONObject jsonPsyker) throws JSONException {
        String                              name;
        int                                 masteryLvl;
        Map<String, ArrayList<PsykerPower>> powers;
        ArrayList<PsykerPower>              knownPowers     = new ArrayList<>();
        ArrayList<String>                   generateFrom;
        ArrayList<String>                   knownSchools    = new ArrayList<>();

        name            = jsonPsyker.getString("name");
        masteryLvl      = jsonPsyker.getInt("masteryLvl");
        generateFrom    = fillGenerateFromList(jsonPsyker);
        powers          = new HashMap<>();
        try {
            knownPowers     = getKnownPowersFromJson(jsonPsyker.getJSONArray("known_powers"));
        } catch (JSONException e ) {
            //NOP
        }


        if ( jsonPsyker.has("known_schools") ) {
            knownSchools.add(jsonPsyker.optString("known_schools", ""));

            return new KairosFateweaver(name, masteryLvl, powers, knownPowers, knownSchools,
                    generateFrom, false, false);
        }
        if ( jsonPsyker.has("single_reroll") ) {
           return new Tigurius(name, masteryLvl, powers, knownPowers, knownSchools, generateFrom,
                   true, false);
        }
        if ( jsonPsyker.has("choose_sourse") ) {
            return new Coteaz(name, masteryLvl, powers, knownPowers, knownSchools, generateFrom,
                    false, true);
        }

        return new SpecialPsyker(name, masteryLvl, powers, knownPowers, knownSchools, generateFrom,
                false, false);
    }

    private static ArrayList<String> fillGenerateFromList(JSONObject jsonPsyker) {
        ArrayList<String> generateList = new ArrayList<>();

        try {
            JSONArray   jsonArray   = jsonPsyker.getJSONArray("generate_from");
            int         length      = jsonArray.length();

            for ( int i = 0; i < length; i++ ) {
                generateList.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return generateList;
    }

    private static ArrayList<PsykerPower> getKnownPowersFromJson(JSONArray jsonKnown_powers) {
        ArrayList<String>       knownPowersName = new ArrayList<>();
        ArrayList<PsykerPower>  psykerPowers    = new ArrayList<>();
        int                     length          = jsonKnown_powers.length();
        JSONObject              specialPowers;

        try {
            specialPowers = new JSONObject(AppDelegate.getInstance()
                    .readStringFromAssets("app_data/randomizer/special_powers.json"));
        } catch (JSONException e) {
            return psykerPowers;
        }

        for ( int i = 0; i < length; i++ ) {
            try {
                knownPowersName.add(jsonKnown_powers.getString(i));
            } catch (JSONException e) {
                //NOP
            }
        }

        for ( String power : knownPowersName ) {
            try {
                JSONObject jsonPower = specialPowers.getJSONObject(power);

                psykerPowers.add(new PsykerPower(
                        jsonPower.getString("descr"),
                        jsonPower.getString("tile"), true));
            } catch (JSONException e) {
                //NOP
            }
        }

        return psykerPowers;
    }

    private static Psyker getPsykerFromJson(JSONObject jsonPsyker) throws JSONException {
        Psyker psyker = new Psyker();

        psyker.setName(jsonPsyker.getString("name"));
        psyker.setMasteryLvl(jsonPsyker.getInt("masteryLvl"));
        psyker.setPowers(getPowersMapFromJson(jsonPsyker.getJSONObject("powers")));

        return psyker;
    }

    private static JSONObject getJsonPsyker(Psyker psyker) throws JSONException {
        JSONObject jsonPsyker = new JSONObject();

        jsonPsyker.put("name", psyker.getName());
        jsonPsyker.put("masteryLvl", psyker.getMasteryLvl());
        jsonPsyker.put("powers", getJsonObjectFromPowersMap(psyker.getPowers()));

        return jsonPsyker;
    }

    private static JSONObject getJsonObjectFromPowersMap(
            Map<String, ArrayList<PsykerPower>> powers) throws JSONException {
        JSONObject jsonPowers = new JSONObject();

        for ( Map.Entry<String, ArrayList<PsykerPower>> entry : powers.entrySet() ) {
            JSONArray               jsonPowersArray     = new JSONArray();
            ArrayList<PsykerPower>  schoolPowersList    = entry.getValue();

            for ( PsykerPower psykerPower : schoolPowersList ) {
                jsonPowersArray.put(getJsonPsykerPower(psykerPower));
            }

            jsonPowers.put(entry.getKey().toLowerCase(), jsonPowersArray);
        }

        return jsonPowers;
    }

    private static JSONObject getJsonPsykerPower(PsykerPower psykerPower) throws JSONException {
        JSONObject jsonPsykerPower = new JSONObject();

        jsonPsykerPower.put("title",        psykerPower.getTitle());
        jsonPsykerPower.put("descr",        psykerPower.getDescription());
        jsonPsykerPower.put("isPrimaris",   psykerPower.isPrimaris());
        jsonPsykerPower.put("have_table",    psykerPower.isHaveTable());

        if ( psykerPower.isHaveTable() ) {
            jsonPsykerPower.put("table_range",    psykerPower.getRange());
            jsonPsykerPower.put("table_s",        psykerPower.getS());
            jsonPsykerPower.put("table_ap",       psykerPower.getAp());
            jsonPsykerPower.put("table_type",     psykerPower.getType());

            if ( psykerPower instanceof SecondRowPower ) {
                SecondRowPower secondRowPower = (SecondRowPower) psykerPower;

                jsonPsykerPower.put("have_second_table_row", true);
                jsonPsykerPower.put("table_row_title", secondRowPower.getRowTitle());
                jsonPsykerPower.put("table_row_title2", secondRowPower.getSecondRowTitle());
                jsonPsykerPower.put("table_range2", secondRowPower.getRange2());
                jsonPsykerPower.put("table_s2", secondRowPower.getS2());
                jsonPsykerPower.put("table_ap2", secondRowPower.getAp2());
                jsonPsykerPower.put("table_type2", secondRowPower.getType2());
            }
        } else if ( psykerPower instanceof SpectablePower ) {
            jsonPsykerPower.put("have_spec_table", true);
            //Add saving spec table params if be more table data
        }

        return jsonPsykerPower;
    }

    private static ArrayList<PsykerPower> getArrayListOfPower(JSONArray powerArray)
            throws JSONException {
        ArrayList<PsykerPower>  powerArrayList  = new ArrayList<>();
        int                     length          = powerArray.length();

        for ( int i = 0; i < length; i++ ) {
            powerArrayList.add(getPsykerPowerFromJson(powerArray.getJSONObject(i)));
        }

        return powerArrayList;
    }

    private static PsykerPower getPsykerPowerFromJson(JSONObject jsonPower) {
        String      title       = jsonPower.optString("title", "");
        String      description = jsonPower.optString("descr", "");
        boolean     isPrimaris  = jsonPower.optBoolean("isPrimaris", false);
        boolean     haveTable   = jsonPower.optBoolean("have_table", false);
        boolean     haveSecond  = jsonPower.optBoolean("have_second_table_row", false);
        boolean     haveSpec    = jsonPower.optBoolean("have_spec_table", false);

        if ( haveTable ) {
            String range    = jsonPower.optString("table_range", "-");
            String s        = jsonPower.optString("table_s", "-");
            String ap       = jsonPower.optString("table_ap", "-");
            String type     = jsonPower.optString("table_type", "-");

            if ( haveSecond ) {
                String rowTitle  = jsonPower.optString("table_row_title", "-");
                String row2Title = jsonPower.optString("table_row_title2", "-");
                String range2    = jsonPower.optString("table_range2", "-");
                String s2        = jsonPower.optString("table_s2", "-");
                String ap2       = jsonPower.optString("table_ap2", "-");
                String type2     = jsonPower.optString("table_type2", "-");

                return new SecondRowPower(description, range, title, type, isPrimaris, ap, s,
                        rowTitle, row2Title, range2, s2, ap2, type2);
            }

            return new PsykerPower(description, range, title, type, isPrimaris, ap, s);
        } else if ( haveSpec ) {
            return new SpectablePower(description, title, isPrimaris, new ArrayList<String>(),
                    new ArrayList<String>());
        }

        return new PsykerPower(description, title, isPrimaris);
    }

    private static JSONObject getJsonChampion(Champion champion) throws JSONException {
        JSONObject  jsonChampion    = new JSONObject();
        int         rewardsSize     = champion.getRewards().size();
//        String[]    rewards         = champion.getRewards().toArray(new String[rewardsSize]);

        jsonChampion.put("name", champion.getName());
//        jsonChampion.put("rewards", Arrays.toString(rewards));
        jsonChampion.put("rewards", new JSONArray(champion.getRewards()));

        return jsonChampion;
    }

    private static Champion getChampionFromJson(JSONObject jsonChampion) throws JSONException {
        String              name            = jsonChampion.getString("name");
        ArrayList<String>   rewardsList     = new ArrayList<>();
        JSONArray           jsonRewards     = jsonChampion.getJSONArray("rewards");
        int                 rewardsCount    = jsonRewards.length();

        for ( int i = 0; i < rewardsCount; i++ ) {
            rewardsList.add(jsonRewards.getString(i));
        }

        return new Champion(name, rewardsList);
    }

    private static JSONObject getJsonTacticalObjective(TacticalObjective tacticalObjective)
            throws JSONException {
        JSONObject objective = new JSONObject();

        objective.put("title", tacticalObjective.getTitle());
        objective.put("type", tacticalObjective.getType());
        objective.put("descr", tacticalObjective.getDescription());
        objective.put("one_vp", tacticalObjective.isOneVp());
        objective.put("d3", tacticalObjective.isD3());
        objective.put("d3_plus_3", tacticalObjective.isD3Plus3());

        return objective;
    }

    private static TacticalObjective getObjectiveFromJson(JSONObject jsonObjective)
            throws JSONException {

        return new TacticalObjective(jsonObjective.getString("title"),
                jsonObjective.getString("type"),
                jsonObjective.getString("descr"),
                jsonObjective.getBoolean("one_vp"),
                jsonObjective.getBoolean("d3"),
                jsonObjective.getBoolean("d3_plus_3"));
    }

    private static String capitalizeString(String string) {
        char[]  chars   = string.toLowerCase().toCharArray();
        boolean found   = false;
        int     length  = chars.length;

        for (int i = 0; i < length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}

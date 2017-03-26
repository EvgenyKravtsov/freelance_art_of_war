package com.tesseractumstudios.warhammer_artofwar.models;

import java.util.ArrayList;

public class Champion {
    private String name;
    private ArrayList<String> rewards;

    public Champion() {
        this("testName");
        ArrayList<String> rewards = new ArrayList<>();
        rewards.add("test1");
        rewards.add("test2");
        rewards.add("test3");

        this.rewards = rewards;
    }

    public Champion(String name) {
        this(name, new ArrayList<String>());
    }

    public Champion(String name, ArrayList<String> rewards) {
        this.name = name;
        this.rewards = rewards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<String> rewards) {
        this.rewards = rewards;
    }
}

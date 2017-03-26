package com.tesseractumstudios.warhammer_artofwar.models;

import java.util.ArrayList;

public class SpectablePower extends PsykerPower {
    private ArrayList<String> headers;
    private ArrayList<String> data;

    public SpectablePower(String description, String title, boolean isPrimaris,
                          ArrayList<String> headers, ArrayList<String> data) {
        super(description, title, isPrimaris);
        this.headers = headers;
        this.data = data;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public ArrayList<String> getData() {
        return data;
    }
}

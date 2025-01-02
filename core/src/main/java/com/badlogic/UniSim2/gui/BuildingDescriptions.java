package com.badlogic.UniSim2.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.UniSim2.core.buildings.BuildingType;

public class BuildingDescriptions {

    private final TextboxActor descriptionTextboxActor;
    private final Skin skin;

    public BuildingDescriptions(Skin skin) {
        this.skin = skin;
        Textbox descriptionTextbox = new Textbox("", 87, 39, 200); // Adjust width as needed
        this.descriptionTextboxActor = new TextboxActor(descriptionTextbox);
        this.descriptionTextboxActor.setVisible(false);
    }

    public void showDescription(BuildingType type) {
        String description = getDescription(type);
        descriptionTextboxActor.setText(description);
        descriptionTextboxActor.setVisible(true);
    }

    public void hideDescription() {
        descriptionTextboxActor.setVisible(false);
    }

    private String getDescription(BuildingType type) {
        switch (type) {
            case ACCOMMODATION:
                return "Accommodation: A place for your students to stay.";
            case BAR:
                return "Bar: A place for your students to drink and socialize.";
            case COURSE:
                return "Course: A place for your students to learn.";
            case FOODZONE:
                return "Food Zone: A place for your students to eat.";
            case LECTUREHALL:
                return "Lecture Hall: A place for your students lectures.";
            case LIBRARY:
                return "Library: A place for your students to read and study.";
            case NATURE:
                return "Nature: A place for your students to relax and enjoy nature.";
            default:
                return "Unknown building type.";
        }
    }

    public TextboxActor getDescriptionTextboxActor() {
        return descriptionTextboxActor;
    }
}
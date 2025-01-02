package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class Menu {

    private final Skin skin;
    private final TimerLabel timerLabel;
    private final Array<BuildingButton> buildingButtons;
    private final Round round;
    private final Stage stage;
    private final BuildingDescriptions buildingDescriptions;

    public Menu(Stage stage, Round round) {
        this.stage = stage;
        this.round = round;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.buildingButtons = new Array<>();
        this.buildingDescriptions = new BuildingDescriptions(skin);

        Image menuBar = createMenuBar();
        this.timerLabel = new TimerLabel(round, skin);

        stage.addActor(menuBar);
        stage.addActor(timerLabel);
        stage.addActor(buildingDescriptions.getDescriptionTextboxActor());
        createBuildingButtons();
    }

    public void update() {
        timerLabel.update();

        for (BuildingButton button : buildingButtons) {
            button.update();
        }
    }

    private Image createMenuBar() {
        Image b = new Image(Assets.menuBarTexture);
        b.setSize(Consts.MENU_BAR_WIDTH, Consts.MENU_BAR_HEIGHT);
        b.setPosition(Consts.MENU_BAR_X, Consts.MENU_BAR_Y);
        return b;
    }

    private void createBuildingButtons() {
        int buttonGap = Consts.BUILDING_BUTTON_GAP;
        int yPosition = Consts.BUILDING_BUTTON_Y_BOUNDARY;

        for (BuildingType type : BuildingType.values()) {
            BuildingButton button = new BuildingButton(
                    type,
                    round,
                    yPosition - buttonGap,
                    skin,
                    this::handleBuildingSelected,
                    buildingDescriptions
            );

            buildingButtons.add(button);
            stage.addActor(button);
            stage.addActor(button.getCountLabel());
            yPosition -= buttonGap;
        }
    }

    private void handleBuildingSelected(BuildingType type) {
        SoundManager.playClick();
        round.selectBuildingType(type);
    }
}

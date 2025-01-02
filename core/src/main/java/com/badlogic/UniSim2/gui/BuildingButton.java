package com.badlogic.UniSim2.gui;

import com.badlogic.UniSim2.core.Round;
import com.badlogic.UniSim2.core.buildings.BuildingType;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BuildingButton extends ImageButton {

    public interface SelectionListener {

        void onBuildingSelected(BuildingType type);
    }

    private final BuildingType type;
    private final Round round;
    private final Label countLabel;
    public boolean isHovered;

    public BuildingButton(BuildingType type, Round round, int yPosition, Skin skin, SelectionListener listener) {
        super(createButtonStyle(type));
        this.type = type;
        this.round = round;
        this.isHovered = false;

        setSize(Consts.BUILDING_BUTTON_WIDTH, Consts.BUILDING_BUTTON_HEIGHT);
        setPosition(Consts.BUILDING_BUTTON_X_BOUNDARY, yPosition);
        this.countLabel = new Label("0", skin);
        this.countLabel.setFontScale(Consts.COUNT_SIZE);
        this.countLabel.setColor(Consts.COUNT_COLOR);
        updateCountLabel();
        updateCountPosition();
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onBuildingSelected(type);
            }
        });
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isHovered = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                isHovered = false;
            }
        });
    }

    static private ImageButtonStyle createButtonStyle(BuildingType type) {
        ImageButtonStyle style = new ImageButtonStyle();
        int index = type.ordinal();
        style.up = new TextureRegionDrawable(Assets.buttonUpTextures[index]);
        style.down = new TextureRegionDrawable(Assets.buttonDownTextures[index]);
        style.over = style.down;
        return style;
    }

    private void updateCountPosition() {
        countLabel.setPosition(
                getX() + getWidth(),
                getY() + getHeight()
        );
    }

    public void update() {
        updateCountLabel();
    }

    private void updateCountLabel() {
        int count = round.getBuildingCount(type);
        countLabel.setText(String.valueOf(count));
    }

    public Label getCountLabel() {
        return countLabel;
    }

    public BuildingType getType() {
        return type;
    }
}

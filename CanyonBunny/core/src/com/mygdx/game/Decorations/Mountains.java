package com.mygdx.game.Decorations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utility.AbstractGameObject;

/**
 * Created by studente on 17/03/18.
 */

public class Mountains extends AbstractGameObject {

    private TextureRegion regMountainLeft;
    private TextureRegion regMountainRight;

    private int length;

    public Mountains(int length) {
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(10, 2);

        regMountainLeft = Assets.instance.levelDecoration.mountainLeft;
        regMountainRight = Assets.instance.levelDecoration.mountainRight;

        //shift mountain and extend length
        origin.x = -dimension.x * 2;
        length += dimension.x * 2;
    }

    private void drawMountain(SpriteBatch batch, float offesetX, float offsetY, float tintColor, float parallaxSpeedX) {
        TextureRegion reg = null;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = dimension.x * offesetX;
        float yRel = dimension.y * offsetY;

        //mountain span whole level
        int mountainLenght = 0;
        mountainLenght += MathUtils.ceil(length / (2 * dimension.x) * (1 - parallaxSpeedX));
        mountainLenght += MathUtils.ceil(0.5f + offesetX);
        for (int i = 0; i < mountainLenght; i++) {
            //mountain left
            reg = regMountainLeft;
            batch.draw(reg.getTexture(), origin.x + xRel+position.x*parallaxSpeedX,
                    position.y + origin.y + yRel,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                    rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                    reg.getRegionHeight(), false, false);
            xRel += dimension.x;

            //mountain right
            reg = regMountainRight;
            batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y + yRel,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                    rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                    reg.getRegionHeight(), false, false);
            xRel += dimension.x;
        }
        //reset color to white
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        //distant mountains(dark grey)
        drawMountain(batch, 0.5f, 0.5f, 0.5f,0.8f);
        //distant mountains(gray)
        drawMountain(batch, 0.25f, 0.25f, 0.7f,0.5f);
        //distant mountains(light gray)
        drawMountain(batch, 0.0f, 0.0f, 0.9f,0.3f);
    }

    public void updateScrollposition(Vector2 camPosition) {
        position.set(camPosition.x, position.y);
    }
}

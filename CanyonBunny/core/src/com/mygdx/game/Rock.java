package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by studente on 17/03/18.
 */

public class Rock extends AbstractGameObject {

    private TextureRegion regEdge;//Edge region: we can use only one edge and rotate it for achieving the other one
    private TextureRegion regMiddle;//Middle region: is the center part of the rock

    private int length;//length of the rock

    public Rock() {
        init();
    }

    private void init() {
        dimension.set(1, 1.5f);

        regEdge = Assets.instance.rock.edge;
        regMiddle = Assets.instance.rock.middle;

        //start length of the rock
        setLength(1);
    }

    public void setLength(int lenght) {
        this.length = lenght;
    }

    public void incraseLength(int amount) {
        setLength(length + amount);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null; //specific region of the texture we need to draw

        float relX = 0;
        float relY = 0;

        //draw left edge
        reg = regEdge;
        relX -= dimension.x / 4;
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(),reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
        relX += dimension.x;

        //draw middle
        relX = 0;
        reg = regMiddle;
        for( int i=0;i<length;i++)
        {
            batch.draw(reg.getTexture(), position.x + relX, position.y
                            + relY, origin.x, origin.y, dimension.x, dimension.y,
                    scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
            relX += dimension.x;
        }

        //draw right edge
        reg = regEdge;
        batch.draw(reg.getTexture(),position.x+relX,position.y+relY,
                origin.x+dimension.x/8,origin.y,dimension.x/4,
                dimension.y,scale.x,scale.y,rotation,regEdge.getRegionX(),
                reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),
        true,false);

    }
}

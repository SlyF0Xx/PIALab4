package Areas.impl;

import Areas.Area;

public class RectangleArea implements Area<Integer, Double, Integer> {
    @Override
    public boolean isIn(Integer x, Double y, Integer size) {
        return x<=0 && y <= 0 && x>=-size/2 && y>=-size;
    }
}

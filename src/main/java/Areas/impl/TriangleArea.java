package Areas.impl;

import Areas.Area;

public class TriangleArea implements Area<Integer, Double, Integer> {

    @Override
    public boolean isIn(Integer x, Double y, Integer size) {
        return x >= 0 && y <= 0 && y >= x /2.0 -size/2.0;
    }
}

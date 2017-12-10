package Areas.impl;

import Areas.Area;

public class CurveArea implements Area<Integer, Double, Integer> {

    @Override
    public boolean isIn(Integer x, Double y, Integer size) {
        return x >= 0 && y >= 0 && x*x + y*y <= size;
    }
}

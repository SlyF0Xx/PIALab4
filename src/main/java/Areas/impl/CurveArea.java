package Areas.impl;

import Areas.Area;

public class CurveArea implements Area<Double, Double, Double> {

    @Override
    public boolean isIn(Double x, Double y, Double size) {
        return x >= 0 && y >= 0 && x*x + y*y <= size*size;
    }
}

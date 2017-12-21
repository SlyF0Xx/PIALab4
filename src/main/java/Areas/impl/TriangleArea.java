package Areas.impl;

import Areas.Area;

public class TriangleArea implements Area<Double, Double, Double> {

    @Override
    public boolean isIn(Double x, Double y, Double size) {
        return x >= 0 && y <= 0 && y >= x /2.0 -size/2.0;
    }
}

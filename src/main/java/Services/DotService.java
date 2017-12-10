package Services;

import Areas.Area;

public class DotService<X extends Number, Y extends Number, Size extends Number> {
    private Area<X, Y, Size> areas[];
    public DotService(Area<X, Y, Size> areas[])
    {
        this.areas = areas;
    };

    public Area[] getAreas() {
        return areas;
    }

    public boolean isIn(X x, Y y, Size size)
    {
        for (Area<X, Y, Size> area: areas)
        {
            if(area.isIn(x, y, size))
            {
                return true;
            }
        }
        return false;
    }
}

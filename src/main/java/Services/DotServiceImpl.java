package Services;

import Areas.Area;
import Areas.impl.CurveArea;
import Areas.impl.RectangleArea;
import Areas.impl.TriangleArea;
import Services.DotService;

import javax.ejb.Local;

@Local
public class DotServiceImpl extends DotService<Integer,Double,Integer> {

    public DotServiceImpl() {
        super(new Area[] {new CurveArea(), new RectangleArea(), new TriangleArea()});
    }
}

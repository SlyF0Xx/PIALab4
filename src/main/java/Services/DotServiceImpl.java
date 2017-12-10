package Services;

import Areas.Area;
import Areas.impl.CurveArea;
import Areas.impl.RectangleArea;
import Areas.impl.TriangleArea;
import Services.DotService;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public class DotServiceImpl extends DotService<Integer,Double,Integer> {

    public DotServiceImpl() {
        super(new Area[] {new CurveArea(), new RectangleArea(), new TriangleArea()});
    }
}

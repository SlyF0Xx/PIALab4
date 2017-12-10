package App;

import Services.DotServiceImpl;

import javax.ejb.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Lock(LockType.WRITE)
@Produces(MediaType.APPLICATION_XML)
public class API {
    @EJB
    DotServiceImpl DotService;

    @Path("/shoot")
    @GET
    @Produces(value={"text/xml", "application/json"})
    public Boolean shoot() {
        return DotService.isIn(1, 1.0, 4);
    }

}

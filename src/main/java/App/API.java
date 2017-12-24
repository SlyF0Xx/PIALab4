package App;

import Services.DotService;
import Services.DotServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Dot;
import model.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/API")
@Stateless
public class API {
    DotService dotService = new DotServiceImpl();

    /*private static final EntityManagerFactory emFactoryObj;
    private static final String PERSISTENCE_UNIT_NAME = "NewPersistenceUnit";

    static {
        emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
        // This Method Is Used To Retrieve The 'EntityManager' Object
    public EntityManager getEntityManager() {
        return emFactoryObj.createEntityManager();
    }*/

    @PersistenceContext(unitName = "NewPersistenceUnit")
    EntityManager entityManager;

    @Path("/login")
    @POST
    @Transactional
    public Response login(@FormParam("name") String name,
                      @FormParam("password") String password,
                      @Context HttpServletRequest req) {
        //EntityManager entityManager = getEntityManager();

        List<User> userList = entityManager.createQuery("SELECT user FROM User user WHERE user.name = :name")
                .setParameter("name", name)
                .getResultList();

        if(userList.size() == 1 && userList.get(0).getPassword() == password.hashCode() )
        {
            req.getSession().setAttribute("id", userList.get(0).getId());
            return Response.ok()
                    .entity( true)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("POST").build();
        }
        else {
            //TODO: Может как 400 обработать?
            return Response.ok()
                    .entity( false)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("POST").build();
        }
    }

    @Path("/register")
    @POST
    @Transactional
    public Response register(@FormParam("name") String name,
                         @FormParam("password") String password,
                         @Context HttpServletRequest req) {
        User user = new User(name, password.hashCode());
        //EntityManager entityManager = getEntityManager();

        try {
            entityManager.persist(user);
            entityManager.flush();
            req.getSession().setAttribute("id", user.getId());

            return Response.ok()
                    .entity( true)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("POST").build();
        }
        catch (Exception e)
        {
            //TODO: Может как 400 обработать?
            return Response.ok()
                    .entity( false)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("POST").build();
        }
        //user.getId() - if not sequence
    }

    @Path("/shoot/{x}/{y}/{size}")
    @GET
    //@Produces(value = {"text/xml", "application/json"})
    @Transactional
    public Response shoot(@PathParam("x") Double x,
                         @PathParam("y") Double y,
                         @PathParam("size") Double size,
                         @Context HttpServletRequest req) {
        //EntityManager entityManager = getEntityManager();
        List<User> user = entityManager.createQuery("SELECT user FROM User user where user.id = :ident")
                .setParameter("ident",req.getSession().getAttribute("id")).getResultList();

        if(user.size() == 1)
        {
            Dot dot = new Dot(x, y, size, dotService.isIn(x, y, size), user.get(0));
            entityManager.persist(dot);


            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return Response.ok()
                        .entity( objectMapper.writeValueAsString(dot))
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                        .allow("GET").build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();

                return Response.noContent()
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                        .allow("GET").build();
            }
        }
        else {
            return Response.noContent()
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("GET").build();
        }
    }

    @Path("/refreshShoot/{x}/{y}/{size}")
    @GET
    //@Produces(value = {"text/xml", "application/json"})
    @Transactional
    public Response refreshShoot(@PathParam("x") Double x,
                                @PathParam("y") Double y,
                                @PathParam("size") Double size,
                                @Context HttpServletRequest req) {
        //EntityManager entityManager = getEntityManager();
        List<User> user = entityManager.createQuery("SELECT user FROM User user where user.id = :ident")
                .setParameter("ident",req.getSession().getAttribute("id")).getResultList();

        if(user.size() == 1)
        {
            Dot dot = new Dot(x, y, size, dotService.isIn(x, y, size), user.get(0));

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return Response.ok()
                        .entity( objectMapper.writeValueAsString(dot))
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                        .allow("GET").build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();

                Response.noContent()
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                        .allow("GET").build();
            }
        }
        return Response.noContent()
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("GET").build();
    }

    @Path("/existName/{name}")
    @GET
    @Produces(value = {"text/xml", "application/json"})
    @Transactional
    public Response existName(@PathParam("name") String name) {
        //EntityManager entityManager = getEntityManager();
        List<Long> list = entityManager.createQuery("SELECT COUNT(user) FROM User user where user.name = :name")
                .setParameter("name", name).getResultList();

        return  Response.ok()
                .entity( list.get(0) == 1L)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("GET").build();
    }

    @Path("/isAuthenticated")
    @GET
    @Produces(value = {"text/xml", "application/json"})
    @Transactional
    public Response isAuthenticated(@Context HttpServletRequest req) {
        return Response.ok()
                .entity((Integer)req.getSession().getAttribute("id") != 0)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("GET").build();
    }

    @Path("/logout")
    @GET
    public void logout(@Context HttpServletRequest req) {
        req.getSession().setAttribute("id", 0);
    }


    @Path("/list")
    @GET
    @Produces(value={"text/xml", "application/json"})
    @Transactional
    public Response list(@Context HttpServletRequest req) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        //EntityManager entityManager = getEntityManager();
        if(req.getSession().getAttribute("id") != null)
        {
            List<Dot> dots = entityManager.createQuery("SELECT d FROM Dot d WHERE d.user.id = :ident")
                    .setParameter("ident", req.getSession().getAttribute("id"))
                    .getResultList();

            return Response.ok()
                    .entity( objectMapper.writeValueAsString(dots))
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("GET").build();

        }
        return Response.noContent()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("GET").build();
        /*
         List<User> user = entityManager.createQuery("SELECT user FROM User user WHERE user.id = :id")
                .setParameter("id", req.getSession().getAttribute("id"))
                .getResultList();

        return objectMapper.writeValueAsString(user.get(0).getDots());
         */
    }

}

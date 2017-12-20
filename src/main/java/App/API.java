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
    public void login(@FormParam("name") String name,
                      @FormParam("password") String password,
                      @Context HttpServletRequest req) {
        //EntityManager entityManager = getEntityManager();

        List<User> userList = entityManager.createQuery("SELECT user FROM User user WHERE user.name = :name")
                .setParameter("name", name)
                .getResultList();

        if(userList.size() == 1 && userList.get(0).getPassword() == password.hashCode() )
        {
            req.getSession().setAttribute("id", userList.get(0).getId());
        }
    }

    @Path("/register")
    @POST
    @Transactional
    public void register(@FormParam("name") String name,
                         @FormParam("password") String password,
                         @Context HttpServletRequest req) {
        User user = new User(name, password.hashCode());
        //EntityManager entityManager = getEntityManager();
        entityManager.persist(user);
        entityManager.flush();
        req.getSession().setAttribute("id", user.getId()+1);
    }

    @Path("/shoot/{x}/{y}/{size}")
    @GET
    //@Produces(value = {"text/xml", "application/json"})
    @Transactional
    public String shoot(@PathParam("x") Integer x,
                         @PathParam("y") Double y,
                         @PathParam("size") Integer size,
                         @Context HttpServletRequest req) {
        //EntityManager entityManager = getEntityManager();
        List<User> user = entityManager.createQuery("SELECT user FROM User user where user.id = :ident")
                .setParameter("ident",req.getSession().getAttribute("id")).getResultList();

        if(user.size() == 1)
        {
            Dot dot = new Dot(x, y, size, dotService.isIn(x, y, size), user.get(0));
            entityManager.persist(dot);

            return dot.getShoot().toString();
        }
        else {
            return "false";
        }
    }

    @Path("/existName/{name}")
    @GET
    @Produces(value = {"text/xml", "application/json"})
    @Transactional
    public boolean existName(@PathParam("name") String name) {
        //EntityManager entityManager = getEntityManager();
        List<Integer> list = entityManager.createQuery("SELECT COUNT(user) FROM User user where user.name = :name")
                .setParameter("name", name).getResultList();

        return list.get(0) == 1;
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
    public String list(@Context HttpServletRequest req) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        //EntityManager entityManager = getEntityManager();
        if(req.getSession().getAttribute("id") != null)
        {
            List<Dot> dots = entityManager.createQuery("SELECT d FROM Dot d WHERE d.user.id = :ident")
                    .setParameter("ident", 1)
                    .getResultList();

            return objectMapper.writeValueAsString(dots);

        }
        return "";
        /*
         List<User> user = entityManager.createQuery("SELECT user FROM User user WHERE user.id = :id")
                .setParameter("id", req.getSession().getAttribute("id"))
                .getResultList();

        return objectMapper.writeValueAsString(user.get(0).getDots());
         */
    }

}

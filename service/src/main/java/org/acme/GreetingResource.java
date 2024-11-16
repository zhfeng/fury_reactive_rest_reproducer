package org.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.apache.fury.util.Preconditions;

@Path("/test")
public class GreetingResource {
    public final static short BAR_CLASS_ID = 400;
    
    @POST
    @Path("/json")
    @Produces("application/json")
    @Consumes("application/json")
    public String hello(Bar bar) {
        return "Hello from Quarkus REST " + bar.f2();
    }
    
    @POST
    @Path("/fury")
    @Produces("application/fury")
    @Consumes("application/fury")
    public Bar testBar(Bar obj) {
        Preconditions.checkArgument(obj.f1() == 1, obj);
        Preconditions.checkArgument(obj.f2().equals("hello bar"), obj);
        
        return new Bar(2, "bye bar");
    }
}

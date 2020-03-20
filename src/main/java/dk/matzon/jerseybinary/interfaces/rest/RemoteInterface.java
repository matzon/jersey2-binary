package dk.matzon.jerseybinary.interfaces.rest;


import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Component
@Path(RemoteInterface.STORAGE)
public class RemoteInterface {
    public static final String STORAGE = "/rest/storage";
    private HashMap<String, byte[]> storage;

    public RemoteInterface(HashMap<String, byte[]> storage) {
        this.storage = storage;
    }

    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response load(@PathParam("key") String _key) {
        if (storage.containsKey(_key)) {
            return Response
                    .ok(storage.get(_key), MediaType.APPLICATION_OCTET_STREAM)
                    .build();
        } else {
            return Response
                    .noContent()
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{key}")
    public String save(@PathParam("key") String _key, byte[] _value) {
        storage.put(_key, _value);
        return String.valueOf(true);
    }

}

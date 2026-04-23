/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author senuthmi
 */
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Response getApiInfo() {

        Map<String, Object> api = new HashMap<>();

        api.put("name", "Campus Sensor API");
        api.put("version", "1.0");
        api.put("rooms", "/api/v1/rooms");
        api.put("sensors", "/api/v1/sensors");

        return Response.ok(api).build();
    }
}
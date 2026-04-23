/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author senuthmi
 */

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import exception.LinkedResourceNotFoundException;
import models.Room;
import models.Sensors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResources {

    private static Map<String, Sensors> sensors = new HashMap<>();
    private static Map<String, Room> rooms = RoomResources.getRooms();

    // GET all sensors 
    @GET
    public Response getAllSensors(@QueryParam("type") String type) {

        Collection<Sensors> result = sensors.values();

        if (type != null) {
            result = result.stream()
                    .filter(s -> s.getType() != null && s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        return Response.ok(result).build();
    }

    // GET sensor by ID
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {

        Sensors sensor = sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Sensor not found"))
                    .build();
        }

        return Response.ok(sensor).build();
    }

    // CREATE sensor
    @POST
    public Response createSensor(Sensors sensor) {

        if (sensor == null || sensor.getId() == null ||
            sensor.getRoomId() == null || sensor.getType() == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"id, type and roomId are required\"}")
                .type("application/json")
                .build();}

        if (!rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        sensors.put(sensor.getId(), sensor);

        Room room = rooms.get(sensor.getRoomId());
        if (room != null) {
            room.addSensorId(sensor.getId());
        }

        URI location = UriBuilder.fromPath("/api/v1/sensors/{id}")
                .build(sensor.getId());

        return Response.created(location)
                .entity(sensor)
                .build();
    }

    // DELETE sensor
    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensors sensor = sensors.remove(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Sensor not found"))
                    .build();
        }

        Room room = rooms.get(sensor.getRoomId());
        if (room != null) {
            room.removeSensorId(id);
        }

        return Response.noContent().build();
    }

    // GET sensors by room
    @GET
    @Path("/room/{roomId}")
    public Response getSensorsByRoom(@PathParam("roomId") String roomId) {

        List<Sensors> result = sensors.values().stream()
                .filter(s -> roomId.equals(s.getRoomId()))
                .collect(Collectors.toList());

        return Response.ok(result).build();
    }

    // Sub-resource locator
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource() {
        return new SensorReadingResource();
    }

    public static Map<String, Sensors> getSensors() {
        return sensors;
    }
}

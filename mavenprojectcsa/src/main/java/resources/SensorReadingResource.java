/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author senuthmi
 */
import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import exception.SensorUnavailableException;
import models.Sensors;
import resources.SensorResources;
import models.SensorReading;

@Path("/sensors/{sensorId}/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    @PathParam("sensorId")
    private String sensorId;

    private static final Map<String, Sensors> sensors = SensorResources.getSensors();

    @GET
    public Response getAllReadings() {

        Sensors sensor = sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(404)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(new ArrayList<>(sensor.getReadings())).build();
    }

    @POST
    public Response addReading(SensorReading reading) {
    
        if (reading == null) {
            return Response.status(400)
                    .entity("Invalid reading")
                    .build();
        }
    
        if (reading.getTimestamp() <= 0) {
            return Response.status(400)
                    .entity("Invalid timestamp")
                    .build();
        }
    
        Sensors sensor = sensors.get(sensorId);
    
        //  404 
        if (sensor == null) {
            return Response.status(404)
                    .entity("Sensor not found")
                    .build();
        }
    
        //  403 
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())
                || "OFFLINE".equalsIgnoreCase(sensor.getStatus())) {
    
            throw new SensorUnavailableException("Sensor is not available for readings");
        }
    
        sensor.getReadings().add(reading);
        sensor.setCurrentValue(reading.getValue());
    
        return Response.status(201).entity(reading).build();
    }
}

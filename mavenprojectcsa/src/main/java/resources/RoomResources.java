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
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Room;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResources {

    private static Map<String, Room> rooms = new HashMap<>();

    // GET all rooms
    @GET
    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    // GET room by ID
    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        Room room = rooms.get(id);
        if (room == null) throw new NotFoundException("Room not found");

        return Response.ok(room).build();
    }

    // CREATE room (IMPORTANT FIX)
    @POST
    public Response createRoom(Room room) {
        if (room == null || room.getId() == null || room.getId().isBlank()) {
            throw new BadRequestException("Room id is required");
        }
        if (rooms.containsKey(room.getId())) {
            throw new ClientErrorException("Room id already exists", Response.Status.CONFLICT);
        }

        rooms.put(room.getId(), room);
        URI location = URI.create("/api/v1/rooms/" + room.getId());

        return Response.created(location)
                .entity(room)
                .build();
    }

    // UPDATE room
    @PUT
    @Path("/{id}")
    public Response updateRoom(@PathParam("id") String id, Room updatedRoom) {
        if (updatedRoom == null) {
            throw new BadRequestException("Room payload is required");
        }

        if (!rooms.containsKey(id)) {
            throw new NotFoundException("Room not found");
        }

        updatedRoom.setId(id);
        rooms.put(id, updatedRoom);

        return Response.ok(updatedRoom).build();
    }

    // DELETE room (with business rule)
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = rooms.get(id);

        if (room == null) {
            return Response.status(404)
                    .entity(Map.of("error", "Room not found"))
                    .build();
        }

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            return Response.status(409)
                    .entity(Map.of("error", "Room has active sensors"))
                    .build();
        }

        rooms.remove(id);

        return Response.noContent().build();
    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }


}
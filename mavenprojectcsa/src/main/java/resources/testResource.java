/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author senuthmi
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class testResource {

    @GET
    @Path("/500")
    public String triggerError() {

        int x = 10 / 0; // force crash

        return "never reached";
    }
}
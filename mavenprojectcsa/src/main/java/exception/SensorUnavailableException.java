/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author senuthmi
 */
public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String msg) {
        super(msg);
    }
}
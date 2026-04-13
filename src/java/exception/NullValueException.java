/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Vinz
 */
public class NullValueException extends RuntimeException{

    public NullValueException() {
    }
    public NullValueException(String msg) {
        super(msg);
    }
}

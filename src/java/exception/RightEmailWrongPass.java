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
public class RightEmailWrongPass extends RuntimeException{
    public RightEmailWrongPass() {
    }
    public RightEmailWrongPass(String msg) {
        super(msg);
    }
}

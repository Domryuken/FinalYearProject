/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallguymachine;

/**
 *
 * @author Ladon
 */
public class LowCodeException extends Exception
{

    /**
     * Creates a new instance of <code>LowCodeException</code> without detail
     * message.
     */
    public LowCodeException()
    {
    }

    /**
     * Constructs an instance of <code>LowCodeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LowCodeException(String msg)
    {
        super(msg);
    }
}

package com.gs.paperclip;

import java.io.Serializable;

/**
 * An object should implement Paperclipped when it's a Java instantiation of
 * an object that exists in the Rails world and has_attached_file.
 *
 * @author Aaron Valade <aaron@generalsensing.com>
 */
public interface Paperclipped<ID extends Serializable> {
    /**
     * Returns the ID of the object which is used to create an ID partition
     * for the file to reside inside.
     *
     * @return the ID of the object
     */
    ID getId();

    /**
     * Returns the base filename of the file that is Paperclipped inside
     * of the implementing model.
     *
     * @return a String that is the file name
     */
    String getFileName();
}

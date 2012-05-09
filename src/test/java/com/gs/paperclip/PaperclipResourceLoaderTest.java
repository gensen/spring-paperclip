package com.gs.paperclip;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * A set of tests for the PaperclipResourceLoaderTest.
 *
 * @author Aaron Valade <aaron@generalsensing.com>
 */
public class PaperclipResourceLoaderTest {
    PaperclipResourceLoader loader = new PaperclipResourceLoader();
    PaperclipResource resource;
    @Before
    public void setUp() throws Exception {
        loader.setBasePaperclipUrl("http://www.somewhere.com");
        resource = new PaperclipResource("http://www.somewhere.com", new Firmware());
    }

    @Test
    public void testGetResource() {
        assertEquals(resource, loader.getResource(new Firmware()));

    }
}

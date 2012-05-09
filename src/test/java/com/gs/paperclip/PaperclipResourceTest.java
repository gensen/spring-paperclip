package com.gs.paperclip;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

/**
 * A set of tests for the PaperclipResource class.
 *
 * @author Aaron Valade <aaron@generalsensing.com>
 */
public class PaperclipResourceTest {
    PaperclipResource resource;

    @Before
    public void setUp() throws Exception {
        resource = new PaperclipResource("http://somewhere_out_there.com", new Firmware());
    }

    @Test
    public void testGetURI() throws Exception {
        assertEquals("The URI was not correct",
                "http://somewhere_out_there.com/firmwares/0012/3456/something_fw_original.bin",
                resource.getURI().toString());
    }

    @Test(expected = IOException.class)
    public void testGetBadURI() throws Exception {
        resource = new PaperclipResource("blahblah:blah", new Firmware());
        assertNull(resource.getURI());
    }

    @Test
    public void testGetURL() throws Exception {
        assertEquals("The URI was not correct",
                "http://somewhere_out_there.com/firmwares/0012/3456/something_fw_original.bin",
                resource.getURL().toString());
    }

    @Test
    public void testGetDescription() {
        assertNotNull(resource.getDescription());
    }

    /**
     * This test requires a net connection.
     * @throws Exception
     */
    @Test
    public void testGetInputStream() throws Exception {
        PaperclipResource r = new MockPaperclipResource();
        InputStream ret = r.getInputStream();
        assertTrue(ret.available() > 0);

        // Any additional calls must provide a fresh InputStream
        InputStream ret2 = r.getInputStream();
        assertFalse(ret.equals(ret2));
    }

    @Test
    public void testEquals() throws Exception {
        PaperclipResource r = new PaperclipResource("http://somewhere_out_there.com", new Firmware());

        assertTrue(resource.equals(r));
        r = new PaperclipResource("http://somewhere_else.com", new Firmware());
        assertFalse(resource.equals(r));
    }
}

class MockPaperclipResource extends PaperclipResource {
    public MockPaperclipResource() throws Exception {
        super("http://www.yahoo.com", new Firmware());
    }

    @Override
    public URL getURL() throws IOException {
        return new URL("http://www.yahoo.com");
    }
}

class Firmware implements Paperclipped {
    @Override
    public Serializable getId() {
        return 123456L;
    }

    @Override
    public String getFileName() {
        return "something_fw.bin";
    }
}

package com.gs.paperclip;

import static org.jvnet.inflector.Noun.pluralOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.AbstractResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * An extension of a Spring Resource that formats the URL into a Paperclip
 * like URL.  The user should be able to pass in a base url and a class that
 * implements the Paperclip interface and we will return a reference to the
 * file.
 *
 * @author Aaron Valade <aaron@generalsensing.com>
 */
public class PaperclipResource extends AbstractResource {
    private static final Logger LOG = LoggerFactory.getLogger(PaperclipResource.class.getName());
    private String url;

    /**
     * Creates a new PaperclipResource.
     *
     * @param assetHost the base URL for the Paperclip resource
     * @param resource the Resource
     */
    public PaperclipResource(String assetHost, Paperclipped resource) {
        assert assetHost != null;
        assert resource != null;
        String idPartition = getIdPartition(resource);
        String filename = resource.getFileName().substring(0, resource.getFileName().lastIndexOf("."));
        String fileExt = resource.getFileName().substring(resource.getFileName().lastIndexOf("."));
        String resourceClassName = pluralOf(resource.getClass().getSimpleName().toLowerCase());

        this.url = new StringBuilder(assetHost)
                .append("/").append(resourceClassName)
                .append("/").append(idPartition)
                .append("/").append(filename)
                .append("_original")
                .append(fileExt).toString();
        LOG.debug("Created PaperclipResource from URL: {}", this.url);
    }

    /**
     * Returns an id partition that is left padded out to 8 characters with 0s and
     * containing a '/' every 4th character from the right.
     *
     * @param resource the Paperclipped to get an IdPartition from
     * @return a String
     */
    private String getIdPartition(Paperclipped resource) {
        StringBuilder ret = new StringBuilder(resource.getId().toString());
        int paddingLength = 8 - ret.length();
        for (int i = 0; i < paddingLength; i++) {
            ret.insert(0, 0);
        }

        int curPos = ret.length();

        while (curPos > 0) {
            curPos -= 4;
            ret.insert(curPos, "/");
        }
        if (ret.charAt(0) == '/') {
            ret.deleteCharAt(0);
        }
        return ret.toString();
    }

    /**
     * This implementation builds a URI based on the URL returned
     * by {@link #getURL()}.
     */
    @Override
    public URI getURI() throws IOException {
        URL url = getURL();
        try {
            return ResourceUtils.toURI(url);
        } catch (URISyntaxException ex) {
            throw new NestedIOException("Invalid URI [" + url + "]", ex);
        }
    }

    /**
     * This implementation returns the underlying URL reference.
     * @return the URL for this resource
     * @throws IOException if the URL can't be parsed
     */
    public URL getURL() throws IOException {
        return new URL(url);
    }

    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     * <p>Implementations are also encouraged to return this value
     * from their <code>toString</code> method.
     *
     * @see Object#toString()
     */
    @Override
    public String getDescription() {
        return "URL [" + url + "]";
    }

    /**
     * Return an {@link java.io.InputStream}.
     * <p>It is expected that each call creates a <i>fresh</i> stream.
     * <p>This requirement is particularly important when you consider an API such
     * as JavaMail, which needs to be able to read the stream multiple times when
     * creating mail attachments. For such a use case, it is <i>required</i>
     * that each <code>getInputStream()</code> call returns a fresh stream.
     *
     * @throws java.io.IOException if the stream could not be opened
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = getURL().openConnection();
        con.setUseCaches(false);
        return con.getInputStream();
    }

    /**
     * This implementation compares description strings.
     *
     * @see #getDescription()
     */
    @Override
    public boolean equals(Object obj) {
        return (obj == this || (obj instanceof PaperclipResource && url.equals(((PaperclipResource) obj).url)));
    }

    /**
     * This implementation returns the description's hash code.
     *
     * @see #getDescription()
     */
    @Override
    public int hashCode() {
        return url.hashCode();
    }
}

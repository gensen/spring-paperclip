package com.gs.paperclip;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * A ResourceLoader for Paperclip resources.
 *
 * @author Aaron Valade <aaron@generalsensing.com>
 */
public class PaperclipResourceLoader extends DefaultResourceLoader {
    private String basePaperclipUrl;

    public PaperclipResourceLoader() { }
    public PaperclipResourceLoader(String basePaperclipUrl) {
        this.basePaperclipUrl = basePaperclipUrl;
    }

    /**
     * Attempt to get the resource for a Paperclipped model.  If we cannot do that,
     * then we fall back on trying to get the fileName resource specified by the
     * model.
     * @param model an Object implementing the Paperclipped interface
     * @return the Resource specified by the Paperclipped model
     */
    public Resource getResource(Paperclipped model) {
        return new PaperclipResource(basePaperclipUrl, model);
    }

    public void setBasePaperclipUrl(String basePaperclipUrl) {
        this.basePaperclipUrl = basePaperclipUrl;
    }
}

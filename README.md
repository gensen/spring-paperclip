# Spring Paperclip

This is a little library providing some hooks for Spring applications
to use [Paperclip](https://github.com/thoughtbot/paperclip)
resources.  You just need to add the interface `Paperclipped` to your
Java class files and then you can instantiate a new `Resource` by
specifying your asset host and the model:

    new PaperclipResource("http://my-awesome-bucket.s3.amazon.com", myPaperClipResource);

It doesn't handle any special customizations that you could make to
your Paperclip models.  It just handles the basics out of the box and
it relies on your name of your models to agree on the Java and Ruby side.

## Requirements

Currently, the library depends on [Spring 3.1.1](http://www.springsource.org/spring-framework), [SLF4J 1.6.4](http://www.slf4j.org/) and the
Inflector library (don't have a canonical URL for this one).

## License

Spring-Paperclip is Copyright © 2012 General Sensing LTD.  It is free
software, and may be redistributed under the terms specified in the
MIT-LICENSE file.

package de.cosmocode.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

import de.cosmocode.patterns.Factory;

/**
 * A {@link Factory} that creates {@link JSONRenderer}.
 * The implementation may differ between different methods,
 * depending on the underlying framework.
 * 
 * @author Oliver Lorenz
 */
public interface JsonFactory extends Factory<JSONRenderer> {
    
    /**
     * The default encoding for OutputStreams, UTF-8.
     */
    Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

    /**
     * <p> Creates a {@link JSONRenderer}.
     * The returned JsonRenderer must support {@link JSONRenderer#toString() toString()}.
     * </p>
     * <p> <strong>Important: </strong>
     * A call on {@link JSONRenderer#toString() toString()} may close an underlying stream,
     * resulting in the behavior that
     * only toString() can be called after toString() is called -
     * all other methods then throw IllegalStateExceptions.
     * </p>
     * @return a {@link JSONRenderer} that supports toString()
     */
    @Override
    JSONRenderer create();
    
    /**
     * <p> Creates a {@link JSONRenderer}.
     * The returned JsonRenderer must support {@link JSONRenderer#toString() toString()}.
     * </p>
     * <p> <strong>Important: </strong>
     * A call on {@link JSONRenderer#toString() toString()} may close an underlying stream,
     * resulting in the behavior that
     * only toString() can be called after toString() is called -
     * all other methods then throw IllegalStateExceptions.
     * </p>
     * @param level the RenderLevel of the JsonRenderer
     * @return a {@link JSONRenderer} that supports toString()
     */
    JSONRenderer create(RenderLevel level);

    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link Writer}.
     * All data is directly written to the given Writer 
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param writer a {@link Writer}; the rendered json is written to this Writer
     * @return a new JsonRenderer that writes its output to the given Writer.
     */
    JSONRenderer create(Writer writer);
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link Writer}.
     * All data is directly written to the given Writer 
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param writer a {@link Writer}; the rendered json is written to this Writer
     * @param level the RenderLevel of the JsonRenderer
     * @return a new JsonRenderer that writes its output to the given Writer.
     */
    JSONRenderer create(Writer writer, RenderLevel level);
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the {@link #DEFAULT_ENCODING},
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the given character encoding,
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param encoding the character encoding to use when writing to the stream
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, String encoding) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the given character encoding,
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param encoding the character encoding to use when writing to the stream
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, Charset encoding) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the {@link #DEFAULT_ENCODING},
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param level the RenderLevel of the JsonRenderer
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, RenderLevel level) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the given character encoding,
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param encoding the character encoding to use when writing to the stream
     * @param level the RenderLevel of the JsonRenderer
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, String encoding, RenderLevel level) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the given character encoding,
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param encoding the character encoding to use when writing to the stream
     * @param level the RenderLevel of the JsonRenderer
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, Charset encoding, RenderLevel level) throws IOException;
    
}

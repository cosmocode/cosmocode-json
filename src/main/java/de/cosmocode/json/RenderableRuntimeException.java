package de.cosmocode.json;

public class RenderableRuntimeException extends RuntimeException implements JSONMapable {

    private static final long serialVersionUID = 3995699132414599160L;

    protected RenderableRuntimeException() {
        
    }
    
    protected RenderableRuntimeException(String message) {
        super(message);
    }
    
    protected RenderableRuntimeException(Throwable cause) {
        super(cause);
    }
    
    protected RenderableRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Override
    public RenderableRuntimeException getCause() {
        final Throwable cause = getCause();
        if (cause == null) return null;
        if (cause instanceof RenderableRuntimeException) {
            return RenderableRuntimeException.class.cast(cause);
        } else {
            return RenderableRuntimeException.copyOf(cause);
        }
    }
    
    @Override
    public final JSONRenderer renderAsMap(JSONRenderer renderer) {
        return renderer.
            key("message").value(getMessage()).
            key("class").value(getClass().getName()).
            key("stacktrace").array(getStackTrace()).
            key("cause").object(getCause());
    }
    
    public static RenderableRuntimeException copyOf(Throwable source) {
        final RenderableRuntimeException e = new RenderableRuntimeException(source.getMessage(), source.getCause()) {
            
            private static final long serialVersionUID = -9097384160301358600L;

            @Override
            public synchronized Throwable fillInStackTrace() {
                // don't fill the stacktrace, we wouldn't use it
                return this;
            }
            
        };
        e.setStackTrace(source.getStackTrace());
        return e;
    }

}

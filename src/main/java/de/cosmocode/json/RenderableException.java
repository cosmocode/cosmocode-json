package de.cosmocode.json;

public class RenderableException extends Exception implements JSONMapable {

    private static final long serialVersionUID = 3995699132414599160L;

    protected RenderableException() {
        
    }
    
    protected RenderableException(String message) {
        super(message);
    }
    
    protected RenderableException(Throwable cause) {
        super(cause);
    }
    
    protected RenderableException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @Override
    public RenderableException getCause() {
        final Throwable cause = getCause();
        if (cause == null) return null;
        if (cause instanceof RenderableException) {
            return RenderableException.class.cast(cause);
        } else {
            return RenderableException.copyOf(cause);
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
    
    private static RenderableException copyOf(Throwable source) {
        final RenderableException e = new RenderableException(source.getMessage(), source.getCause()) {
            
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

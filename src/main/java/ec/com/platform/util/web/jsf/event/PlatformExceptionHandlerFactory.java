package ec.com.platform.util.web.jsf.event;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Manejo de excepciones JSF
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class PlatformExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory base;

    private PlatformExceptionHandler cached;

    public PlatformExceptionHandlerFactory(ExceptionHandlerFactory base) {
        this.base = base;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        if(cached == null) {
            cached = new PlatformExceptionHandler(base.getExceptionHandler());
        }
        return cached;
    }
}

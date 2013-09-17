package ec.com.ebos.util.web.jsf.handler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Manejo de excepciones JSF
 * 
 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public class EbosExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory base;

    private EbosExceptionHandler cached;

    public EbosExceptionHandlerFactory(ExceptionHandlerFactory base) {
        this.base = base;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        if(cached == null) {
            cached = new EbosExceptionHandler(base.getExceptionHandler());
        }
        return cached;
    }
}

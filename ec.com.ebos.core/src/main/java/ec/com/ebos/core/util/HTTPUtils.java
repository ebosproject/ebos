package ec.com.ebos.core.util;

import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * Utilidades para el protocolo HTTP
 *
 * @see
 * http://johannburkard.de/blog/programming/java/x-forwarded-for-http-header.html
 * @udpate <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
 */
public final class HTTPUtils {

    private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";

    public static final HttpServletRequest getCurrentRequest() {
        HttpServletRequest request = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        } else {
            FacesContext currentInstance = FacesContextWrapper.getCurrentInstance();
            if (currentInstance != null) {
                request = (HttpServletRequest) currentInstance.getExternalContext().getRequest();
            }
            //request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();                   
        }
        return request;
    }

    public static final Object getRequestAttribute(String name) {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            return request.getAttribute(name);
        }
        return null;
    }

    public static final HttpServletResponse getCurrentResponse() {
        HttpServletResponse response = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        } else {
            // TODO
        }
        return response;
    }

    public static final HttpSession getCurrentSession() {
        return getCurrentRequest().getSession(false);
    }

    public static final Object getSessionAttribute(String name) {
        HttpSession session = getCurrentSession();
        if (session != null) {
            return session.getAttribute(name);
        }
        return null;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String x = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (x != null) {
            remoteAddr = x;
            int idx = remoteAddr.indexOf(',');
            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }
        if (remoteAddr != null) {
            remoteAddr = remoteAddr.trim();
        }
        return remoteAddr;
    }

    public static String getRemoteAddr() {
        return getRemoteAddr(getCurrentRequest());
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void addCookie(String name, String value, int maxAge) {
        addCookie(getCurrentResponse(), name, value, maxAge);
    }

    public static void addCookie(String name, String value) {
        addCookie(name, value, Integer.MAX_VALUE);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getCookie(String name) {
        return getCookie(getCurrentRequest(), name);
    }

    public static String getCookie(String name, String defaultValue) {
        return StringUtils.defaultIfEmpty(getCookie(getCurrentRequest(), name), defaultValue);
    }
}

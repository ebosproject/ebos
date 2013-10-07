/*
 @author Eduardo Plua Alay
 @since 2013-03-03
*/

/**
 * Eduardo Plua Alay
 * @see http://code.irontec.com/2012/evento-click-del-boton-del-medio-en-javascript-capture-cancel/
 * @see http://code.irontec.com/wp-content/uploads/2012/01/middle-click-disabled.html
 * @since 04-01-2013
 */
function jsPreventDefault(event, elem){
    // Find out which button has triggered the mouseup (~click) event.
    // Based on: http://unixpapa.com/js/mouse.html
    if (event.which == null) {
        /* IE case */
        button= (event.button < 2) ? "LEFT" : ((event.button == 4) ? "MIDDLE" : "RIGHT");
    } else {
        /* All others */
        button= (event.which < 2) ? "LEFT" : ((event.which == 2) ? "MIDDLE" : "RIGHT");
    }
    if (button == 'MIDDLE') {
        // Cancel click event
        // Works on webkit & IE9?
        event.stopPropagation();
        event.preventDefault();

        // Created 2 variables within the event context			
        var $self = $(elem);
        var prevHref = $self.attr("href");		
        // Remove href attribute, so browser won't do anything on default behaviour
        $self.removeAttr("href");
        setTimeout(function() {
            // Put back the original href, passed 100ms (maybe less?)
            // Almost sure the browset has processed the event with an empty href,
            // So nothing has happened
            $self.attr("href",prevHref);
        },100);				
    }
}
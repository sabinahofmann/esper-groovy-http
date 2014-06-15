package de.hofmanns.esper.http

import java.io.IOException;


import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler

class SupportHTTPServerReqestHandler implements HttpRequestHandler {

	private static Log log = LogFactory.getLog(SupportHTTPServerReqestHandler.class);

    private static List<String> targets = new ArrayList<String>();

    public SupportHTTPServerReqestHandler() {
        super();
    }

    public static List<String> getAndResetTargets() {
        List<String> copy = new ArrayList<String>(targets);
        targets = new ArrayList<String>();
        return copy;
    }

    public static List<String> getTargets() {
        return targets;
    }

    public void handle(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws HttpException, IOException {

        String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
        if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
            throw new MethodNotSupportedException(method + " method not supported");
        }

        log.debug("URI= " + request.getRequestLine().getUri());

        response.setStatusCode(HttpStatus.SC_OK);
        targets.add(request.getRequestLine().getUri());
    }
}

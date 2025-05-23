/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2025 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jpos.rest;

import org.jpos.q2.Q2;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("/echo")
public class Echo extends RestSupport {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response echoGet(
            @HeaderParam("version") String version,
            @HeaderParam("consumer-id") String consumerId,
            @HeaderParam("timestamp") long timestamp,
            @HeaderParam("hash") String hash,
            @HeaderParam("nonce") String nonce,
            @Context UriInfo uriInfo,
            @Context SecurityContext sc) {
        org.jpos.transaction.Context ctx = createContext(getClass().getSimpleName().toLowerCase() + "::get");
        ctx.put (RestConstants.RESTAPI_CREDENTIALS.value(),
                APICredential.builder().version(version)
                        .consumerId(consumerId)
                        .timestamp(timestamp)
                        .hash(hash)
                        .nonce(nonce)
                        .securityContext(sc).build()
        );
        ctx.put(RestConstants.URI_INFO.value(), uriInfo);
        Response.ResponseBuilder rb = execute(ctx, Response.Status.OK, (ctx1, resp) ->
        {
            put(resp, "version", Q2.getVersion());
            put(resp, "revision", Q2.getRevision());
            put(resp, "timestamp", ctx1.get(RestConstants.TIMESTAMP.value()));
            put(resp, "security-context", sc);
        });
        return rb.build();
    }
}

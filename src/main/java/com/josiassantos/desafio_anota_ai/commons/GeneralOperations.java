package com.josiassantos.desafio_anota_ai.commons;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully recovered"),
        @ApiResponse(
                responseCode = "204",
                description = "No content — request processed successfully, but no data to return",
                content = @Content(schema = @Schema())
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Malformed Request",
                content = {@Content(
                        schema = @Schema(implementation = ErrorInformation.class),
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = "{\"dateTime\":\"19/02/2025 20:28:01\"," +
                                                "\"httpStatus\":400," +
                                                "\"errorMessage\":\"Unable to register the user\"}"
                                )
                        })
                }
        ),
        @ApiResponse(responseCode = "500",
                description = "Internal Server Error",
                content = {@Content(
                        schema = @Schema(implementation = ErrorInformation.class),
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = "{\"dateTime\":\"19/02/2025 20:28:01\"," +
                                                "\"httpStatus\":500," +
                                                "\"erroMessage\":\"Error when making the request\"}"
                                )
                        })
                }
        )
})
public interface GeneralOperations {
}
package com.example.trello;

import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.URI;


public interface TrelloHttpClient {
    /**
     * Performs HTTP {@code GET} request and converts response.
     * <p>
     * The template variables in {@code url} are expanded using the given {@code params}, if any.
     *
     * @param url          The URL to make request to.
     * @param responseType The response object class object.
     * @param params       The URL query part or path parameters that should be expanded.
     * @param <T>          The response object type.
     *
     * @return The converted response.
     */
    <T> T get(String url, Class<T> responseType, String... params);

    /**
     * Performs HTTP {@code POST} request and converts response.
     * <p>
     * The template variables in {@code url} are expanded using the given {@code params}, if any.
     *
     * @param url          The URL to make request to.
     * @param body         The object to be converted to its {@code JSON} representation and {@code POST}ed. Use {@code
     *                     null} to send request with empty body.
     * @param responseType The response object class object.
     * @param params       The URL query part or path parameters that should be expanded.
     * @param <T>          The response object type.
     *
     * @return The converted response.
     */
    <T> T postForObject(String url, Object body, Class<T> responseType, String... params);

    /**
     * Performs HTTP {@code POST} request and returns the value of the {@code Location} header.
     * <p>
     * The template variables in {@code url} are expanded using the given {@code params}, if any.
     *
     * @param url    The URL to make request to.
     * @param body   The object to be converted to its {@code JSON} representation and {@code POST}ed. Use {@code null}
     *               to send request with empty body.
     * @param params The URL query part or path parameters that should be expanded.
     *
     * @return The {@code POST}ed resource location.
     */
    URI postForLocation(String url, Object body, String... params);

    /**
     * Performs HTTP {@code PUT} request and converts response.
     *
     * <p>
     * The template variables in {@code url} are expanded using the given {@code params}, if any.
     *
     * @param url          The URL to make request to.
     * @param body         The object to be converted to its {@code JSON} representation and {@code POST}ed. Use {@code
     *                     null} send request with empty body.
     * @param responseType The response object class object.
     * @param params       The URL query part or path parameters that should be expanded.
     * @param <T>          The response object type.
     *
     * @return The converted response.
     */
    <T> T putForObject(String url, Object body, Class<T> responseType, String... params);

    /**
     * Performs HTTP {@code DELETE} request and converts response.
     * <p>
     * The template variables in {@code url} are expanded using the given {@code params}, if any.
     *
     * @param url          The URL to make request to.
     * @param responseType The response object class object.
     * @param params       The URL query part or path parameters that should be expanded.
     * @param <T>          The response object type.
     *
     * @return The converted response.
     */
    <T> T delete(String url, Class<T> responseType, String... params);

    /**
     * Performs HTTP {@code POST} request and converts response. The {@code file} should be transmitted using {@code
     * multipart/form-data}.
     * <p>
     * This is optional method.
     * <p>
     * The template variables in {@code url} are expanded using the given {@code params}, if any.
     *
     * @param url          The URL to make request to.
     * @param file         The file to upload.
     * @param responseType The response object class object.
     * @param params       The URL query part or path parameters that should be expanded.
     * @param <T>          The response object type.
     *
     * @return The converted response.
     */
    default <T> T postFileForObject(String url, File file, Class<T> responseType, String... params) {
        throw new UnsupportedOperationException("Uploading files is not supported by default!");
    }
}


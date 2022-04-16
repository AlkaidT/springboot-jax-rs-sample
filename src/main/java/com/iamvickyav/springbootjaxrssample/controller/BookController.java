package com.iamvickyav.springbootjaxrssample.controller;

import com.iamvickyav.springbootjaxrssample.dto.BookDto;
import com.iamvickyav.springbootjaxrssample.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GET
    @Path("/books")
    @Produces(APPLICATION_JSON)
    public Response getAllBooks(@DefaultValue("") @QueryParam("author") String author) {
        return Response
                .status(Response.Status.OK)
                .entity(bookService.getAllBooks(author))
                .build();
    }

    @POST
    @Path("/books")
    @Produces(APPLICATION_JSON)
    public Response createBooksInBulk(List<BookDto> books){
        return Response
                .status(Response.Status.CREATED)
                .entity(bookService.createBooksInBulk(books))
                .build();
    }

    @PUT
    @Path("/books/{isbn}")
    public Response updateBook(@PathParam("isbn") String isbn, BookDto bookDto) {
        bookService.updateBook(isbn, bookDto);
        return Response
                .status(Response.Status.ACCEPTED)
                .build();
    }

    @DELETE
    @Path("/books/{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) {
        bookService.deleteBook(isbn);
        return Response
                .status(Response.Status.ACCEPTED)
                .build();
    }

    @DELETE
    @Path("/author/{authorName}")
    public Response deleteBooksByAuthor(@PathParam("authorName") String author) {
        bookService.deleteBooksByAuthor(author);
        return Response
                .status(Response.Status.ACCEPTED)
                .build();
    }
}

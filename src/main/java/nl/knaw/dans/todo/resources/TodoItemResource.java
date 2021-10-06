/*
 * Copyright (C) 2021 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.todo.resources;

import io.dropwizard.hibernate.UnitOfWork;
import nl.knaw.dans.todo.core.TodoItem;
import nl.knaw.dans.todo.db.TodoItemDAO;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
public class TodoItemResource {

    private final TodoItemDAO todoItemDAO;

    public TodoItemResource(TodoItemDAO todoItemDAO) {
        this.todoItemDAO = todoItemDAO;
    }

    @POST
    @UnitOfWork
    public TodoItem create(TodoItem todoItem) {
        return todoItemDAO.create(todoItem);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public TodoItem get(@PathParam("id") long id) {
        return todoItemDAO.findById(id).orElseThrow(() -> new NotFoundException("No such TO DO item"));
    }

}

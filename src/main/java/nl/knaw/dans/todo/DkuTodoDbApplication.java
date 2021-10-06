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

package nl.knaw.dans.todo;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.knaw.dans.todo.core.TodoItem;
import nl.knaw.dans.todo.db.TodoItemDAO;
import nl.knaw.dans.todo.resources.TodoItemResource;

public class DkuTodoDbApplication extends Application<DkuTodoDbConfiguration> {

    private final HibernateBundle<DkuTodoDbConfiguration> hibernate = new HibernateBundle<DkuTodoDbConfiguration>(TodoItem.class) {

        @Override
        public DataSourceFactory getDataSourceFactory(DkuTodoDbConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new DkuTodoDbApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DkuTodoDbConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(DkuTodoDbConfiguration config, Environment environment) {
        final TodoItemDAO dao = new TodoItemDAO(hibernate.getSessionFactory());
        environment.jersey().register(new TodoItemResource(dao));
    }

    @Override
    public String getName() {
        return "Dku Todo Db";
    }
}

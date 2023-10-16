package sever.middlewares;

import io.javalin.config.JavalinConfig;
import sever.exceptions.AccessDeniedException;

public class AuthMidddleware {

    public static void aply(JavalinConfig config){

        config.accessManager(((handler, context, routeRoles) -> {
            if(context.cookie("id") == null && !context.path().equals("/incidentes")){
                throw new AccessDeniedException();

            }else {
                handler.handle(context);
            }
        }));

    }

}

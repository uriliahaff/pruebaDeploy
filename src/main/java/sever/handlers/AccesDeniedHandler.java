package sever.handlers;

import io.javalin.Javalin;
import sever.exceptions.AccessDeniedException;

public class AccesDeniedHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(AccessDeniedException.class, (e, ctx)-> {
            ctx.render("401.hbs");
        });
    }
}

package sever.handlers;

import io.javalin.Javalin;
import sever.exceptions.AccessDeniedException;
import sever.exceptions.NoAuthExcpetion;

public class NoAuthHandler implements IHandler{
    @Override
    public void setHandle(Javalin app) {
        app.exception(NoAuthExcpetion.class, (e, ctx)-> {
            ctx.redirect("/login");
        });
    }
}

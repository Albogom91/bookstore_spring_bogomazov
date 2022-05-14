package com.belhard.bookstore.controller.command;

import com.belhard.bookstore.controller.command.impl.BookCommand;
import com.belhard.bookstore.controller.command.impl.BooksCommand;
import com.belhard.bookstore.controller.command.impl.ErrorCommand;
import com.belhard.bookstore.controller.command.impl.UserCommand;
import com.belhard.bookstore.controller.command.impl.UsersCommand;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory instance;

    private static class Holder {
        private static final CommandFactory instance = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Holder.instance;
    }

    private CommandFactory(){
    }

    public Command getCommand(String action) {
        Command command = CommandRegister.ERROR.getCommand();
        if (action == null) {
            return command;
        }
        try {
            return command = CommandRegister.valueOf(action.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            return command;
        }
    }
}

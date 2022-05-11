package com.belhard.bookstore.controller.command;

import com.belhard.bookstore.controller.command.impl.BookCommand;
import com.belhard.bookstore.controller.command.impl.BooksCommand;
import com.belhard.bookstore.controller.command.impl.ErrorCommand;
import com.belhard.bookstore.controller.command.impl.UserCommand;
import com.belhard.bookstore.controller.command.impl.UsersCommand;

public enum CommandRegister {
    BOOK(new BookCommand()),
    BOOKS(new BooksCommand()),
    USER(new UserCommand()),
    USERS(new UsersCommand()),
    ERROR(new ErrorCommand());

    private final Command command;

    CommandRegister(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

package carsharing.dbclasses;

import carsharing.menu.MainMenu;

import static carsharing.dbclasses.LocalUtils.runParam;

public class Host {

    public void start(String[] args) {
        DBClass dbClass = new DBClass(runParam(args));
        new MainMenu(dbClass).start();
        dbClass.closeDB();
    }
}

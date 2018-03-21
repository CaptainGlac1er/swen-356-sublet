package sublet.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Roles {
    public static HashMap<String, Role> CurrentRoles = new HashMap<>();

    public static void MakeStandardRoles() {
        Role newRole = new Role("Admin");
        newRole.setAdminSite(true);
        newRole.setMakePost(true);
        newRole.setManageRoles(true);
        newRole.setManagePosts(true);
        CurrentRoles.put(newRole.getName(), newRole);
        newRole = new Role("Guest");
        newRole.setSeePosts(true);
        CurrentRoles.put(newRole.getName(), newRole);
        newRole = new Role("User");
        newRole.setMakePost(true);
        newRole.setSeePosts(true);
        CurrentRoles.put(newRole.getName(), newRole);
        newRole = new Role("Mod");
        newRole.setSeePosts(true);
        newRole.setMakePost(true);
        newRole.setManagePosts(true);
        CurrentRoles.put(newRole.getName(), newRole);
        newRole = new Role("RIT");
        CurrentRoles.put(newRole.getName(), newRole);
    }

    public static boolean CanModListings(ArrayList<Role> userRoles) {
        boolean checkIfMod = false;
        for (Role r : userRoles)
            if (checkIfMod = r.isManagePosts())
                break;
        return checkIfMod;
    }
}

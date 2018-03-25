package sublet.models;

public class Role {
    private String name;
    private boolean seePosts = false;
    private boolean makePost = false;
    private boolean adminSite = false;
    private boolean manageRoles = false;
    private boolean managePosts = false;

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeePosts() {
        return seePosts || adminSite;
    }

    public void setSeePosts(boolean seePosts) {
        this.seePosts = seePosts;
    }

    public boolean isMakePost() {
        return makePost || adminSite;
    }

    public void setMakePost(boolean makePost) {
        this.makePost = makePost;
    }

    public boolean isAdminSite() {
        return adminSite;
    }

    public void setAdminSite(boolean adminSite) {
        this.adminSite = adminSite;
    }

    public boolean isManageRoles() {
        return manageRoles || adminSite;
    }

    public void setManageRoles(boolean manageRoles) {
        this.manageRoles = manageRoles;
    }

    public boolean isManagePosts() {
        return managePosts || adminSite;
    }

    public void setManagePosts(boolean managePosts) {
        this.managePosts = managePosts;
    }

}

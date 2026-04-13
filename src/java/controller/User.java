/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Vinz
 */
public class User {
    private String email;
    private String password;
    private String userrole;
    
    User(String email, String password, String userrole){
        this.email = email;
        this.password = password;
        this.userrole = userrole;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userrole;
    }
    
}

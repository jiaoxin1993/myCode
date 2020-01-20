package javaSE.annotation;

import javaSE.annotation.UseCase.UseCases;

public class PasswordUtils {
    @UseCases(id="47",description="Passwords must contain at least one numeric")
     public boolean validatePassword(String password) {
         return (password.matches("\\w*\\d\\w*"));
     }

     @UseCases(id ="48")
     public String encryptPassword(String password) {
         return new StringBuilder(password).reverse().toString();
     }
     public String Password(String password) {
         return new StringBuilder(password).reverse().toString();
     }
}

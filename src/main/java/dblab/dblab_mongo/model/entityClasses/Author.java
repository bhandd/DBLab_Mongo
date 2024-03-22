package dblab.dblab_mongo.model.entityClasses;

import java.time.LocalDate;
import java.util.Date;

public class Author {

  // private int age;
   private String fullName;
   private LocalDate birthdate;

   //private String lName;
/** Constructor for the Authors-class
 *
 * */
   public Author( String fullName, LocalDate birthdate/*String lName*/) {
      this.birthdate = birthdate;
      this.fullName = fullName;
    //  this.lName = lName;
   }
   public Author(String fullName){
      this.fullName = fullName;
      this.birthdate = LocalDate.now();
   }

   public Author() {
      this.fullName = "";
      this.birthdate = LocalDate.now();
   }

   public LocalDate getAge() {
      return birthdate;
   }

   public void setAge(LocalDate birthdate) {
      this.birthdate = birthdate;
   }

   public String getFullName() {
      return fullName;
   }

   public void setfName(String fName) {
      this.fullName = fName;
   }


   @Override
   public String toString() {
      return
                fullName;
   }
}

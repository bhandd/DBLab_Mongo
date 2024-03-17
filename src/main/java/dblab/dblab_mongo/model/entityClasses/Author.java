package dblab.dblab_mongo.model.entityClasses;

import java.util.Date;

public class Author {

  // private int age;
   private String fullName;
   private Date birthdate;

   //private String lName;
/** Constructor for the Authors-class
 *
 * */
//TODO: check if we need a fName and lName string or if a fullName will suffice
   public Author( String fullName, Date birthdate/*String lName*/) {
      this.birthdate = birthdate;
      this.fullName = fullName;
    //  this.lName = lName;
   }

   public Author() {
      this.fullName = "";
   }

   public Date getAge() {
      return birthdate;
   }

   public void setAge(Date birthdate) {
      this.birthdate = birthdate;
   }

   public String getFullName() {
      return fullName;
   }

   public void setfName(String fName) {
      this.fullName = fName;
   }

   //TODO:Check if needed
//   public String getlName() {
//      return lName;
//   }


//TODO: check if needed

//   public void setlName(String lName) {
//      this.lName = lName;
//   }

   @Override
   public String toString() {
      return "Author{" +
              "id=" + birthdate +
              ", fName='" + fullName + '\'' +
              '}';
   }
}

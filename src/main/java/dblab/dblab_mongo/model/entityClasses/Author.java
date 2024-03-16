package dblab.dblab_mongo.model.entityClasses;

public class Author {

   private int age;
   private String fullName;

   //private String lName;
/** Constructor for the Authors-class
 *
 * */
//TODO: check if we need a fName and lName string or if a fullName will suffice
   public Author(int age, String fullName/*String lName*/) {
      this.age = age;
      this.fullName = fullName;
    //  this.lName = lName;
   }

   public Author() {
      this.fullName = "";
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
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
              "id=" + age +
              ", fName='" + fullName + '\'' +
              '}';
   }
}

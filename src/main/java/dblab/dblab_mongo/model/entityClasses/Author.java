package dblab.dblab_mongo.model.entityClasses;

public class Author {

   private int id;
   private String fullName;

   //private String lName;
/** Constructor for the Authors-class
 *
 * */
//TODO: check if we need a fName and lName string or if a fullName will suffice
   public Author(int id, String fullName/*String lName*/) {
      this.id = id;
      this.fullName = fullName;
    //  this.lName = lName;
   }

   public Author() {

   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
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
              "id=" + id +
              ", fName='" + fullName + '\'' +
              '}';
   }
}

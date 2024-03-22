package dblab.dblab_mongo.model.entityClasses;

import dblab.dblab_mongo.model.Dialog.FooBook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of a book.
 * 
 * @author anderslm@kth.se
 */
public class Book {

    private String isbn; // should check format
    private String title;
    private String published;

    private String grade;

   //  private Genre genre;
    private String genre;

    private String author;

     //  private ArrayList<Author> authors;
    // TODO:
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"

    //TODO: avkommentera arraylist med authors då detta krävs för att representera relationen mellan book och author
    // avkommentera också String author som parameter i konstruktiorn
    // när detta göra behöver man anpassa implementeringen i övriga programmet
    public Book(String isbn, String title, List<Author> author, String published, String genre, String grade) {

        this.isbn = isbn;
        this.title = title;
      //  this.authors = new ArrayList<>();
         //   this.authors.add(author.get(0));
        this.author = author.toString();
        this.published = published;
       this.genre = genre;
        this.grade = grade;

    }

    public Book() {

    }


//
//    public Book(){
//        this.bookId = 0;
//
//        this.isbn = null;
//        this.title = null;
//      //  this.authors = new ArrayList<>();
////        this.authors.add(new Author(author));
//        //    this.author.setfName(author);
//
//        this.published = new java.sql.Date(2000);
//       // this.genre = Genre.UNDEFINED;
//        this.grade = grade;
//
//
//    }




    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getPublished() {
        return published;
    }

    public String getGenre() {
        return genre;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getGrade() {
        return grade;
    }

    //TODO: use with Authors list
//    public ArrayList<Author> getAuthors() {
//        return authors;
//    }



    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublished(String published) {
        this.published = published;
    }


    public void setGrade(String grade) {
        this.grade = grade;
    }


    public void setGenre(String genre) {
        this.genre = genre ;
    }


    /**Add author to the list of authors
     *
     * */
//    public void addAuthor(ArrayList<Author> authors) {
//        this.authors = authors;
//    }





    /**For printing the authors from the List of authors
     *
     * */

//public void printAuthors(){
//
//        for (int i=0; i < authors.size(); i++){
//            System.out.println("Name:" + authors.get(i).getFullName());
//            System.out.println("ID: "+ authors.get(i).getId());
//
//        }
//
//}

//public ArrayList<String> getAuthorsNames(ArrayList<Author> authors){
//  ArrayList<String> nameList = new ArrayList<>();
//
//        for (int i=0; i < authors.size(); i++){
//            nameList.add(authors.get(i).getFullName()) ;
//        }
//        return nameList;
//}
public String addAuthor(String name){
    author = name;
    return name;
}
    @Override
    public String toString() {
        return isbn + ", " + title + ", "/* + getAuthorsNames(this.authors) +*/+ author + ", " +  published + ", " + genre + ", " + grade;
    }
}

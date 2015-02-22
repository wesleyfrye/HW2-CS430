import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

public class DatabaseConnection {

	Connection _connection;
	GUI _gui;

	public DatabaseConnection(GUI gui){
		_gui = gui;
		String url = "jdbc:postgresql://faure.cs.colostate.edu:5432/weswf";
		Properties props = new Properties();
		props.setProperty("user", "weswf");
		props.setProperty("password", "828458947");
		try{
			_connection = DriverManager.getConnection(url, props);
		}catch(SQLException connectionException){
			_gui.Display("Error in establishing connection");
		}
	}

	public boolean addBook(String ISBN, String name, int yearOfPublication, int numberOfCopies){
		boolean bookAdded = false;
		String addBookString = "INSERT INTO Books " +
				"(ISBN, Name, YearOfPublication, NumberOfCopies " +
				"VALUES (?, ?, ? ,?)";
		try{
			PreparedStatement addBook = _connection.prepareStatement(addBookString);
			addBook.setString(1, ISBN);
			addBook.setString(2, name);
			addBook.setInt(3, yearOfPublication);
			addBook.setInt(4, numberOfCopies);
			addBook.executeUpdate();
			bookAdded = true;
		}catch(SQLException addBookException){
			_gui.Display("Error in adding book: " + ISBN);
			return false;
		}
		return bookAdded;
	}

	public boolean addBook2Student(int studentID, String ISBN){
		boolean rentalAdded = false;
		String addRentalString = "INSERT INTO Books2Students " +
				"(StudentID, ISBN, IssueDate, DueDate) " +
				"VALUES ( ?, ?, ?, ?);";
		Calendar cal = Calendar.getInstance();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTime().getTime());
		cal.add(Calendar.DAY_OF_MONTH, 30);
		java.util.Date calDueDate = cal.getTime();
		java.sql.Date dueDate = new java.sql.Date(calDueDate.getTime());
		if(updateBookCopies(ISBN, -1)){
			try{
				PreparedStatement addRental = _connection.prepareStatement(addRentalString);
				addRental.setInt(1, studentID);
				addRental.setString(2, ISBN);
				addRental.setTimestamp(3, timestamp);
				addRental.setDate(4, dueDate);
				addRental.executeUpdate();
				rentalAdded = true;
			}catch(SQLException addBook2StudentException){
				_gui.Display("Error in addBook2Student: " +
						"Student ID - " + studentID +
						"ISBN - " + ISBN);
				return false;
			}
		}
		return rentalAdded;
	}

	public boolean addStudent(int studentID, String firstName, String lastName, String degree){
		boolean studentAdded = false;
		String addStudentString = "INSERT INTO Students " +
				"(StudentID, FirstName, LastName, Degree) " +
				"VALUES (?, ?, ?, ?);";
		try{
			PreparedStatement addStudent = _connection.prepareStatement(addStudentString);
			addStudent.setInt(1, studentID);
			addStudent.setString(2, firstName);
			addStudent.setString(3, lastName);
			addStudent.setString(4, degree);
			addStudent.executeUpdate();
			studentAdded = true;
		}catch(SQLException addStudentException){
			_gui.Display("Error in adding student: " + studentID);
			return false;
		}
		return studentAdded;
	}

	public boolean deleteBook(String ISBN){
		boolean bookDeleted = false;
		String deleteBookString = "DELETE FROM Books " +
				"WHERE ISBN = ?;";
		try{
			PreparedStatement deleteBook = _connection.prepareStatement(deleteBookString);
			deleteBook.setString(1, ISBN);
			deleteBook.executeUpdate();
			bookDeleted = true;
		}catch(SQLException deleteBookException){
			_gui.Display("Error in deleting book: ISBN = " + ISBN);
			return false;
		}
		return bookDeleted;
	}

	public boolean deleteBook2Student(int studentID, String ISBN){
		boolean deletedRental = false;
		String deleteRentalString = "DELETE FROM Books2Students " +
				"WHERE StudentID = ? AND ISBN = ?;";
		try{
			PreparedStatement deleteRental = _connection.prepareStatement(deleteRentalString);
			deleteRental.setInt(1, studentID);
			deleteRental.setString(2, ISBN);
			deleteRental.executeUpdate();
			deletedRental = true;
		}catch(SQLException deleteBook2StudentException){
			_gui.Display("Error in deleteBook2Student: " +
					"StudentID = " + studentID +
					" ISBN = " + ISBN);
		}
		return deletedRental;
	}
	
	public boolean deleteStudent(int studentID){
		boolean studentDeleted = false;
		String deleteStudentString = "DELETE FROM Students " +
				"WHERE StudentID = ?;";
		try{
			PreparedStatement deleteStudent = _connection.prepareStatement(deleteStudentString);
			deleteStudent.setInt(1, studentID);
			deleteStudent.executeUpdate();
			studentDeleted = true;
		}catch(SQLException deleteStudentException){
			_gui.Display("Error in deleteStudent: StudentID = " + studentID);
			return false;
		}
		return studentDeleted;
	}
	
	public ResultSet getBookTable(){
		ResultSet queryResult = null;
		String bookQueryString = "SELECT * " +
				"FROM Books;";
		try{
			PreparedStatement bookQuery = _connection.prepareStatement(bookQueryString);
			queryResult = bookQuery.executeQuery();
		}catch(SQLException getStudentTableException){
			_gui.Display("Error in book query");
		}
		return queryResult;
	}

	public ResultSet getRentalTable(){
		ResultSet queryResult = null;
		//TODO Join tables
		return queryResult;
	}
	
	public ResultSet getStudentTable(){
		ResultSet queryResult = null;
		String studentQueryString = "SELECT * " +
				"FROM Students;";
		try{
			PreparedStatement studentQuery = _connection.prepareStatement(studentQueryString);
			queryResult = studentQuery.executeQuery();
		}catch(SQLException getStudentTableException){
			_gui.Display("Error in student query");
		}
		return queryResult;
	}
	
	private boolean updateBookCopies(String ISBN, int changeCopiesBy){
		boolean copiesUpdated = false;
		String updateCopiesString = "UPDATE Books " +
				"SET numberofcopies=numberofcopes + ? " + 
				"WHERE ISBN = ?;";
		try{
			PreparedStatement updateCopies = _connection.prepareStatement(updateCopiesString);
			updateCopies.setInt(1, changeCopiesBy);
			updateCopies.setString(2, ISBN);
			updateCopies.executeUpdate();
			copiesUpdated = true;
		}catch(SQLException updateBookCopiesException){
			_gui.Display("Error in updating book copies: ISBN = " + ISBN);
			return false;
		}
		return copiesUpdated;
	}

}

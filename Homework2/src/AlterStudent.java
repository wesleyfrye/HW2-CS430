import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class AlterStudent extends Frame{

	private static final long serialVersionUID = 3576553329856806879L;
	
	public final static int 
	WINDOW_WIDTH = 1400,
	WINDOW_HEIGHT = 500;
	
	DatabaseConnection _dbConnection;
	
	private Label _idTopLabel = new Label("Student ID", Label.CENTER);
	private Label _firstNameTopLabel = new Label("First Name", Label.CENTER);
	private Label _lastNameTopLabel = new Label("Last Name", Label.CENTER);
	private Label _degreeTopLabel = new Label("Degree", Label.CENTER);
	private List _id = new List();
	private List _firstName = new List();
	private List _lastName = new List();
	private List _degree = new List();
	
	private TextField _textID = new TextField();
	private TextField _textFirstName = new TextField();
	private TextField _textLastName = new TextField();
	private TextField _textDegree = new TextField();
	private JPopupMenu _rightClickMenuStudents = new JPopupMenu();
	private JMenuItem _removeStudent = new JMenuItem("Remove Student");
	
	private Label _idLabel = new Label("Student ID", Label.RIGHT);
	private Label _firstNameLabel = new Label("First Name", Label.RIGHT);
	private Label _lastNameLabel = new Label("Last Name", Label.RIGHT);
	private Label _degreeLabel = new Label("Degree", Label.RIGHT);
	private Button _submit = new Button("Add");
	private Button _cancel = new Button("Cancel");

	public AlterStudent(DatabaseConnection dbConnection){
		super("Alter Student");
		_dbConnection = dbConnection;
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new BorderLayout(5, 5));
		
		Panel top = new Panel();
		add("North", top);
		top.setLayout(new GridLayout(1, 4));
		top.add(_idTopLabel);
		top.add(_firstNameTopLabel);
		top.add(_lastNameTopLabel);
		top.add(_degreeTopLabel);
		
		Panel center = new Panel();
		add("Center", center);
		center.setLayout(new GridLayout(1, 4));
		center.add(_id);
		center.add(_firstName);
		center.add(_lastName);
		center.add(_degree);
		
		Panel bottom = new Panel();
		add("South", bottom);
		bottom.setLayout(new GridLayout(5, 2));
		bottom.add(_idLabel);
		bottom.add(_textID);
		bottom.add(_firstNameLabel);
		bottom.add(_textFirstName);
		bottom.add(_lastNameLabel);
		bottom.add(_textLastName);
		bottom.add(_degreeLabel);
		bottom.add(_textDegree);
		bottom.add(_submit);
		bottom.add(_cancel);
		
		setVisible(true);
		
		//System box
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windE){
				dispose();
			}
		});
		
		//Right click menu
		_id.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0){ }
			@Override
			public void mouseEntered(MouseEvent arg0){ }
			@Override
			public void mouseExited(MouseEvent arg0){ }
			@Override
			public void mousePressed(MouseEvent arg0){
				messagesPopUp(arg0);
			}
			@Override
			public void mouseReleased(MouseEvent arg0){
				messagesPopUp(arg0);
			}
			private void messagesPopUp(MouseEvent me){
				_rightClickMenuStudents.show(me.getComponent(), 
						me.getX(), me.getY());
			}
		});
		
		_rightClickMenuStudents.add(_removeStudent);
		
		_removeStudent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				
			}
		});
		
	}
	
	private void addToStudentList(ResultSet addMe) throws SQLException{
		String 
		studentID = Integer.toString(addMe.getInt(1)),
		firstName = addMe.getString(2),
		lastName = addMe.getString(3),
		degree = addMe.getString(4);
		String ids[] = _id.getItems();
		for(int i = 0; i < ids.length; i++){
			if(ids[i].equals(studentID))
				return;
		}
		_id.add(studentID);
		_id.add(firstName);
		_id.add(lastName);
		_id.add(degree);
	}
	
	private void populateStudentList(){
		ResultSet students = _dbConnection.getStudentTable();
		try{
			while(students.next()){
				addToStudentList(students);
			}
		}catch(SQLException populateStudentException){
			//TODO
		}
	}
	
	public static void main(String args[]){
		DatabaseConnection dbc = null;
		AlterStudent test = new AlterStudent(dbc);
	}
	
}

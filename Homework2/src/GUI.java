import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GUI extends Frame{
	
	private static final long serialVersionUID = -6139728716733494139L;

	public final static int
	WINDOW_WIDTH = 400,
	WINDOW_HEIGHT = 300;
	
	private DatabaseConnection _dbConnection;
	
	private Button _alterBook = new Button("Book Table");
	private Button _alterStudent = new Button("Student Table");
	private Button _alterRental = new Button("Book To Student Table");
	
	public GUI(){
		super("Homework 2");
		
		_dbConnection = new DatabaseConnection(this);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new BorderLayout(5, 5));
		
		Panel center = new Panel();
		add("Center", center);
		center.setLayout(new GridLayout(3, 1));
		center.add(_alterBook);
		center.add(_alterStudent);
		center.add(_alterRental);
		
		setVisible(true);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windE){
				dispose();
			}
		});
		
		_alterStudent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AlterStudent altStudent = new AlterStudent(_dbConnection);
			}
		});
		
	}
	
	public void Display(String msg){
		System.out.println(msg);
	}
	
	public static void main(String args[]){
		GUI test = new GUI();
	}
	
}












import static org.junit.Assert.assertEquals;

import domain.Student;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import javax.swing.*;

public class TestStudent {
    StudentXMLRepo studentXMLRepo = new StudentXMLRepo("fisiere/testStudentXmlRepo.xml");
    StudentValidator studentValidator = new StudentValidator();

    TemaXMLRepo temaXMLRepo = new TemaXMLRepo("fisiere/testTemaXmlRepo.xml");

    TemaValidator temaValidator = new TemaValidator();

    NotaXMLRepo notaXMLRepo = new NotaXMLRepo("fisiere/testNotaXmlRepo.xml");

    NotaValidator notaValidator = new NotaValidator(studentXMLRepo, temaXMLRepo);

    String defaultName="defaultName";
    Integer defaultGrupa = 123;
    String defaultEmail = "a@e.com";
    String defaultId = "defaultId";




    Service serv = new Service(studentXMLRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);

    @Test
    public void testAdd() {
        Student stud = new Student("100", "NewNane", 934, "a@e.com");
        serv.addStudent(stud);
        assertEquals(serv.findStudent("100").toString(), stud.toString());

        Student stud1 = new Student("id2", "DIFFNAME", 123, "A1@gmail.com");
        serv.addStudent(stud1);
        assertEquals(serv.findStudent("id2").toString(), stud1.toString());
        int len=0;
        for(Student s: serv.getAllStudenti()) {
            len++;
        }
        Student stud2 = new Student("id2", "DIFFNAME", -123, "A1@gmail.com");
        try {
            serv.addStudent(stud2);
            assertEquals(1, 2);
        } catch (ValidationException ve) {
            assertEquals(1, 1);
        }
        assertEquals(len, 2);

        serv.deleteStudent("100");
        serv.deleteStudent("id2");
    }

    public void tryAdd(Student stud) {
        try {
            serv.addStudent(stud);
            serv.deleteStudent(stud.getID());
            assertEquals(1, 0);
        }
        catch (ValidationException ve) {
            assertEquals(1, 1);
        }
    }

    public void checkAdd(Student stud) {
        serv.addStudent(stud);
        assertEquals(serv.findStudent(stud.getID()).toString(), stud.toString());
    }
    @Test
    public void testAddStudentIdCheck() {
//        id should have length greater than 0 and smaller than 30

        Student stud = new Student(null, defaultName, defaultGrupa, defaultEmail);
        tryAdd(stud);

        stud = new Student("", defaultName, defaultGrupa, defaultEmail);
        tryAdd(stud);

        stud = new Student("1", defaultName, defaultGrupa, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent("1");

        stud = new Student("012345678901234567890123456789", defaultName, defaultGrupa, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent("012345678901234567890123456789");

        stud = new Student("0123456789012345678901234567890", defaultName, defaultGrupa, defaultEmail);
        tryAdd(stud);

        stud = new Student(defaultId, defaultName, defaultGrupa, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent(defaultId);

    }

    @Test
    public void testAddStudentCheckGroup() {
        Student stud = new Student(defaultId, defaultName, -1, defaultEmail);
        tryAdd(stud);

        stud = new Student(defaultId, defaultName, 0, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent(defaultId);

        stud = new Student(defaultId, defaultName, 999, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent(defaultId);

        stud = new Student(defaultId,defaultName,1000, defaultEmail);
        tryAdd(stud);
    }

    @Test
    public void testAddStudentCheckName() {
        Student stud = new Student(defaultId,null, defaultGrupa, defaultEmail);
        tryAdd(stud);

        stud = new Student(defaultId,"", defaultGrupa, defaultEmail);
        tryAdd(stud);

        stud = new Student(defaultId, "a1", defaultGrupa, defaultEmail);
        tryAdd(stud);

        stud = new Student(defaultId, "a", defaultGrupa, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent(defaultId);

        stud = new Student(defaultId, "qwertyuioplkjhgfdsazxcvbnmlkjh", defaultGrupa, defaultEmail);
        checkAdd(stud);
        serv.deleteStudent(defaultId);

        stud = new Student(defaultId, "qwertyuioplkjhgfdsazxcvbnmlokjh", defaultGrupa, defaultEmail);
        tryAdd(stud);
    }

    @Test
    public void testAddStudentCheckEmail() {
        Student stud = new Student(defaultId, defaultName, defaultGrupa, "a@e.com");
        checkAdd(stud);
        serv.deleteStudent(defaultId);

        stud = new Student(defaultId, defaultName, defaultGrupa, "notgood");
        tryAdd(stud);

    }
}

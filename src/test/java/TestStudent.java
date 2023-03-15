
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

public class TestStudent {
    StudentXMLRepo studentXMLRepo = new StudentXMLRepo("fisiere/testStudentXmlRepo.xml");
    StudentValidator studentValidator = new StudentValidator();

    TemaXMLRepo temaXMLRepo = new TemaXMLRepo("fisiere/testTemaXmlRepo.xml");

    TemaValidator temaValidator = new TemaValidator();

    NotaXMLRepo notaXMLRepo = new NotaXMLRepo("fisiere/testNotaXmlRepo.xml");

    NotaValidator notaValidator = new NotaValidator(studentXMLRepo, temaXMLRepo);





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
        assertEquals(len, 2);
    }
}

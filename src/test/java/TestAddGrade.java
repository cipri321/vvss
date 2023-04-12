import curent.Curent;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static java.time.temporal.ChronoUnit.DAYS;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class TestAddGrade {
    StudentXMLRepo studentXMLRepo = new StudentXMLRepo("fisiere/testStudentXmlRepo.xml");
    StudentValidator studentValidator = new StudentValidator();

    TemaXMLRepo temaXMLRepo = new TemaXMLRepo("fisiere/testTemaXmlRepo.xml");

    TemaValidator temaValidator = new TemaValidator();

    NotaXMLRepo notaXMLRepo = new NotaXMLRepo("fisiere/testNotaXmlRepo.xml");

    NotaValidator notaValidator = new NotaValidator(studentXMLRepo, temaXMLRepo);

    String defaultStudentName="defaultName";
    Integer defaultStudentGrupa = 123;
    String defaultStudentEmail = "a@e.com";
    String defaultStudentId = "defaultId";

    String defaultAssignmentDescriere = "defaultDescriere";
    Integer defaultAssignmentDeadline = 10;
    Integer defaultAssignmentPrimire = 10;
    String defaultAssignmentId = "100";

    String defaultGradeId = "gradeId";
    double defaultGrade = 9.5;

    String defaultFeedback = "defaultFeedback";

    LocalDate defaultDate = Curent.getStartDate().plus((Curent.getCurrentWeek()-1)*7, DAYS);

    Service serv = new Service(studentXMLRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);


    @Test
    public void testAddGrade() {
        Student stud = new Student(defaultStudentId, defaultStudentName, defaultStudentGrupa, defaultStudentEmail);
        Tema tema = new Tema(defaultAssignmentId, defaultAssignmentDescriere, defaultAssignmentDeadline, defaultAssignmentPrimire);
        serv.addStudent(stud);
        serv.addTema(tema);

        Nota nota = new Nota(defaultGradeId, stud.getID(), tema.getID(), defaultGrade, defaultDate);
        serv.addNota(nota, defaultFeedback);
    }


}

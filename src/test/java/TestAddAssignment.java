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
import validation.ValidationException;

import static org.junit.Assert.assertEquals;

public class TestAddAssignment {
    StudentXMLRepo studentXMLRepo = new StudentXMLRepo("fisiere/testStudentXmlRepo.xml");
    StudentValidator studentValidator = new StudentValidator();

    TemaXMLRepo temaXMLRepo = new TemaXMLRepo("fisiere/testTemaXmlRepo.xml");

    TemaValidator temaValidator = new TemaValidator();

    NotaXMLRepo notaXMLRepo = new NotaXMLRepo("fisiere/testNotaXmlRepo.xml");

    NotaValidator notaValidator = new NotaValidator(studentXMLRepo, temaXMLRepo);

    String defaultDescriere = "defaultDescriere";
    Integer defaultDeadline = 10;
    Integer defaultPrimire = 5;
    String defaultId = "100";


    Service serv = new Service(studentXMLRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);

    public void tryAdd(Tema tema) {
        try {
            serv.addTema(tema);
            serv.deleteTema(tema.getID());
            assertEquals(1, 0);
        }
        catch (ValidationException ve) {
            assertEquals(1, 1);
        }
    }

    public void checkAdd(Tema tema) {
        serv.addTema(tema);
        assertEquals(serv.findTema(tema.getID()).toString(), tema.toString());
        serv.deleteTema(tema.getID());
    }
    @Test
    public void testId() {
        Tema tema = new Tema("", defaultDescriere, defaultDeadline, defaultPrimire);
        tryAdd(tema);

        tema = new Tema(null, defaultDescriere, defaultDeadline, defaultPrimire);
        tryAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, defaultDeadline, defaultPrimire);
        checkAdd(tema);
    }

    @Test
    public void testDescriere() {
        Tema tema = new Tema(defaultId, "", defaultDeadline, defaultPrimire);
        tryAdd(tema);
    }

    @Test
    public void testDeadline() {
        Tema tema = new Tema(defaultId, defaultDescriere, 0, defaultPrimire);
        tryAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, 1, defaultPrimire);
        checkAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, 14, defaultPrimire);
        checkAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, 15, defaultPrimire);
        tryAdd(tema);
    }

    @Test
    public void testPrimire() {
        Tema tema = new Tema(defaultId, defaultDescriere, defaultDeadline, 0);
        tryAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, defaultDeadline, 1);
        checkAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, defaultDeadline, 14);
        checkAdd(tema);

        tema = new Tema(defaultId, defaultDescriere, defaultDeadline, 15);
        tryAdd(tema);
    }
}

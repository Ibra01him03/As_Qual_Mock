import org.example.ServiceException;
import org.example.UserService;
import org.example.Utilisateur;
import org.example.UtilisateurApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.AssertEquals.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UtilisateurApi utilisateurApiMock;
    @Test
    public void testCreerUtilisateur() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");
        // TODO : Configuration du comportement du mock, utiliser la
//directive « when » avec sa méthode « thenReturn »
        doNothing().when(utilisateurApiMock).creerUtilisateur(utilisateur);
        // TODO : Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);
        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);

        // Vérification de l'appel à l'API
        // On vérifie que la méthode creerUtilisateur du mock a bien été appelée une fois
        verify(utilisateurApiMock, times(1)).creerUtilisateur(utilisateur);
    }
    @Test(expected = ServiceException.class)
    public void testCreerUtilisateur_Exception() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Configuration du comportement du mock pour lever une exception
        doThrow(new ServiceException("Echec de la création de l'utilisateur"))
                .when(utilisateurApiMock).creerUtilisateur(utilisateur);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);
    }
    @Test
    public void testCreerUtilisateur_ErreurValidation() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "email_invalide"); // Email invalide

        // Configuration du mock pour retourner true (comme si la création réussissait malgré l'erreur de validation)
        doNothing().when(utilisateurApiMock).creerUtilisateur(utilisateur);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        try {
            userService.creerUtilisateur(utilisateur);
        } catch (ServiceException e) {
            // La méthode devrait lancer une exception ServiceException en cas d'erreur de validation
            // On vérifie que l'API n'a jamais été appelée (car il y avait une erreur de validation)
            verify(utilisateurApiMock, never()).creerUtilisateur(utilisateur);
            return;
        }

        // Si aucune exception n'est lancée, le test doit échouer
        fail("La méthode n'a pas levé d'exception ServiceException pour une erreur de validation.");
    }
    @Test
    public void testCreerUtilisateur_IdUnique() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur(123,"Jean", "Dupont", "jeandupont@email.com");

        // Configuration du mock pour retourner true (comme si la création réussissait)
        doNothing().when(utilisateurApiMock).creerUtilisateur(utilisateur);

        // Définition d'un ID fictif
        int idUtilisateur = 123;

        // Configuration du mock pour renvoyer l'ID

        when(utilisateurApiMock.get(utilisateur)).thenReturn(idUtilisateur);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);

        // Vérification de l'ID de l'utilisateur
        assertEquals(idUtilisateur, utilisateur.getId());
    }


}
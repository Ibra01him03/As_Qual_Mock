import org.example.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JeuTest {
    @Test
    public void testJouer_JeuFerme() {
        Banque banqueMock = mock(Banque.class);
        Jeu jeu = new Jeu(banqueMock);
        jeu.fermer();

        Joueur joueurMock = mock(Joueur.class);
        De de1Mock = mock(De.class);
        De de2Mock = mock(De.class);

        assertFalse(jeu.jouer(joueurMock, de1Mock, de2Mock));
        assertFalse(jeu.estOuvert());
    }

    @Test
    public void testJouer_JoueurInsolvable() throws DebitImpossibleException {
        Banque banqueMock = mock(Banque.class);
        when(banqueMock.est_solvable()).thenReturn(true);

        Jeu jeu = new Jeu(banqueMock);
        Joueur joueurMock = mock(Joueur.class);
        when(joueurMock.mise()).thenReturn(10); // Mise du joueur
        doThrow(new DebitImpossibleException("Le joueur est insolvable")).when(joueurMock).debiter(10);

        De de1Mock = mock(De.class);
        De de2Mock = mock(De.class);

        jeu.jouer(joueurMock, de1Mock, de2Mock);

        assertFalse(jeu.estOuvert());
        verify(de1Mock, never()).lancer(); // Vérifie que le dé 1 n'a pas été lancé
        verify(de2Mock, never()).lancer(); // Vérifie que le dé 2 n'a pas été lancé
    }

    @Test
    public void testJouer_SommeDesDésDifferentDe7() throws DebitImpossibleException {
        Banque banqueMock = mock(Banque.class);
        when(banqueMock.est_solvable()).thenReturn(true);

        Jeu jeu = new Jeu(banqueMock);
        Joueur joueurMock = mock(Joueur.class);
        when(joueurMock.mise()).thenReturn(10); // Mise du joueur

        De de1Mock = mock(De.class);
        when(de1Mock.lancer()).thenReturn(1); // Simule le lancer de dé
        De de2Mock = mock(De.class);
        when(de2Mock.lancer()).thenReturn(6); // Simule le lancer de dé

        assertFalse(jeu.jouer(joueurMock, de1Mock, de2Mock));
        assertFalse(jeu.estOuvert());
    }

    @Test
    public void testJouer_SommeDesDésEgaleA7() throws DebitImpossibleException {
        Banque banqueMock = mock(Banque.class);
        when(banqueMock.est_solvable()).thenReturn(true);

        Jeu jeu = new Jeu(banqueMock);
        Joueur joueurMock = mock(Joueur.class);
        when(joueurMock.mise()).thenReturn(10); // Mise du joueur

        De de1Mock = mock(De.class);
        when(de1Mock.lancer()).thenReturn(3); // Simule le lancer de dé
        De de2Mock = mock(De.class);
        when(de2Mock.lancer()).thenReturn(4); // Simule le lancer de dé

        assertTrue(jeu.jouer(joueurMock, de1Mock, de2Mock));
        assertTrue(jeu.estOuvert());
    }

    @Test
    public void testJouer_BanqueInsolvableApresPaiementGain() throws DebitImpossibleException {
        Banque banqueMock = mock(Banque.class);
        when(banqueMock.est_solvable()).thenReturn(true, false); // Banque solvable puis insolvable

        Jeu jeu = new Jeu(banqueMock);
        Joueur joueurMock = mock(Joueur.class);
        when(joueurMock.mise()).thenReturn(10); // Mise du joueur

        De de1Mock = mock(De.class);
        when(de1Mock.lancer()).thenReturn(3); // Simule le lancer de dé
        De de2Mock = mock(De.class);
        when(de2Mock.lancer()).thenReturn(4); // Simule le lancer de dé

        assertTrue(jeu.jouer(joueurMock, de1Mock, de2Mock));
        assertFalse(jeu.estOuvert());
    }
}

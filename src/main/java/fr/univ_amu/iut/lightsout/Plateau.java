package fr.univ_amu.iut.lightsout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;

public class Plateau extends GridPane {
    private static final Point2D[] directions = {
            new Point2D(1, 0),
            new Point2D(0, 1),
            new Point2D(0, -1),
            new Point2D(-1, 0)
    };

    private int taille;
    private IntegerProperty nombreDeCoupsJoués;
    private IntegerProperty nombreCarreauEteint;
    private BooleanProperty aGagné;
    private Carreau[][] carreaux;
    private EventHandler<ActionEvent> carreauListener = actionEvent -> {
        Carreau carreauChoisi = (Carreau) actionEvent.getSource();
        nombreDeCoupsJoués.set(nombreDeCoupsJoués.get() + 1);
        carreauChoisi.permuter();
        permuterVoisin(carreauChoisi);
    };

    public Plateau() {
        this(3);
    }

    public Plateau(int taille) {
        this.taille = taille;
        this.nombreDeCoupsJoués = new SimpleIntegerProperty(0);
        this.nombreCarreauEteint = new SimpleIntegerProperty(0);
        this.aGagné = new SimpleBooleanProperty(false);

        setHgap(3);
        setVgap(3);

        remplir();
        creerBindings();
        nouvellePartie();
    }

    private void remplir() {
        carreaux = new Carreau[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                carreaux[i][j] = new Carreau(i, j);
                carreaux[i][j].setOnAction(carreauListener);
                add(carreaux[i][j], i, j);
            }
        }
    }

    private void toutAllumer() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                carreaux[i][j].allumer();
            }
        }
    }

    public void nouvellePartie() {
        nombreDeCoupsJoués.set(0);
        toutAllumer();
    }

    private void creerBindings() {
        aGagné.bind(nombreCarreauEteint.isEqualTo(taille * taille));
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                carreaux[i][j].estAlluméProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue)
                        nombreCarreauEteint.set(nombreCarreauEteint.get() - 1);
                    else
                        nombreCarreauEteint.set(nombreCarreauEteint.get() + 1);
                });
            }
        }
    }

    private void permuterVoisin(Carreau carreauChoisi) {
        for (Point2D direction : directions) {
            int indiceLigne = (int) (carreauChoisi.getPosition().getY() + direction.getY());
            int indiceColonne = (int) (carreauChoisi.getPosition().getX() + direction.getX());
            if (estIndicesValides(indiceLigne, indiceColonne))
                carreaux[indiceColonne][indiceLigne].permuter();
        }
    }

    private boolean estIndicesValides(int indiceLigne, int indiceColonne) {
        return estIndiceValide(indiceColonne) && estIndiceValide(indiceLigne);
    }

    private boolean estIndiceValide(int indice) {
        return indice < taille && indice >= 0;
    }

    public int getNombreDeCoupsJoués() {
        return nombreDeCoupsJoués.get();
    }

    public IntegerProperty nombreDeCoupsJouésProperty() {
        return nombreDeCoupsJoués;
    }

    public boolean aGagné() {
        return aGagné.get();
    }

    public BooleanProperty aGagnéProperty() {
        return aGagné;
    }

    public IntegerProperty nombreCarreauEteintProperty() {
        return nombreCarreauEteint;
    }
}

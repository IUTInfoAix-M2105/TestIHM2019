package fr.univ_amu.iut.lightsout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;


public class Case extends Button {

    private static final int TAILLE_CASE = 50;
    private Position position;
    private BooleanProperty estAllumé;

    public Case(int x, int y) {
        position = new Position(x, y);

        estAllumé = new SimpleBooleanProperty();
        allumer();

        final int tailleCase = TAILLE_CASE;
        setMinSize(tailleCase, tailleCase);
        setPrefSize(tailleCase, tailleCase);
    }

    public Position getPosition() {
        return position;
    }

    public boolean estAllumé() {
        return estAllumé.get();
    }

    public BooleanProperty estAlluméProperty() {
        return estAllumé;
    }

    public void allumer() {
        estAllumé.set(true);
        setStyle("   -fx-background-color:\n" +
                "        linear-gradient(#f0ff35, #a9ff00),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);");
    }

    public void eteindre() {
        estAllumé.set(false);
        setStyle("-fx-background-color: black; -fx-border-color: grey");

    }

    public void permuter() {
        if (estAllumé.get())
            eteindre();
        else
            allumer();
    }
}

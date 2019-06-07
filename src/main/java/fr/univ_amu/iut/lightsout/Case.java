package fr.univ_amu.iut.lightsout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;


public class Case extends Button {

    private static final int TAILLE_CASE = 75;
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
        setStyle("    -fx-background-color:\n" +
                "            linear-gradient(#f0ff35,#a9ff00),\n" +
                "            radial-gradient(center 50% -40%, radius 220%, #b8ee36 45%, #80c800 50%);\n" +
                "    -fx-background-radius: 6,5;\n" +
                "    -fx-background-insets: 0,1;\n" +
                "\n" +
                "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1);\n" +
                "   -fx-text-fill: #395306;");
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

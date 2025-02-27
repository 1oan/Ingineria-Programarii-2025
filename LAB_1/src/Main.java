import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.List;

class Participant {
    private String nume;
    private int varsta;

    public Participant(String nume, int varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return nume + " (" + varsta + " ani)";
    }
}

abstract class Tur {
    protected String ghid;
    protected List<Participant> participanti;
    protected double pretPeKm;

    public Tur(String ghid, double pretPeKm) {
        this.ghid = ghid;
        this.pretPeKm = pretPeKm;
        this.participanti = new ArrayList<>();
    }

    public void adaugaParticipant(Participant p) {
        participanti.add(p);
    }

    public abstract double calculeazaPretTotal(double distanta);

    public void afiseazaDetalii(double distanta) {
        System.out.println("Tip tur: " + this.getClass().getSimpleName());
        System.out.println("Ghid: " + ghid);
        System.out.println("Participanti: " + participanti);
        System.out.println("Pret total: " + calculeazaPretTotal(distanta) + " RON");
    }

    public double calculeazaDistantaTotala(List<Double> distante) {
        double total = 0;
        for (double d : distante)
            total += d;
        return total;
    }
}

class TurAventura extends Tur {
    private double taxaSuplimentara;

    public TurAventura(String ghid, double pretPeKm, double taxaSuplimentara) {
        super(ghid, pretPeKm);
        this.taxaSuplimentara = taxaSuplimentara;
    }

    @Override
    public double calculeazaPretTotal(double distanta) {
        return (distanta * pretPeKm) + taxaSuplimentara;
    }
}

class TurCultural extends Tur {
    private double discount;

    public TurCultural(String ghid, double pretPekm, double discount) {
        super(ghid, pretPekm);
        this.discount = discount;
    }

    @Override
    public double calculeazaPretTotal(double distanta) {
        double pret = distanta * pretPeKm;
        pret = pret - (pret * discount / 100);
        return pret;
    }
}

public class Main {
    public static void main(String[] args) {
        TurAventura TA1 = new TurAventura("Marin", 7, 50);
        TurAventura TA2 = new TurAventura("Alin", 10, 70);
        TurCultural TC1 = new TurCultural("Eduard", 6, 40);
        TurCultural TC2 = new TurCultural("Ilinca", 4, 25);

        Participant p1 = new Participant("Ianis Hagi", 26);
        Participant p2 = new Participant("Cristiano Ronaldo", 40);
        Participant p3 = new Participant("Michael Jordan", 62);
        Participant p4 = new Participant("Luka Doncic", 25);

        TA1.adaugaParticipant(p1);
        TA1.adaugaParticipant(p2);
        TA2.adaugaParticipant(p3);
        TA2.adaugaParticipant(p4);

        TC1.adaugaParticipant(p1);
        TC1.adaugaParticipant(p3);
        TC2.adaugaParticipant(p2);
        TC2.adaugaParticipant(p4);

        List<Double> itinerariuAventura1 = List.of(30.0, 12.0, 45.0, 60.0);
        List<Double> itinerariuCultura1 = List.of(10.0, 23.0, 15.0);

        double distantaAventura1 = TA1.calculeazaDistantaTotala(itinerariuAventura1);
        double distantaCultura1 = TC1.calculeazaDistantaTotala(itinerariuCultura1);

        System.out.println("Detalii Tur Aventura 1:");
        TA1.afiseazaDetalii(distantaAventura1);

        System.out.println("---------------------");

        System.out.println("Detalii Tur Cultural 1:");
        TC1.afiseazaDetalii(distantaCultura1);
    }
}
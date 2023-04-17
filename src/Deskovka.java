public class Deskovka {
    private String nazev;
    private boolean zakoupeno;
    private int oblibenost;

    public Deskovka(String nazev, boolean zakoupeno, int oblibenost) {
        this.nazev = nazev;
        this.zakoupeno = zakoupeno;
        this.oblibenost = oblibenost;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public boolean isZakoupeno() {
        return zakoupeno;
    }

    public void setZakoupeno(boolean zakoupeno) {
        this.zakoupeno = zakoupeno;
    }

    public int getOblibenost() {
        return oblibenost;
    }

    public void setOblibenost(int oblibenost) {
        this.oblibenost = oblibenost;
    }
}

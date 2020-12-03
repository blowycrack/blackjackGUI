public class Kartya {
    private Szin szin;
    private Ertek ertek;

    public Kartya(Szin szin, Ertek ertek) {
        this.szin = szin;
        this.ertek = ertek;
    }
    public String toString() {
        return "[ " + this.szin.toString() + " : " + this.ertek.toString() + " ]";
    }
    public Ertek getErtek()
    {
        return this.ertek;
    }
}

import java.util.ArrayList;
import java.util.Random;

public class Asztal {
    private ArrayList<Kartya> kartyak;

    public Asztal() {
        this.kartyak = new ArrayList<>();
    }
    public void ujAsztal() {
        for(Szin kartya_szin : Szin.values()) {
            for(Ertek kartya_ertek : Ertek.values()) {

                this.kartyak.add(new Kartya(kartya_szin, kartya_ertek));

            }
        }
    }
    public void kartyaKever() {
        ArrayList<Kartya> tmpAsztal = new ArrayList<Kartya>();

        Random rnd = new Random();
        int rnd_index = 0;
        int originalsize = this.kartyak.size();
        for(int i = 0; i < originalsize; i++)
        {
            rnd_index = rnd.nextInt((this.kartyak.size() -1 - 0) + 1) + 0;
            tmpAsztal.add(this.kartyak.get(rnd_index));
            this.kartyak.remove(rnd_index);

        }
        this.kartyak= tmpAsztal;
    }
    public void kartyaTorol(int i) {
        this.kartyak.remove(i);
    }

    public Kartya getKartya(int i){
        return this.kartyak.get(i);
    }

    public void addKartya(Kartya addkartya) {
        this.kartyak.add(addkartya);
    }
    // draw
    public void draw(Asztal honnan)
    {
        this.kartyak.add(honnan.getKartya(0));
        honnan.kartyaTorol(0);
    }
    public int asztalSize() {
        return this.kartyak.size();
    }

    public void kartyaLerak(Asztal at) {
        int asztalMeret = this.kartyak.size();

        for(int i=0; i < asztalMeret; i++) {
            at.addKartya(this.getKartya(i));
        }
        for(int i=0; i< asztalMeret; i++) {
            this.kartyaTorol(0);
        }
    }

    public String toString() {
        String kartyaLista = "";
        int i=0;
        for(Kartya k : this.kartyak) {
            kartyaLista += "\n" + " - " + k.toString();
        }
        return kartyaLista;
    }
    //
    public int kartyakErteke() {
        int ertek = 0;
        int aces = 0;
        for(Kartya k : this.kartyak) {
            switch (k.getErtek())
            {
                case KETTO: ertek+=2; break;
                case HAROM: ertek+=3; break;
                case NEGY: ertek+=4; break;
                case OT: ertek+=5; break;
                case HAT: ertek+=6; break;
                case HET:ertek+=7; break;
                case NYOLC: ertek+=8; break;
                case KILENC: ertek+=9; break;
                case TIZ: ertek+=10; break;
                case BUBI: ertek+=10; break;
                case DAMA: ertek+=10; break;
                case KIRALY: ertek+=10; break;
                case ASZ: aces++; break;
            }
        }
        for(int i=0; i < aces; i++) {
            if(ertek > 10) {
                ertek += 1;
            }
            else
            {
                ertek += 11;
            }
        }
        return ertek;

    }
}

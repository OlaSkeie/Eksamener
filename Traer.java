import java.util.List;

public class Traer {
   
    //Trær generelt
    public int depth(Node v) {
        if (v == null) {
            return -1; //Hvis depth(v.parent) kjører og v.parent er null
            //vil den returnere -1 og da begynner stacken å gå nedover
            //hvis det f. eks er 4 noder ned og du starter på løvnoden, vil den gå opp helt til rotnoden og kalle depth og den vil få dybde 0
            // så vil den gå nedover i stacken og gi +1 til hver av de fra foreldrenoden
        }
        return 1 + depth(v.parent);
    }


    public int height(Node v){
        int heightTemp = -1; 
        if(v == null){ //høyden til et tomt tre er -1
            return heightTemp;
        }
        for(Node child : v.children){ //går gjennom alle barna til v for å finne den med høyest høyde
            heightTemp = max(heightTemp, height(child));
        }
        return 1 + heightTemp; //Returnerer den høyeste høyden av barna + 1 fordi forelderen er en høyere oppe
    }


    public void preOrder(Node v){
        //Denne metoden utfører operasjonen på seg selv først og så barna
        //kan f. eks brukes til å kopiere et tre
        if(v == null){
            return;
        }
        //Her kan man gjøre en operasjon på v
        for(Node child : v.children){
            preOrder(child); //går gjennom alle barna til v
        }
    }


    public void postOrder(Node v){
        if(v == null){
            return;
        }
        for(Node child : v.children){
            postOrder(child);
        }
        //Gjør operasjon på v
    }

    //Binære trær
    
    //O(log n)
    public Node insert(Node v, int element){
        if(v == null){
            v = new Node(1); //Hvis den kommer til et sted hvor det ikke er en node fra før
            //så vil den lage en ny og sette den på den plassen
            v.element = element;
        }
        else if(element < v.element){ //Hvis det som sendes inn er mindre enn det på den nåværende node plassen
            //vil man traversere venstresiden
            v.venstre = insert(v.venstre, element);
        }
        else if(element > v.element){ //større så vil man traversere høyresiden
            v.hoyre = insert(v.hoyre, element);
        }
        return v;
    }

    //O(log n)
    public Node remove(Node v, int x) {
        if (v == null) {
            return null; //Hvis den ikke finner noden
        }
        if (x < v.element) { //Lete på venstresiden
            v.venstre = remove(v.venstre, x);
            return v;
        }
        if (x > v.element) { //lete på høyresiden
            v.hoyre = remove(v.hoyre, x);
            return v;
        }
        if (v.venstre == null) { //hvis den har funnet den og venstresiden er null så 
            //vil den returnere v.hoyre for å bytte ut den fjernede noden
            return v.hoyre;
        }
        if (v.hoyre == null) { //motsatt
            return v.venstre;
        }

        Node u = finnMinst(v.hoyre); //Hvis den har to barn som ikke er null finner den den minste på en av sidene
        //i dette tilfellet høyre, og setter elementet inn i v og fjerner noden u
        v.element = u.element;
        v.hoyre = remove(v.hoyre, u.element);
        return v;
    }

    //O(log n)
    public boolean contains(Node v, int x) {
        //veldig rett frem metode, hvis den finner elementet så returnerer den true
        //hvis ikke false, og den leter på enten høyre eller venstresiden av hver node
        if (v == null) {
            return false;
        }
        if (v.element == x) {
            return true;
        }
        if (x < v.element) {
            return contains(v.venstre, x);
        }
        if (x > v.element) {
            return contains(v.hoyre, x);
        }
        return false;

    }

    //AVL-Trær
    //Insetting og sletting i et AVL tre er helt like binære
    //med eneste forskjell at man må sette høyden og balansere det i slutten av metoden

    //O(1)
    public int balanceFactor(Node v) {
        if (v == null) {
            return 0;
        }
        return height(v.venstre) - height(v.hoyre); //returnerer forskjellen mellom venstre og høyre side
    }

    //O(1)
    public Node balance(Node v) {
        //metode for å balansere treet
        if (balanceFactor(v) < -1) { //sjekker forskjellen på venstre og høyreside
            if (balanceFactor(v.hoyre) > 0) {//igjen
                v.hoyre = hoyreRoter(v.hoyre); //hvis den er venstretung
            }
            return venstreRoter(v); //høyretung
        }
        if (balanceFactor(v) > 1) {
            if (balanceFactor(v.venstre) < 0) {
                v.venstre = venstreRoter(v.venstre);
            }
            return hoyreRoter(v);
        }
        return v;

    }

    //O(1)
    public Node venstreRoter(Node z) {
        Node y = z.hoyre; //lagrer variabler slik at de ikke blir borte hvis de fjernes fra treet
        Node T1 = y.venstre;
        y.venstre = z; //roterer z til sin høyre side og til dens venstreside
        z.hoyre = T1; //setter z sin hoyre (z er nå på venstresiden av y)
        //til y sin gamle venstre
        setHeight(z); //justerer hoyden på nodene
        setHeight(y);
        return y;
    }

    //O(1)
    public Node hoyreRoter(Node z) {
        Node y = z.venstre;
        Node T2 = y.hoyre;
        y.hoyre = z;
        z.venstre = T2;
        setHeight(z);
        setHeight(y);
        return y;
    }

    //O(1)
    public void setHeight(Node v) {
        if (v == null) {
            return;
        }
        v.hoyde = 1 + max(height(v.venstre), height(v.hoyre));
    }


    public int max(int en, int to){
        if(en > to){
            return en;
        }
        return to;
    }


    public Node finnMinst(Node v) {
        if (v == null) {
            return null;
        }
        if (v.venstre == null) {
            return v;
        }
        return finnMinst(v.venstre);
    }

    

    class Node {
        public Node(int e){
            element = e;
        }
        int element;
        private Node parent;
        private List<Node> children;
        int hoyde;
         Node venstre;
         Node hoyre;
    }
}
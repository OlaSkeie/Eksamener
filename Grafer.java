import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.PriorityQueue;

public class Grafer {

    // Korteste veier

    //Prims algoritme er for å finne minimale spenntrær
    //Den er i O(E log v), samme som dijkstra


    // O(E log(V));
    public HashMap<Node, Double> dijkstra(Node s) { // Fungerer ikke hvis det er negative vekter.
        HashMap<Node, Double> dist = new HashMap<>();

        PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparing(dist::get)); // Trenger ikke bruke comparator.
                                                                                      // Dette er en heap
        // som brukes for å sjekke
        dist.put(s, 0.0); // Putter startnoden i Map dist, med distanse 0.0 fra seg selv
        q.add(s);

        while (!q.isEmpty()) {
            Node tmp = q.poll(); // Henter ut noden med høyest prioritet

            for (Node nabo : tmp.adjacentNodes.keySet()) { // går gjennom alle naboene til node tmp
                double tempDistanse = tmp.distance + tmp.kanter.get(nabo).vekt; // tmp sin distanse fra startnoden +
                                                                                // kanten sin vekt fra tmp til nabo
                if (tempDistanse < nabo.distance) { // Hvis den er mindre enn nabo sin tidligere distanse oppdateres den

                    nabo.distance = tempDistanse;
                    q.add(nabo); // alle naboene til tmp vil addes her, og den med høyest prioritet vil havne
                                 // først (her den med lavest distanse tror jeg)
                }
            }
        }
        return dist; // Returnerer alle distansene fra startnode s
    }

    // O(V^3)
    public HashMap<Node, Double> bellmanFord(Graph graf, Node startnode, int antNoder) { // Går ut på å finne korteste vei med
                                                                             // negative vektede kanter
        HashMap<Node, Double> dist = new HashMap<>(); 
        
        for(Node n : graf.noder){
            dist.put(n, Double.MAX_VALUE);
        }
        dist.put(startnode, 0.0);
        for (int i = 0; i < antNoder - 1; i++) {//Sjekker alle kantene n - 1 gang
            for(Kant kant : graf.kanter){ 
                if(dist.get(kant.start) + kant.vekt < dist.get(kant.slutt)){ //'Relax kanten'
                    dist.put(kant.slutt, dist.get(kant.start) + kant.vekt); 
                }
            }
        }
        for(int i = 0; i < antNoder - 1; i++){ //Denne er for å sjekke om det er noen negative sykler 
            for(Kant kant : graf.kanter){
                if(dist.get(kant.start) + kant.vekt < dist.get(kant.slutt)){ //Man sjekker om noen får mindre enn det de hadde som egentlig skal være det minste
                    dist.put(kant.slutt, -Double.MAX_VALUE);
                }
            }
        }
        return dist;
    }


    //Hvis vi vet at grafen er en DAG (Rettet og asyklisk graf (Directed Asyclic Graph))
    //så kan vi finne korteste vei i O(V + E)

    public Map<Node, Double> DAGShortest(Graph graf, Node startNode){
        HashMap<Node, Double> dist = new HashMap<>();
        dist.put(startNode, 0.0);

        for(Node n : TopSort(graf)){ //Går gjennom en topologisk sortert array
            for(Kant kant : graf.kanter){ //Alle kantene i grafen?
                double sjekkVekt = dist.get(kant.start) + graf.weight(kant.start, kant.slutt); //ny vekt mellom start og sluttnoden
                if(sjekkVekt < dist.get(kant.start)){ //Sjekker om den er mindre enn den som allerede er i mapet
                    dist.put(kant.slutt, sjekkVekt); 
                }
            }
        }
        return dist;
    }


    public Node[] TopSort(Graph graf){
        Stack<Node> stack = new Stack<>(); //last in first out (LIFO)
        Node[] output = new Node[graf.noder.size()]; //listen som skal returneres som en topologisk sortering
        int tellerOutput = 0; //Hvilken index man er på
        for(Node n : graf.noder){ //leter først etter en node med inngrad 0
            if(n.indegree == 0){
                stack.push(n);//Hvis du finner en legger du den på stacken
            }
        
        while(!stack.isEmpty()){
            Node u = stack.pop(); //tar noden av stacken
            output[tellerOutput] = u; //legger den til i output listen
            tellerOutput++; 
            

            for(Node n1 : u.adjacentNodes.keySet()){ //leter gjennom alle nabonodene og sjekker om noen har inngrad 0
                if(n1.indegree - 1 == 0){
                    stack.push(n1);
                }
            }
        }
    }
    if(output.length < graf.noder.size()){ //Her vil du enten ha gått gjennom alle elementene eller gått ut tidlig pga en eller flere noder ikke kan få inngrad 0
        System.out.println("IKKE MULIG AA TOPOLOGISK SORTERE!");
    }
    return output;


    }

    public void minus(Map<Node, Integer> adjacentNodes){
        for(Node n : adjacentNodes.keySet()){
            n.indegree--;
        }
    }

    

    //SØK
    //O(V + E)
    public void DFSFull(Set<Node> graph) { // DFS bruker en stack data struktur
        // starter i denne metoden
        boolean[] besokt = new boolean[graph.size()];
        for (Node n : graph) { // går gjennom alle nodene her og hvis de ikke er besøkt sjekker man
                               // komponentene til de nodene ved DFS
            if (!besokt[n.nummer]) {
                DFS(n, besokt);
            }
        }
    }

    public void DFS(Node startNode, boolean[] besokt) {
        besokt[startNode.nummer] = true;
        for (Node p : startNode.adjacentNodes.keySet()) { // enkel metode som sjekker alle adjacentNodes til startnoden,
                                                         // kan returnere et tall f eks hvor mange som er i lista
            if (!besokt[p.nummer]) {
                DFS(p, besokt);
            }
        }
    }

    //O(V + E)
    public void BFSVisit(Set<Node> graf, Node startNode, boolean[] besokt) { //man vil sjekke alle naboene til startNode, og så naboene
        //til de nodene osv, eneste måten den ikke vil sjekke alle med et BFSVisit-kall er hvis det er flere komponenter
        //Den brukes ofte til å finne korteste veier
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(startNode);
        besokt[startNode.nummer] = true;
        while (!queue.isEmpty()) { // sjekker videre så lenge det er elementer i lista
            Node nodeTattAv = queue.poll();
            for (Node n : nodeTattAv.adjacentNodes.keySet()) { //sjekker naboene til nodeTattAv
                if (!besokt[n.nummer]) {
                    queue.offer(n); //Legger naboene inn i queuen
                    besokt[n.nummer] = true;
                }
            }
        }
    }

    public void BFSFull(Set<Node> graf) { // Bredde først søk bruker en queue data struktur
        boolean[] besokt = new boolean[graf.size()];
        for (Node n : graf) {
            if (!besokt[n.nummer]) {
                BFSVisit(graf, n, besokt);
            }
        }
    }



    //Sammenhengende grafer
    //En sammenhengende graf betyr at det finnes en sti mellom alle noder, at den ikke kan deles inn i komponenter
    //En to-sammenhengende graf er en graf som forblir sammenhengende selvom vi fjerner en vilkårlig node fra grafen
    //En graf er k-sammenhengende hvis den forblir sammenhengende selv om man fjerner færre enn k noder

    //Algoritme for å finne alle separasjonsnoder. En separasjonsnode er en node som holder grafen sammenhengende
    HashMap<Node, Integer> depth = new HashMap<>();
    HashMap<Node, Integer> low = new HashMap<>();
    Set<Node> seps = new HashSet<>();
    public Set<Node> separationVertices(Graph graf){
        Node s = graf.noder.get(0); //tilfeldig
        low.put(s, 0);
        int children = 0;

        for(Kant kant : graf.kanter){
            if(depth.get(kant.slutt) == null){
                sepRec(graf, kant.start, kant.slutt, 1);
                children++;
            }
        }
        if(children > 1){
            seps.add(s);
        }
        return seps;
    }
    //Hjelpemetode for separationVertices
    public void sepRec(Graph graf, Node start, Node slutt, int barn){
        depth.put(slutt, barn);
        low.put(slutt, barn);

        for(Kant kant : graf.kanter){
            if(kant.slutt.equals(start)){
                continue;
            }
            if(depth.get(kant.slutt) != null){
                low.put(kant.start, min(low.get(kant.start), depth.get(kant.slutt)));
            }

            sepRec(graf, start, slutt, barn + 1);
            low.put(kant.start, min(low.get(kant.start), low.get(kant.slutt)));
            if(barn <= low.get(kant.slutt)){
                seps.add(kant.start);
            }
        }
    }
    //Hjelpemetode
    public int min(int en, int to){
        if(en > to){
            return en;
        }
        return to;
    }


    //Metode for å reversere en graf
    //En reversert graf har alltid de samme sterkt sammenhengende komponentene
    public Set<Kant> reverseGraph(Graph graf){
        HashSet<Kant> reversert = new HashSet<>();
        for(Kant kant : graf.kanter){ //går gjennom kanter i orginale grafen
            Kant leggTilReversert = new Kant();
            leggTilReversert.start = kant.slutt; //bytter om start til slutt og motsatt
            leggTilReversert.slutt = kant.start;
        }
        return reversert;
    }

    //En komponent er sterkt sammenhengende hvis det finnes en sti mellom alle par av noder
    //


    class Graph{
        ArrayList<Kant> kanter = new ArrayList<>();
        Node startNode;
        ArrayList<Node> noder = new ArrayList<>();
        public Graph(Node s){
            startNode = s;
        }
        public double weight(Node start, Node slutt){
            return start.kanter.get(slutt).vekt;
        }
    }

    class Node implements Comparable<Node>{
        int nummer;
        String name;
        double distance = Integer.MAX_VALUE;
        int indegree;
        Map<Node, Integer> adjacentNodes = new HashMap<>();
        ArrayList<Node> shortestPath = new ArrayList<>();
        HashMap<Node, Kant> kanter = new HashMap<>();

        public String toString(){
            return "" + distance;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare((int)distance, (int)o.distance);
        }
    }
   

    class Kant {
        double vekt;
        Node start;
        Node slutt;
    }
   



}

import java.util.HashSet;
@SuppressWarnings("unchecked")
public class Hashing {

    //Separate chaining
    //Separate chaining går ut på at hver plass i arrayen er en 'Bøtte', her har jeg valgt å gjøre de til HashSet
    //Hvis de får samme hash, vil den gå i bøtten
    HashSet<Integer>[] arr = new HashSet[1000];
    public int remove(int i) {
        int index = hash(i);
        HashSet<Integer> b = arr[index];
        if (b == null) {
            return -1;
        }
       if(arr[index].remove(i)){
            return i;
       }
        return -1;

    }

    public int insert(int i) {
        int index = hash(i);
        HashSet<Integer> b = arr[index];
        if (b == null) {
            arr[index] = new HashSet<>();
            arr[index].add(i);
            return i;
        }
        arr[index].add(i);
        return i;
    }

    public boolean contains(int i) {
        int index = hash(i);
        HashSet<Integer> b = arr[index];
        if (b == null) {
            return false;
        }
        return b.contains(i);
    }

    public int hash(int i) {
        return i % arr.length;
    }

    //Linear probing
    //Linear probing går ut på å hashe verdien til en plass, hvis plassen er tatt, så sjekker du alle andre plasser ved å ta i % størrelsen på lista
    //Hvis alle plassene er tatt forstørrer du lengden til lista med f eks 2x

    Node[] linearProbing = new Node[10000];
    public void insertLinearProbing(int settInn, Hashing.Node[] linearProbing){
        Node nyNode = new Hashing.Node(settInn);
        int index = hash(settInn);
        if(linearProbing[index] == null){
            linearProbing[index] = nyNode;
            return;
        }
        if(!erDetPlass(linearProbing)){ //Sjekker om det er plass i arrayet
            return;
        }
        while(linearProbing[index] == null){
            index = index % linearProbing.length; //Modder index helt til man finner en ledig plass
            
        }

        linearProbing[index] = nyNode; //Her har vi funnet plass og setter den inn
    }

    public Node oppslagLinearProbing(Node finnInt){
        Node nyNode = new Node(finnInt.tall);
        int index = hash(finnInt.tall);

        if(linearProbing[index] == finnInt){
            return linearProbing[index];
        }
        while(linearProbing[index].equals(finnInt) || //Enten er det lik det vi vil finne 
        linearProbing[index] == null){ //eller så er den null, hvis den er null betyr det at den ikke er i 
            //arrayet siden index + 1 % n vil også ha blitt gjort i insetting og sletting,
            //i sletting blir det hullet tettet
            index = (index + 1) % linearProbing.length;
        }
        return linearProbing[index];
    }

    public Node removeLinearProbing(Node slettNode){
        int index = slettNode.tall % linearProbing.length;
        if(linearProbing[index] == null){
            return null;
        }
        while(linearProbing[index] != slettNode || linearProbing[index] != null){
            index = (index + 1) % linearProbing.length;
        }
        Node tmp = linearProbing[index];
        if(linearProbing[index] == slettNode){
            
            linearProbing[index] = null;
        }
        tettHull(index);
        return tmp;
    }

   

    public void tettHull(int i){
        int nyI = i = (i + 1) % linearProbing.length;
        if(linearProbing[nyI] == null){
            return;
        }
        linearProbing[i] = linearProbing[nyI];
        tettHull(nyI); 
    }
    
    public boolean erDetPlass(Node[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null){
                return true;
            }
        }
        
        return false;
    }
    public Node[] rehash(Node[] arr){ //Rehasher alle elementene
        Node[] arrX2 = new Node[arr.length * 2];
        for(int i = 0; i < arr.length; i++){
            insertLinearProbing(arr[i].tall, arrX2);
        }
        return arrX2;
        
    }
    class Node{
        int tall;
        public Node(int t){
            tall = t;
        }
        
    }
    
}

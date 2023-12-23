import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class FlereSorteringer {

    public static void main(String[] args){
        FlereSorteringer f = new FlereSorteringer();
        int[] a = {1,3,4,2,7,6,10,8};
        a = f.heapSort(a);
        for(int i : a){
            //System.out.println(i);
        }
    }

    // HeapSort
    // Går ut på å lage om arrayet til en max heap, også sortere
    // Heapsort er i O(n log n), samme som mergesort
    // Den har flere konstante faktorer enn mergesort, men bruker mindre minne
    public int[] heapSort(int[] A) {
        int lengde = A.length;
        A = BuildMaxHeap(A, lengde);
       
        for (int i = lengde - 1; i >= 0; i--) {
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            bubbleDown(A, 0, i);
        }
       
        return A;
    }

    // Starter i midten av arrayet og går nedover, da vil vi få med alle sortert
    // garantert
    public int[] BuildMaxHeap(int[] A, int antElementer) { // Denne starter å bygge max heapen
        for (int i = antElementer / 2; i >= 0; i--) {
           
            // den er faktisk O(n)
            bubbleDown(A, antElementer, i);
        }
        return A;
    }

    public void bubbleDown(int[] A, int antElementer, int rot) {
        int largestIndex = rot; // Dette er ikke nødvendigvis det største elementet, men 'roten' eller det
                                // elementet man kaller roten
        int left = 2 * rot + 1;
        int right = 2 * rot + 2;
        

        // Hvis venstrebarnet er større enn foreldrenoden
        if (left < antElementer) {
            if(A[largestIndex] < A[left]){
                largestIndex = left;
            }
            
            
        }
        // Hvis høyre barn er større enn foreldrenoden (Og venstre barnet til
        // foreldrenoden) vil man bytte
        if (right < antElementer) {
            if(A[largestIndex] < A[right]){
                largestIndex = right;
            }
            
        }
        // Hvis en eller begge if setningene har vært sanne så bytter man om på de og
        // kjører metoden på nytt fra
        if (rot != largestIndex) {
            int tmp = A[rot];
            A[rot] = A[largestIndex];
            A[largestIndex] = tmp;
            bubbleDown(A, antElementer, largestIndex);

        }
    }

    // Quicksort
    // Velge et element 'pivot' og samle alt som er mindre til venstre og større til
    // høyre
    // Det vanskelige er å velge en bra pivot. Det beste ville vært medianen til
    // listen
    // Noen vanlige strategier er å enten velge tilfeldig eller en median av start,
    // midt og slutt

    //Quicksort
    //Bruke quicksort når du har lite 'space'. Det er O(n log n)
    public int[] quickSort(int[] A, int low, int high){//første iterasjon vil low være 0 og high lengden av listen
        if(low >= high){ 
            return A;
        }
        int p = partition(A, low, high); 
        quickSort(A, low, p - 1); //man vil ta alt på venstre siden av pivoten
        quickSort(A, p + 1, high); //alt på høyre
        return A;
    }

    public int partition(int[] A, int low, int high) { // low og high bestemmer hvor vi har 'lov til' å se i arrayen
        int p = choosePivot(A);
        int temp = A[p]; // Her gjemmer vi bort pivot elementet
        A[p] = A[high]; 
        A[high] = temp;
        int pivot = A[high]; // Finner pivot elementet ved å slå opp A[high] siden vi akkurat byttet
        int left = low;
        int right = high;
        while (left <= right) {

            while (left <= right && A[left] <= pivot) {
                left++;
            }
            while(left <= right && A[right] >= pivot){
                right++;
            }
            if(left < right){
                int tempVenstre = A[left];
                A[left] = A[right];
                A[right] = tempVenstre;
            }
        }
        int tempVenstre = A[left];
        A[left] = A[high]; //Setter pivot elementet i midten
        A[high] = tempVenstre; 
        return left; //Pivot elementet

    }

    public int choosePivot(int[] A) {
        return 1;
    }


    //Bucket sort går ut på å lage N bøtter og plassere elementene i bøttene basert på noe informasjon
    //Denne informasjonen kan f eks være en type kort som hjerter. For å bruke denne må vi vite litt mer om elementene.
    //Vi kaller denne verdien vi sorterer etter nøkler.
    //Vi kan få kort sortert på både sort og verdi ved å først sortere på verdi og så sort
    //Dette kan oppnås ved å gjøre to bucket sort

    //Bucket sort er i O(N + n) hvor N er antall bøtter og n er antall elementer. Det kan ofte forenkles til O(n)
    public int[] bucketSort(int[] liste, int maksPrio){
        ArrayList<Integer>[] boetter = new ArrayList[maksPrio]; //Kan være hvordan som helst liste
        for(int i = 0; i < maksPrio - 1; i++){ //Denne for løkken legger inn alle elementene i bøttene de hører til i
            int k = liste[i];
            boetter[k].add(liste[i]);
        }
        int j = 0;
        for(int k = 0; k < maksPrio - 1; k++){ //Her legger du alle elementene tilbake igjen i orginale arrayen
            //sortert på den prioriteten du har.
            for(int node : boetter[k]){
                liste[j] = node;
                j++;
            }
        }
        return liste;
    }

    //Radix sort, veldig likt bucket sort
    //Radix sort en en suksessiv anvendelse av bucket sort
    //Radix sort er en ordning som kan sorteres leksiografisk, altså en generalisering av alfabetisk rekkefølge
    //dette betyr at vi kan ordne ting som allerede er ordnet 'mer'. 
    //Et eksempel på dette er med kort, de kan først sorteres på sort, og så enda mer på verdi
    
    //Tidskompleksiteten er O(maksSiffer(N + n)), dersom vi bruker titallsystemet
    //kan vi sette N = 10, og da får vi O(maksSiffer(10 + n)) = O(maksSiffer * n) siden da blir N konstant
    //hvis vi i tillegg vet at tallene ikke blir større enn 10^10 som det nesten aldri er vil vi få
    //O(10 * n) = O(n)
   
    public int[] radixSort(int[] A){
        int maksSiffer = findMax(A);
        for(int i = maksSiffer - 1; i >= 0; i--){
            A = bucketSort(A, maksSiffer);
        }
        return A;
    }

    public int findMax(int[] A){
        int maks = A[0];
        for(int n : A){
            if(n > maks){
                maks = n;
            }
        }
        return Integer.toString(maks).length();
    }
    class Node{
        int prioritet;
        int tall;
    }

}

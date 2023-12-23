class Sorteringer {
    public static void main(String[] args) {
        Sorteringer b = new Sorteringer();
        int[] arr = { 2, 3, 1, 5, 4 };
        b.bubbleSort(arr);
        
    }

    // Bubble sort

    //O(n^2)
    public void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) { // Går gjennom hvert element til n - 1, denne for loopen brukes ikke
                                                   // til så mye
            for (int j = 0; j < arr.length - i - 1; j++) { // går gjennom hvert element til n - i - 1
                if (arr[j] > arr[j + 1]) { // sjekker om elementet på den j plass er større enn det på j + 1
                    int temp = arr[j];
                    arr[j] = arr[j + 1]; // Hvis det er så byttes de
                    arr[j + 1] = temp;
                }
                // Etter første iterasjon vil det største elementet være bakerst i lista, det er
                // derfor ikke noe poeng å sjekke den indexen, derfor n - i - 1
                // Det må derfor gjøre n^2 gjennomganger i verste tilfelle
            }
        }
    }

    // Selection sort

    //O(n^2)
    public void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) { // går gjennom hele listen
            int k = i; // variabel k settes til i, som kan endres, ulikt i
            for (int j = i + 1; j < arr.length; j++) { // setter j = i + 1 slik at tallene bak i aldri sjekkes og i ikke
                                                       // sjekker seg selv
                if (arr[j] < arr[k]) { // hvis det finner noe mindre enn k så vil j bli minsteindex
                    k = j;
                }
            }
            if (i != k) { // Hvis det er et tall mindre enn arr[i], så settes det på den i´te plassen
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
        // Selection sort går ut på å finne det minste elementet av tallene fra
        // i hver eneste gang og sette det der det skal, denne er O(n^2) fordi begge
        // loopene gjør n iterasjoner
    }

    // Mergesort 
    //O(n))
    int[] merge(int[] arr1, int[] arr2, int[] arr) {
        // to variabler for å holde styr på hvor mange elementer som er igjen i de to
        // listene
        int listeEnIndex = 0;
        int listeToIndex = 0;
        // mens begge har elementer igjen så kjører denne while løkken
        while (listeEnIndex < arr1.length && listeToIndex < arr2.length) {
            // hvis listeEnIndex listeEnIndex den første arrayen er mindre eller er lik listeToIndex listeEnIndex den andre arrayen, så
            // vil arr1[listeEnIndex] legges inn og listeEnIndex plusses på
            // Hvis ikke vil arr2[listeToIndex] legges inn listeEnIndex arr og listeToIndex plusses på
            if (arr1[listeEnIndex] <= arr2[listeToIndex]) { 
                arr[listeEnIndex + listeToIndex] = arr1[listeEnIndex];
                listeEnIndex++;
    
            } else {
                arr[listeEnIndex + listeToIndex] = arr2[listeToIndex];
                listeToIndex++;
            }
        }
        // Hvis det er arr1 som ikke har brukt opp alle elementene fyller den opp arr
        // med de tallene listeEnIndex rekkefølge siden de allerede er sortert.
        while (listeEnIndex < arr1.length) {
            arr[listeEnIndex + listeToIndex] = arr1[listeEnIndex];
            listeEnIndex++;
        }
        // samme for arr2
        while (listeToIndex < arr2.length) {
            arr[listeEnIndex + listeToIndex] = arr2[listeToIndex];
            listeToIndex++;
        }
        return arr;
        
    }

    // Denne metoden starter mergesort
    //O(n log n)
    int[] sort(int arr[]) {

        if (arr.length <= 1) { // Hvis lengden av listen er 1 så er den allerede sortert og vil da starte
                               // mergen
            return arr;
        }
        // Deler listen i to
        int i = arr.length / 2;
        int[] arr1 = new int[i];
        int[] arr2 = new int[arr.length - i];
        for (int k = 0; k < i; k++) {
            arr1[k] = arr[k];
        }
        for (int j = 0; j < arr.length - i; j++) {
            arr2[j] = arr[j + i];
        }
        // Deler venstre siden av listen
        sort(arr1);
        // Deler høyre siden av listen
        sort(arr2);
        return merge(arr1, arr2, arr); // merger de to sidene sammen

    }

    //O(n^2)
    public void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) { //Starter med i = 1 fordi man sjekker alltid de til venstre
            int tmp = arr[i]; //temp hvis det blir byttet
            int j = i - 1; // venstre for temp
            while (j >= 0 && tmp < arr[j]) { //sjekker de til venstre men hvis den til venstre er større
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tmp; // hvis den til venstre ikke er større vil dette bare bli det samme elementet
            // Det er j + 1 fordi man tar j-- etter man bytter i while loopen
        }
    }
    
}
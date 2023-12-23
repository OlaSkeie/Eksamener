public class PrioritetsKoer {
    //Kan også bare bruke klassen PriorityQueue for å lage en heap
    // Støtter insert(element), removeMin() som fjerner minste elementet
    // Kan implementeres på mange ulike måter, som usortert- og sortert liste,
    // balansert søketre, og en heap

    //Huffman koding:
    //Komprimere data og gi en frekvens til alle symbolene våres.
    //Hvert symbol representeres med en bitstreng
    public Node[] insert(Node[] arr, int x, int elementerIHeap){
        arr[elementerIHeap] = new Node(x);
        int i = elementerIHeap;

        while(0 < i && arr[i].element < arr[parentOf(i)].element){ //Bytter med forelderen om den er mindre helt til det er sortert riktig
            Node temp = arr[i];
            arr[i] = arr[parentOf(i)];
            arr[parentOf(i)] = temp;
            i = parentOf(i);
        }
        return arr;
    }

    public int removeMin(Node[] arr, int eIHeap){
        Node x = arr[0];
        arr[0] = arr[eIHeap - 1];
        int i = 0;
        while(rightOf(i) < eIHeap - 1){
            int j;
            if(arr[leftOf(i)].element <= arr[rightOf(i)].element){ //Vil finne ut hvilken indeks av de to barna det minste elementet ligger
                j = leftOf(i);
            }
            else{
                j = rightOf(i);
            }
            if(arr[j].element > arr[i].element){ //Sammenligner med det elementet som 'pekes' på, altså hvis man flytter 6 nedover er 6 arr[i]
                break; //Hvis pekeren er mindre, breaker man
            }
            Node temp = arr[i]; //Bytter på pekeren og det minste barnet, hvis det var mindre
            arr[i] = arr[j]; //
            arr[j] = temp;//
            i = j;
        }
        if(leftOf(i) < arr.length - 1 && arr[leftOf(i)].element <= arr[i].element){ //Hvis noden bare hadde et venstre barn og ikke et høyre
            Node temp = arr[i];
            arr[i] = arr[leftOf(i)];
            arr[leftOf(i)] = temp;
        }
        return x.element;

    }
    public int parentOf(int i) { // Finne forelder
        return (i - 1) / 2;
    }

    public int leftOf(int i) { // Finne venstre barn
        return (i + 1) * 2;
    }

    public int rightOf(int i) { // finne høyre barn
        return (i + 2) * 2;
    }

    class Node{
        int element;
        public Node(int e){
            element = e;
        }
        

    }

}

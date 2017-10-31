package dsa;
public class ListArrayBasedPlus<T> extends ListArrayBased<T> {

    public ListArrayBasedPlus() {
        super();
    }

    public void add(int index, T item) {
        if (numItems == items.length) {
            reSize();
        }
        super.add(index, item);
    }


    public void reverse() {
        for (int a = 0, b = numItems - 1; a < b; a++, b--) {
            T temp = items[a];
            items[a]  = items[b];
            items[b] = temp;
        }
    }

    public String toString() {
        String array = "";
        for(int i = 0 ; i < numItems ; i++) {
            array += (items[i].toString() + " ");
        }
        return array;
    }

    private void reSize() {
        T newArray[] = (T[]) new Object[items.length * 2];
        for(int i = 0 ; i < items.length ; i++) {
            newArray[i] = items[i];
        }
        items = newArray;
    }
}
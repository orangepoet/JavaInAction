package cn.orangepoet.inaction.algorithm.tree;

/**
 * 堆是一种完全二叉树, 是由满二叉树衍生而来
 *
 * @author chengzhi
 */
public class Heap {
    private int[] element;
    private int current = -1;

    public int get(int index) {
        if (isEmpty()) {
            return -1;
        }

        if (index > element.length - 1) {
            throw new IndexOutOfBoundsException("index");
        }

        return element[index];
    }

    public int size() {
        return this.element.length;
    }

    /**
     * 把输入的无序数组转为堆
     *
     * @param element
     */
    public Heap(int[] element) {
        if (element == null || element.length == 0) {
            throw new IllegalArgumentException("element");
        }

        this.element = new int[element.length];
        this.element[++current] = 0;

        for (int i : element) {
            insert(i);
        }
    }

    private int findMin() {
        if (isEmpty()) {
            return -1;
        }
        return element[1];
    }

    /**
     * 最小堆生成 (parent<children) 对每个新元素, 先放入到最后位置, 比较它和父元素大小, 如果小于, 交换它和父元素的位置, 递归下去;
     *
     * @param newItem
     */
    private void insert(int newItem) {
        ensureForAdd();
        int position = ++current;
        int parent;
        // position>1 说明不是堆顶元素
        for (; position > 1 && newItem < (parent = element[position / 2]); position /= 2) {
            // 当父元素 > 新插元素, 父元素向下移动
            element[position] = parent;
        }
        // 当父元素下沉后, 剩余的位置就是新元素插入位置;
        element[position] = newItem;
    }

    private void ensureForAdd() {
        if (current == element.length - 1) {
            int[] copy = new int[element.length * 2];
            for (int i = 0; i < element.length; i++) {
                copy[i] = element[i];
            }
            element = copy;
        }
    }

    public boolean isEmpty() {
        return size() < 2;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {150, 80, 40, 20, 10, 50, 110, 100, 30, 90, 60, 70, 120, 140, 130};
        Heap heap = new Heap(arr);
        printHeap(heap);
        System.out.println(String.format("min element: %d", heap.findMin()));
    }

    private static void printHeap(Heap heap) {
        if (heap.isEmpty()) {
            return;
        }
        System.out.println("------------------------------");
        for (int i = 1; i < heap.size(); i++) {
            if (heap.get(i) == 0) { break; }

            int step = (int)Math.floor(Math.log(i) / Math.log(2));
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < step; j++) {
                sb.append("-");
            }

            System.out.println(String.format("%s[%d]", sb.toString(), heap.get(i)));
        }
        System.out.println();
    }
}

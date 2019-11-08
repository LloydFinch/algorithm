public class ATest {

    public static void main(String[] args) {

        Node head = new Node();
        head.value = "head";

        int number = 5;
        Node cur = head;
        for (int i = 0; i < number; i++) {
            Node node = new Node();
            node.value = String.valueOf(i);

            cur.next = node;
            cur = node;
        }

        Node temp = head;
        while (temp != null) {
            temp.visit();
            temp = temp.next;
        }

    }

    public static class Test {

    }


    public static class Node {

        public String value;
        public Node next;

        public void visit() {
            System.out.println(value);
        }
    }


}

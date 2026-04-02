public class PhoneBook {
    private Tree<Contact> tree;

    public PhoneBook() {
        tree = new Tree<>();
    }

    public void add(String name, String phone) {
        tree.add(new Contact(name, phone));
    }

    public void delete(String name) {
        tree.delete(new Contact(name, ""));
    }

    public String find(String name) {
        TreeNode<Contact> node = tree.search(new Contact(name, ""));
        return node != null ? node.getValue().getPhone() : null;
    }

    public void showAll() {
        System.out.println("\n=== Телефонная книга (по алфавиту) ===");
        tree.inOrder();
    }

    public int size() {
        return tree.size();
    }
}
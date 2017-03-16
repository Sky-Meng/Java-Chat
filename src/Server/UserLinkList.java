package Server;

public class UserLinkList {
	Node root;
	Node pointer;
	int count;

	/**
	 * �����û�����
	 */
	public UserLinkList() {
		// TODO �Զ����ĳɷ������
		root = new Node();
		root.next = null;
		pointer = null;
		count = 0;
	}

	/**
	 * ����û�
	 */
	public void addUser(Node n) {
		// TODO �Զ����ɵķ������
		pointer = root;
		while (pointer.next != null) {
			pointer = pointer.next;
		}
		pointer.next = n;
		n.next = null;
		count++;
	}

	/**
	 * ɾ���û�
	 */
	public void delUser(Node n) {
		// TODO �Զ����ɵķ������
		pointer = root;
		while (pointer.next != null) {
			if (pointer.next == n) {
				pointer = pointer.next;
				count--;
				break;
			}
			pointer = pointer.next;
		}
	}

	/**
	 * �����û���
	 */
	public int getCount() {
		// TODO �Զ����ɵķ������
		return count;
	}

	/**
	 * �����û��������û�
	 */
	public Node findUser(String username) {
		// TODO �Զ����ɵķ������
		if (count == 0) {
			return null;
		}
		pointer = root;
		while (pointer.next != null) {
			pointer = pointer.next;
			if (pointer.username.equalsIgnoreCase(username)) {
				return pointer;
			}
		}
		return null;
	}

	/**
	 * �������������û�
	 */
	public Node findUser(int index) {
		// TODO �Զ����ɵķ������
		if (count == 0) {
			return null;
		}
		if (index < 0) {
			return null;
		}
		pointer = root;
		int i = 0;
		while (i < index + 1) {
			if (pointer.next != null) {
				pointer = pointer.next;
			} else {
				return null;
			}
			i++;
		}
		return pointer;
	}

}

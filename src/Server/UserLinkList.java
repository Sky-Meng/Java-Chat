package Server;

public class UserLinkList {
	Node root;
	Node pointer;
	int count;

	/**
	 * 构造用户链表
	 */
	public UserLinkList() {
		// TODO 自动生的成方法存根
		root = new Node();
		root.next = null;
		pointer = null;
		count = 0;
	}

	/**
	 * 添加用户
	 */
	public void addUser(Node n) {
		// TODO 自动生成的方法存根
		pointer = root;
		while (pointer.next != null) {
			pointer = pointer.next;
		}
		pointer.next = n;
		n.next = null;
		count++;
	}

	/**
	 * 删除用户
	 */
	public void delUser(Node n) {
		// TODO 自动生成的方法存根
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
	 * 返回用户数
	 */
	public int getCount() {
		// TODO 自动生成的方法存根
		return count;
	}

	/**
	 * 根据用户名查找用户
	 */
	public Node findUser(String username) {
		// TODO 自动生成的方法存根
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
	 * 根据索引查找用户
	 */
	public Node findUser(int index) {
		// TODO 自动生成的方法存根
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

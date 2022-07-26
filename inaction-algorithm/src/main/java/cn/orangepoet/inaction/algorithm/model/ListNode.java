package cn.orangepoet.inaction.algorithm.model;

import cn.orangepoet.inaction.algorithm.LeetCode;

import java.util.Objects;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListNode listNode = (ListNode)o;
        if (val == listNode.val) {
            if (this.next == null) {
                if (listNode.next == null) {
                    return true;
                }
                return false;
            }
            return this.next.equals(listNode.next);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }
}

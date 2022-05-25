package cn.orangepoet.inaction.algorithm.dp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author chengzhi
 * @date 2022/05/24
 */
public class Compose {
    Codec codec = new Codec();

    public static void main(String[] args) {
        //LRU lru = new LRU(5);
        //lru.put("1", "a");
        //System.out.println(lru.get("1"));
        //
        //lru.put("2", "b");
        //lru.put("3", "c");
        //lru.put("4", "d");
        //
        //lru.get("1");
        //
        //lru.put("5", "d");
        //lru.put("6", "e");
        //
        //System.out.println(lru.get("1"));
        //System.out.println(lru.get("2"));

        Compose compose = new Compose();
        TreeNode deserialize = compose.codec.deserialize("2_1_3");
        String serialize = compose.codec.serialize(deserialize);
        TreeNode deserialize1 = compose.codec.deserialize(serialize);
        System.out.println(deserialize1);
    }

    public static class LRU {
        private final Map<String, String> map;
        private int capacity;

        public LRU(int capacity) {
            this.capacity = capacity;
            this.map = new LinkedHashMap<String, String>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return this.size() > capacity;
                }
            };
        }

        public String get(String key) {
            return map.get(key);
        }

        public void put(String key, String value) {
            this.map.put(key, value);
        }
    }

    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            Queue<TreeNode> nodeQueue = new ArrayDeque<>();
            nodeQueue.add(root);

            TreeNode emptyNode = new TreeNode(-1);

            List<Integer> values = new ArrayList<>();
            while (nodeQueue.peek() != null) {
                TreeNode node = nodeQueue.poll();
                values.add(node.val);

                if (node.left != null) {
                    nodeQueue.add(node.left);
                } else if (node != emptyNode) {
                    nodeQueue.add(emptyNode);
                }

                if (node.right != null) {
                    nodeQueue.add(node.right);
                } else if (node != emptyNode) {
                    nodeQueue.add(emptyNode);
                }
            }
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                if (i == 0) {
                    result.append(values.get(i));
                } else {
                    result.append("_").append(values.get(i));
                }
            }
            return result.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] parts = data.split("_");
            Map<Integer, TreeNode> treeMap = new HashMap<>();
            for (int i = 0; i < parts.length; i++) {
                int val = Integer.parseInt(parts[i]);
                if (val == -1) {
                    continue;
                }
                TreeNode node = new TreeNode(val);
                treeMap.put(i, node);
                if (i > 0) {
                    int pIndex = (i + 1) / 2 - 1;
                    boolean mod = (i + 1) % 2 == 0;
                    if (mod) {
                        treeMap.get(pIndex).left = node;
                    } else {
                        treeMap.get(pIndex).right = node;
                    }
                }
            }
            return treeMap.get(0);
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {val = x;}
    }
}

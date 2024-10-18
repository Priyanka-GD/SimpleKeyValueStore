package com.distributed.keyvaluestore.demo;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class Master {
    private static final int MAX_HASH = (int) Math.pow(2, 32);
    private SortedMap<Integer, Node> nodeMap = new TreeMap<>(); // Chord: Hash -> Node mapping
    private List<Node> nodes = new ArrayList<>(); // List of all nodes (Slaves)
    private int virtualNodes = 10;

    public Master () {
        initializeNodes(3); // Example: Initializing 3 nodes
    }

    // Initialize a number of nodes in the hash space
    private void initializeNodes (int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            addNode(new Node(i));
        }
    }

    // Add a new node to the hash space
    public void addNode (Node node) {
        for (int i = 0; i < virtualNodes; i++) {
            int hash = hash(node.getId() + "-" + i); // Create virtual nodes
            nodeMap.put(hash, node);
        }
        nodes.add(node);
    }

    // Remove a node and redistribute keys
    public void removeNode (Node node) {
        nodes.remove(node);
        // Remove the virtual nodes of the removed node from the hash ring
        for (int i = 0; i < virtualNodes; i++) {
            int hash = hash(node.getId() + "-" + i);
            nodeMap.remove(hash);
        }

        // Get the key-value pairs from the removed node
        LRUCache cache = node.getCache();
        Map<String, String> dataToMigrate = cache.getCacheData(); // Get all data in this node

        // Redistribute the data to the new responsible nodes
        for (Map.Entry<String, String> entry : dataToMigrate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Find the new responsible node for each key
            Node newResponsibleNode = getResponsibleNode(key);
            // Migrate the data to the new node
            newResponsibleNode.getCache().set(key, value);
        }

        System.out.println("Node " + node.getId() + " removed and its data has been migrated.");
    }

    // Hash function (could use SHA-256 or MD5)
    private int hash (String key) {
        return key.hashCode() % MAX_HASH;
    }

    // Get the responsible node for a given key
    public Node getResponsibleNode (String key) {
        int hashKey = hash(key);
        SortedMap<Integer, Node> tailMap = nodeMap.tailMap(hashKey);
        int targetHash = tailMap.isEmpty() ? nodeMap.firstKey() : tailMap.firstKey();
        return nodeMap.get(targetHash);
    }

    @GetMapping("/getKey")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<String, Object>> getKey (@RequestParam String key) {
        Node responsibleNode = getResponsibleNode(key);
        String value = responsibleNode.getCache().get(key);
        if (value == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Key not found"));
        }
        Map<String, Object> response = new HashMap<>();
        response.put("key", key);
        response.put("value", value);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/setKey")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<String, Object>> setKey (@RequestParam String key, @RequestParam String value) {
        Node responsibleNode = getResponsibleNode(key);
        responsibleNode.getCache().set(key, value);
        Map<String, Object> response = new HashMap<>();
        response.put("key", key);
        response.put("value", value);
        response.put("node", responsibleNode.getId());
        return ResponseEntity.ok(response);
    }
}

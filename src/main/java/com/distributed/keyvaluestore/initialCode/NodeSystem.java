package com.distributed.keyvaluestore.initialCode;


import com.distributed.keyvaluestore.initialCode.Node;

import java.util.Random;
import java.util.Scanner;

public class NodeSystem {
    private final Node[] nodes;
    private final Random random = new Random();

    public NodeSystem(int numberOfNodes) {
        nodes = new Node[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            nodes[i] = new Node(i);
        }
    }

    public void addDataToRandomNode(String key, String value) {
        int randomNodeIndex = random.nextInt(nodes.length);
        nodes[randomNodeIndex].addData(key, value);
    }

    public void displayAllNodes() {
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("Displaying HashMap for Node " + i + ":");
            nodes[i].displayHashMap();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        int numberOfNodes = scanner.nextInt();
        NodeSystem nodeSystem = new NodeSystem(numberOfNodes);

        while (true) {
            System.out.print("Enter key (or type 'exit' to stop): ");
            String key = scanner.next();
            if (key.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.print("Enter value: ");
            String value = scanner.next();
            nodeSystem.addDataToRandomNode(key, value);
        }
        nodeSystem.displayAllNodes();
        scanner.close();

    }
}
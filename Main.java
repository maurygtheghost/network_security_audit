import java.util.ArrayList;

// ==========================================
// 1. COMPOSITION COMPONENT
// ==========================================
// The NetworkConfig class represents a "has-a" relationship.
// Every Asset WILL HAVE a NetworkConfig object inside it.
class NetworkConfig {
    private String ipAddress;
    private String macAddress;

    public NetworkConfig(String ipAddress, String macAddress) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }

    public String getIpAddress() { return ipAddress; }
    public String getMacAddress() { return macAddress; }
}

// ==========================================
// 2. BASE PARENT CLASS (INHERITANCE)
// ==========================================
class Asset {
    private String assetName;
    private String osVersion;
    // Composition: Asset has a NetworkConfig object
    private NetworkConfig networkInfo; 

    public Asset(String assetName, String osVersion, NetworkConfig networkInfo) {
        this.assetName = assetName;
        this.osVersion = osVersion;
        this.networkInfo = networkInfo;
    }

    public String getAssetName() { return assetName; }
    public String getOsVersion() { return osVersion; }
    public NetworkConfig getNetworkInfo() { return networkInfo; }

    // Base method to be overridden by child classes
    public void displayDetails() {
        System.out.println("Asset: " + assetName);
        System.out.println("-> IP: " + networkInfo.getIpAddress() + " | MAC: " + networkInfo.getMacAddress());
        System.out.println("-> OS: " + osVersion);
    }
}

// ==========================================
// 3. CHILD CLASS 1: SERVER
// ==========================================
class Server extends Asset {
    private String serverRole; // e.g., "Database", "Web Server"
    private boolean isFirewallEnabled;

    public Server(String assetName, String osVersion, NetworkConfig networkInfo, String serverRole, boolean isFirewallEnabled) {
        // super() calls the Parent (Asset) constructor
        super(assetName, osVersion, networkInfo);
        this.serverRole = serverRole;
        this.isFirewallEnabled = isFirewallEnabled;
    }

    @Override
    public void displayDetails() {
        super.displayDetails(); // Runs parent display logic
        System.out.println("-> Type: Server | Role: " + serverRole);
        System.out.println("-> Security Status: " + (isFirewallEnabled ? "PASS (Firewall Active)" : "FLAGGED (NO FIREWALL)"));
    }
}

// ==========================================
// 4. CHILD CLASS 2: WORKSTATION
// ==========================================
class Workstation extends Asset {
    private String assignedUser;
    private boolean hasMalwareScanner;

    public Workstation(String assetName, String osVersion, NetworkConfig networkInfo, String assignedUser, boolean hasMalwareScanner) {
        super(assetName, osVersion, networkInfo);
        this.assignedUser = assignedUser;
        this.hasMalwareScanner = hasMalwareScanner;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("-> Type: Workstation | Assigned To: " + assignedUser);
        System.out.println("-> Security Status: " + (hasMalwareScanner ? "PASS (AV Active)" : "FLAGGED (NO ANTIVIRUS)"));
    }
}

// ==========================================
// 5. MAIN EXECUTION CLASS
// ==========================================
public class Main {
    public static void main(String[] args) {
        System.out.println("=== INITIALIZING NETWORK SECURITY SCAN ===");
        System.out.println("Scanning local subnets for compliance flaws...\n");

        // Creating an ArrayList to store our network inventory dynamically
        ArrayList<Asset> networkInventory = new ArrayList<>();

        // Creating Network Configurations (Composition)
        NetworkConfig net1 = new NetworkConfig("192.168.1.50", "00:1A:2B:3C:4D:5E");
        NetworkConfig net2 = new NetworkConfig("192.168.1.101", "00:AB:CD:EF:12:34");
        NetworkConfig net3 = new NetworkConfig("192.168.1.55", "00:50:56:C0:00:08");

        // Instantiating objects using Inheritance hierarchies
        Server prodDB = new Server("Production-DB", "Ubuntu Server 22.04", net1, "Database", true);
        Workstation engPC = new Workstation("Engineering-Laptop", "Windows 11", net2, "Alice Smith", false);
        Server legacyWeb = new Server("Legacy-Web-IIS", "Windows Server 2012", net3, "Web Server", false);

        // Adding diverse object types into our single parent-type collection
        networkInventory.add(prodDB);
        networkInventory.add(engPC);
        networkInventory.add(legacyWeb);

        // Iterating through inventory and performing the "Security Audit"
        int totalIssues = 0;
        for (Asset device : networkInventory) {
            device.displayDetails();
            
            // Evaluates rules to flag architectural vulnerabilities
            if (device.getOsVersion().contains("2012")) {
                System.out.println("-> [CRITICAL] End-of-Life OS detected! Immediate upgrade required.");
                totalIssues++;
            }
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n=== SCAN SUMMARY ===");
        System.out.println("Total Devices Audited: " + networkInventory.size());
        System.out.println("Total Security Risks Found: " + totalIssues);
    }
}

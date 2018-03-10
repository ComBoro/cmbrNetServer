package net.comboro.server;

import net.comboro.server.command.CommandMap;
import net.comboro.server.files.ExternalFile;
import net.comboro.server.networking.TCPServerImpl;
import net.comboro.server.plugin.PluginLoader;
import net.comboro.server.plugin.PluginMap;
import net.comboro.server.ui.BetterUI;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

import static net.comboro.server.files.ExternalFile.*;

public final class Application {

    static BetterUI betterUI;
    private static PluginLoader pluginLoader;
    private static PluginMap pluginMap;
    static boolean offline = false;
    static TCPServerImpl tcp_server;
    static final Properties properties = new Properties();

    public static final Color error = new Color(178, 34, 34);
    public static Color defaultColour = Color.BLACK;

    private Application(){
    }

    public static void main(String[] args) {
        // Load GUI
        betterUI = new BetterUI();
        // Load the server configuration
        betterUI.setTitle(serverInfoFile.getName());

        // Start server
        tcp_server = new TCPServerImpl(serverInfoFile.getPort());
        tcp_server.startServer();

        // Basic auto response
        initProperties();
        // Register default commands
        CommandMap.addDefaults();

        // Loader the basic Plugin Map
        pluginMap = new PluginMap();
        // Loader the loader loading plugins from 'plugins'
        pluginLoader = new PluginLoader(pluginMap, Loader.loadDirectory("plugins"));
        // Load all plugins
        pluginLoader.loadAll();

//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            serverInfo.saveConfig();
//            pluginMap.clear();
//            pluginLoader.unloadAll();
//            tcp_server.stopServer();
//            betterUI.dispose();
//            System.exit(0);
//        }));
    }

    private static void initProperties() {
        properties.setProperty("version", "1.0");
        properties.setProperty("name", serverInfoFile.getName());
    }

    /**
     * Shuts down the server and disconnects all players sending them the server
     * close message.
     *
     */
    public static void shutdown() {
        properties.clear();
        tcp_server.stopServer();
        if(pluginMap!=null)
            pluginMap.clear();
        if (pluginLoader != null)
            getPluginLoader().unloadAll();

        closeStatic();
        Runtime.getRuntime().halt(0);

    }

    public static void log(String string) {
        logFile.log(string);
    }

    /**
     * @return The default {@link PluginLoader} initialised in the main method
     */
    public static PluginLoader getPluginLoader() {
        return pluginLoader;
    }

    /**
     * @return The default {@link PluginMap} initialised in the main method
     */
    public static PluginMap getPluginMap() {
        return pluginMap;
    }

    /**
     * Gets the {@link Properties} instance.
     *
     * @return the {@link Properties} instance
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * @return the server debugging state
     */
    public static boolean isDebugging() {
        return serverInfoFile.isDebugging();
    }

    /**
     * Change the debugging state of the server
     *
     * @param debugging the new debugging state
     */
    public static void setDebugging(boolean debugging) {
        serverInfoFile.setDebugging(debugging);
    }

    public static void updatePluginsPane(){
        betterUI.updatePluginsPane();
    }

    public static void removeUIComponent(JComponent component){
        betterUI.getConsoleTabbedPane().remove(component);
    }

    public static void addUIComponent(String label, JComponent component){
        betterUI.getConsoleTabbedPane().add(label,component);
    }

    public static void updateClientsPane(){
        betterUI.updateUI();
    }

    public static void clearCommandLine(){
        betterUI.clearCommandLine();
    }

    public static void clearConsole(){
        betterUI.clearConsole();
    }

    public static TCPServerImpl getTCPImpl(){
        return tcp_server;
    }

}

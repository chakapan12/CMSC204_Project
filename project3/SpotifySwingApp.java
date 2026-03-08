import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ListIterator;


public class SpotifySwingApp {
    private JFrame frame;
    private JComboBox<String> playlistDropdown;
    private JTextArea songDisplay;
    private JButton  nextButton, prevButton, addSongButton, removeSongButton;
    private static File selectedUserFile;
    private int selectedPlaylistIndex = 0;
    private User user;

    public SpotifySwingApp(User user, SpotifyManager manager) {
        this.user = user;
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Mini Spotify - " + user.getUsername());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel topPanel = new JPanel(new BorderLayout());
        playlistDropdown = new JComboBox<>();
        refreshPlaylistDropdown();
         
        playlistDropdown.addActionListener(e -> {
            selectedPlaylistIndex = playlistDropdown.getSelectedIndex();
            updateSongDisplay();
        });
      
        topPanel.add(new JLabel("Playlists:"), BorderLayout.WEST);
        topPanel.add(playlistDropdown, BorderLayout.CENTER);

        songDisplay = new JTextArea();
        songDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(songDisplay);
        updateSongDisplay();
        
        JPanel controlPanel = new JPanel();
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        addSongButton = new JButton("Add Song");
        removeSongButton = new JButton("Remove Song");

    
        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        controlPanel.add(addSongButton);
        controlPanel.add(removeSongButton);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        setupListeners();
        updateSongDisplay();

        frame.setVisible(true);
    }

    private void refreshPlaylistDropdown() {
        playlistDropdown.removeAllItems();
        for (int i = 0; i < user.getPlaylistCount(); i++) {
            Playlist p = user.getPlaylists().get(i);
            String displayName = (i == selectedPlaylistIndex ? "> " : "  ")
                    + (i + 1) + ". " + p.getName();
            playlistDropdown.addItem(displayName);
        }
        playlistDropdown.setSelectedIndex(user.getPlaylistCount() > 0 ? selectedPlaylistIndex : -1);
    }

    private void setupListeners() {

        
        nextButton.addActionListener(e -> {
        	Playlist p = getCurrentPlaylist();
            if (p != null && !p.isEmpty()) {
                p.nextSong();
                updateSongDisplay(); // redraw full playlist with new ">" marker
            }
        });

        prevButton.addActionListener(e -> {
        	Playlist p = getCurrentPlaylist();
            if (p != null && !p.isEmpty()) {
                p.previousSong(); // move iterator backward
                updateSongDisplay(); // redraw full playlist with new ">" marker
            }
        });

        addSongButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(frame, "Enter song title:");
            String artist = JOptionPane.showInputDialog(frame, "Enter artist:");
            if (title != null && artist != null && !title.isBlank() && !artist.isBlank()) {
            	getCurrentPlaylist().addSong(new Song(title, artist));
                updateSongDisplay();
            }
        });

        removeSongButton.addActionListener(e -> {
            Playlist currentPlaylist = getCurrentPlaylist();
            if (currentPlaylist != null) {
                Song current = currentPlaylist.getCurrentSong();
                if (current != null) {
                    boolean removed = currentPlaylist.removeSong(current);
                    updateSongDisplay();
                    JOptionPane.showMessageDialog(frame, removed ? "Removed: " + current.getTitle() : "No song to remove.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No song is currently selected.");
                }
            }
        });
    }
    private Playlist getCurrentPlaylist() {
        int selectedIndex = playlistDropdown.getSelectedIndex();
        if (selectedIndex == -1) return null;
        return user.getPlaylists().get(selectedIndex);
    }


    private void updateSongDisplay() {
        Playlist p = getCurrentPlaylist();
        if (p == null) {
            songDisplay.setText("No playlist selected.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        ListIterator<Song> it = p.getSongs().iterator();
        Song current = p.getCurrentSong();

        while (it.hasNext()) {
            Song s = it.next();
            sb.append(s.equals(current) ? "> " : "  ");
            sb.append(s).append("\n");
        }
        songDisplay.setText(sb.toString());
    }

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select User Data File");
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "No file selected. Exiting app.");
            return;
        }

        selectedUserFile = fileChooser.getSelectedFile();
        SpotifyManager manager = new SpotifyManager();
        try {
            manager.loadUsersFromFile(selectedUserFile.getAbsolutePath());
        } catch (InvalidUserFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "File Format Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String chosenUsername = JOptionPane.showInputDialog(null, "Enter username:");
        if (chosenUsername == null || chosenUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No username entered. Exiting app.");
            return;
        }
        String password = JOptionPane.showInputDialog(null, "Enter password:");
        if (password == null) {
            JOptionPane.showMessageDialog(null, "No password entered. Exiting app.");
            return;
        }

        try {
            User userFromFile = manager.findUser(chosenUsername.trim(), password.trim());
            SwingUtilities.invokeLater(() -> new SpotifySwingApp(userFromFile, manager));
        } catch (UserNotFoundException | InvalidPasswordException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



